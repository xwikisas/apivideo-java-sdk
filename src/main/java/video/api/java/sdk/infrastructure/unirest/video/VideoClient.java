package video.api.java.sdk.infrastructure.unirest.video;

import kong.unirest.HttpRequest;
import kong.unirest.HttpRequestWithBody;
import kong.unirest.JsonNode;
import kong.unirest.MultipartBody;
import org.json.JSONObject;
import video.api.java.sdk.domain.QueryParams;
import video.api.java.sdk.domain.exception.ResponseException;
import video.api.java.sdk.domain.video.Status;
import video.api.java.sdk.domain.video.UploadProgressListener;
import video.api.java.sdk.domain.video.Video;
import video.api.java.sdk.infrastructure.pagination.IteratorIterable;
import video.api.java.sdk.infrastructure.pagination.PageIterator;
import video.api.java.sdk.infrastructure.unirest.RequestBuilder;
import video.api.java.sdk.infrastructure.unirest.RequestExecutor;
import video.api.java.sdk.infrastructure.unirest.pagination.UriPageLoader;
import video.api.java.sdk.infrastructure.unirest.serializer.JsonSerializer;

import java.io.*;
import java.util.HashMap;

import static java.lang.Math.min;

public class VideoClient implements video.api.java.sdk.domain.video.VideoClient {
    private static final int CHUNK_SIZE = 50 * 1024 * 1024; // 50 Mo

    private final RequestBuilder        requestBuilder;
    private final JsonSerializer<Video> serializer;
    private final RequestExecutor       requestExecutor;
    private final StatusSerializer      statusSerializer = new StatusSerializer();

    public VideoClient(RequestBuilder requestBuilder, JsonSerializer<Video> serializer, RequestExecutor requestExecutor) {
        this.requestBuilder  = requestBuilder;
        this.serializer      = serializer;
        this.requestExecutor = requestExecutor;
    }

    public Video get(String videoId) throws ResponseException {
        HttpRequest request = requestBuilder.get("/videos/" + videoId);

        JsonNode responseBody = requestExecutor.executeJson(request);

        return serializer.deserialize(responseBody.getObject());
    }

    public Status getStatus(String videoId) throws ResponseException {
        HttpRequest request = requestBuilder.get("/videos/" + videoId + "/status");

        JsonNode responseBody = requestExecutor.executeJson(request);

        return statusSerializer.deserialize(responseBody.getObject());
    }

    public Video create(Video video) throws ResponseException {
        if (video.title == null) {
            video.title = "";
        }

        HttpRequest request = requestBuilder.post("/videos")
                .body(serializer.serialize(video));

        JsonNode responseBody = requestExecutor.executeJson(request);

        return serializer.deserialize(responseBody.getObject());
    }

    public Video upload(File file) throws ResponseException {
        return upload(file, new Video(), null);
    }

    public Video upload(File file, UploadProgressListener listener) throws ResponseException {
        return upload(file, new Video(), listener);
    }

    public Video upload(File file, Video video) throws ResponseException {
        if (video.title == null) {
            return upload(file);
        }

        return upload(file, video, null);
    }

    public Video upload(File file, Video video, UploadProgressListener listener) throws ResponseException {
        if (!file.exists() || !file.isFile()) {
            throw new IllegalArgumentException("Can't open file.");
        }
        String videoId;
        if (video.title == null && video.videoId == null) {
            video.title = file.getName();
            Video videoCreated = create(video);
            videoId = videoCreated.videoId;
        } else if (video.videoId == null) {
            Video videoCreated = create(video);
            videoId = videoCreated.videoId;
        } else {
            videoId = video.videoId;
        }
        int fileLength = (int) file.length();

        try {
            Thread.sleep(150);
        } catch (InterruptedException e) {
            throw new IllegalArgumentException(e);
        }

        try {
            // Upload in a single request when file is small enough
            if (fileLength <= 0) {
                throw new IllegalArgumentException("Source is empty.");
            }

            JsonNode responseBody;
            if (fileLength < CHUNK_SIZE) {
                responseBody = uploadSingleRequest(listener, file, videoId);
            } else {
                responseBody = uploadMultipleRequests(file, listener, videoId, fileLength);
            }

            return serializer.deserialize(responseBody.getObject());
        } catch (IOException e) {
            throw new IllegalArgumentException(e.getMessage());
        }

    }

    private JsonNode uploadMultipleRequests(File file, UploadProgressListener listener, String videoId, int fileLength) throws IOException, ResponseException {
        RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");
        int              copiedBytes      = 0;
        int              chunkCount       = (int) Math.ceil((double) fileLength / CHUNK_SIZE);
        JsonNode         responseBody     = null;
        for (int chunkNum = 0; chunkNum < chunkCount; chunkNum++) {

            String chunkFileName = "upload-chunk-";
            int    from          = copiedBytes;
            copiedBytes = min(copiedBytes + CHUNK_SIZE, fileLength);
            int chunkFileSize = copiedBytes - from;

            String tmpdir = System.getProperty("java.io.tmpdir");

            final FileInputStream chunkStream = new FileInputStream(file);
            //noinspection ResultOfMethodCallIgnored
            chunkStream.skip(from);
            byte[] b         = new byte[chunkFileSize];
            File   chunkFile = File.createTempFile(tmpdir, chunkFileName);

            RandomAccessFile randomAccessChunk = new RandomAccessFile(chunkFile, "rw");
            randomAccessFile.readFully(b);
            randomAccessChunk.write(b, 0, chunkFileSize);
            final InputStream stream      = new FileInputStream(chunkFile);
            String            rangeHeader = "bytes " + from + "-" + (copiedBytes - 1) + "/" + fileLength;
            HashMap<String, String> headers = new HashMap<String, String>() {{
                put("Content-Range", rangeHeader);
            }};

            HttpRequest request = buildChunkUploadRequest(listener, videoId, chunkCount, chunkFile, stream, headers, chunkNum);

            responseBody = requestExecutor.executeJson(request);
            stream.close();

            chunkFile.deleteOnExit();

        }

        return responseBody;
    }

    private JsonNode uploadSingleRequest(UploadProgressListener listener, File file, String videoId) throws ResponseException, IOException {
        FileInputStream inputStream = new FileInputStream(file);
        HttpRequest     request     = buildUploadRequest(listener, file, videoId, inputStream);

        JsonNode responseBody = requestExecutor.executeJson(request);

        inputStream.close();
        return responseBody;
    }

    private HttpRequest buildUploadRequest(UploadProgressListener listener, File file, String videoId, FileInputStream inputStream) {
        HttpRequestWithBody request = requestBuilder.post("/videos/" + videoId + "/source");

        MultipartBody uploadField = request.field("file", inputStream, file.getName());

        if (listener != null) {
            uploadField.uploadMonitor((field, fileName, bytesWritten, totalBytes) ->
                                              listener.onProgress(bytesWritten, totalBytes, 1, 0));
        }

        return request;
    }

    private HttpRequest buildChunkUploadRequest(UploadProgressListener listener, String videoId, int chunkCount, File fileToUpload, InputStream inputStream, HashMap<String, String> headers, int chunkNum) {
        HttpRequestWithBody request = requestBuilder.post("/videos/" + videoId + "/source")
                .headers(headers);

        MultipartBody uploadField = request.field("file", inputStream, fileToUpload.getName());

        if (listener != null) {
            uploadField.uploadMonitor((field, fileName, bytesWritten, totalBytes) ->
                                              listener.onProgress(bytesWritten, totalBytes, chunkCount, chunkNum));
        }

        return request;
    }


    public Video uploadThumbnail(Video video, File file) throws ResponseException, IllegalArgumentException {
        try (FileInputStream inputStream = new FileInputStream(file)) {
            HttpRequest request = requestBuilder.post("/videos/" + video.videoId + "/thumbnail")
                    .field("file", inputStream, file.getName());

            JsonNode responseBody = requestExecutor.executeJson(request);

            return serializer.deserialize(responseBody.getObject());

        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public Video updateThumbnail(Video video, String timeCode) throws ResponseException {
        HttpRequest request = requestBuilder.patch("/videos/" + video.videoId)
                .body(new JSONObject().put("timecode", timeCode));

        JsonNode responseBody = requestExecutor.executeJson(request);

        return serializer.deserialize(responseBody.getObject());
    }

    public Video update(Video video) throws ResponseException {
        HttpRequest request = requestBuilder.patch("/videos/" + video.videoId)
                .body(serializer.serialize(video));

        JsonNode responseBody = requestExecutor.executeJson(request);

        return serializer.deserialize(responseBody.getObject());
    }


    public void delete(Video video) throws ResponseException {
        HttpRequest request = requestBuilder.delete("/videos/" + video.videoId);

        requestExecutor.executeJson(request);
    }


    /////////////////////////Iterators//////////////////////////////

    public Iterable<Video> list() throws ResponseException, IllegalArgumentException {
        return search(new QueryParams());
    }

    public Iterable<Video> search(QueryParams queryParams) throws ResponseException, IllegalArgumentException {
        return new IteratorIterable<>(new PageIterator<>(new UriPageLoader<>(
                "/videos",
                requestBuilder,
                requestExecutor,
                serializer
        ), queryParams));
    }

}
