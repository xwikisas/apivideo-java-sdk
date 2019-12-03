package video.api.java.sdk.infrastructure.unirest.video;

import kong.unirest.JsonNode;
import org.json.JSONObject;
import video.api.java.sdk.domain.QueryParams;
import video.api.java.sdk.domain.exception.ResponseException;
import video.api.java.sdk.domain.pagination.PageQuery;
import video.api.java.sdk.domain.video.Status;
import video.api.java.sdk.domain.video.UploadProgressListener;
import video.api.java.sdk.domain.video.Video;
import video.api.java.sdk.infrastructure.pagination.IteratorIterable;
import video.api.java.sdk.infrastructure.pagination.PageIterator;
import video.api.java.sdk.infrastructure.unirest.RequestExecutor;
import video.api.java.sdk.infrastructure.unirest.pagination.UriPageLoader;
import video.api.java.sdk.infrastructure.unirest.request.RequestBuilder;
import video.api.java.sdk.infrastructure.unirest.request.RequestBuilderFactory;
import video.api.java.sdk.infrastructure.unirest.serializer.JsonSerializer;

import java.io.*;

import static java.lang.Math.min;
import static kong.unirest.HttpMethod.*;

public class VideoClient implements video.api.java.sdk.domain.video.VideoClient {
    private static final int CHUNK_SIZE = 128 * 1024 * 1024; // 128 MB

    private final RequestBuilderFactory requestBuilderFactory;
    private final JsonSerializer<Video> serializer;
    private final RequestExecutor       requestExecutor;
    private final StatusSerializer      statusSerializer = new StatusSerializer();

    public VideoClient(RequestBuilderFactory requestBuilderFactory, JsonSerializer<Video> serializer, RequestExecutor requestExecutor) {
        this.requestBuilderFactory = requestBuilderFactory;
        this.serializer            = serializer;
        this.requestExecutor       = requestExecutor;
    }

    public Video get(String videoId) throws ResponseException {
        RequestBuilder request = requestBuilderFactory
                .create(GET, "/videos/" + videoId);

        JsonNode responseBody = requestExecutor.executeJson(request);

        return serializer.deserialize(responseBody.getObject());
    }

    public Status getStatus(String videoId) throws ResponseException {
        RequestBuilder request = requestBuilderFactory
                .create(GET, "/videos/" + videoId + "/status");

        JsonNode responseBody = requestExecutor.executeJson(request);

        return statusSerializer.deserialize(responseBody.getObject());
    }

    public Video create(Video video) throws ResponseException {
        if (video.title == null) {
            video.title = "";
        }

        RequestBuilder request = requestBuilderFactory
                .create(POST, "/videos")
                .withJson(serializer.serialize(video));

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

            try (FileInputStream chunkStream = new FileInputStream(file)) {
                //noinspection ResultOfMethodCallIgnored
                chunkStream.skip(from);

                byte[] b         = new byte[chunkFileSize];
                File   chunkFile = File.createTempFile(tmpdir, chunkFileName);

                RandomAccessFile randomAccessChunk = new RandomAccessFile(chunkFile, "rw");
                randomAccessFile.readFully(b);
                randomAccessChunk.write(b, 0, chunkFileSize);
                final InputStream inputStream = new FileInputStream(chunkFile);
                String            rangeHeader = "bytes " + from + "-" + (copiedBytes - 1) + "/" + fileLength;

                RequestBuilder request = requestBuilderFactory
                        .create(POST, "/videos/" + videoId + "/source")
                        .withChunk(file.getName(), inputStream, chunkCount, chunkNum, listener)
                        .withHeader("Content-Range", rangeHeader);

                responseBody = requestExecutor.executeJson(request);

                chunkFile.deleteOnExit();
            }
        }

        return responseBody;
    }

    private JsonNode uploadSingleRequest(UploadProgressListener listener, File file, String videoId) throws ResponseException, IOException {
        RequestBuilder request = requestBuilderFactory
                .create(POST, "/videos/" + videoId + "/source")
                .withFile(file, listener);

        return requestExecutor.executeJson(request);
    }

    public Video uploadThumbnail(Video video, File file) throws ResponseException, IOException {
        RequestBuilder request = requestBuilderFactory
                .create(POST, "/videos/" + video.videoId + "/thumbnail")
                .withFile(file);

        JsonNode responseBody = requestExecutor.executeJson(request);

        return serializer.deserialize(responseBody.getObject());
    }

    public Video updateThumbnail(Video video, String timeCode) throws ResponseException {
        RequestBuilder request = requestBuilderFactory
                .create(PATCH, "/videos/" + video.videoId)
                .withJson(new JSONObject().put("timecode", timeCode));

        JsonNode responseBody = requestExecutor.executeJson(request);

        return serializer.deserialize(responseBody.getObject());
    }

    public Video update(Video video) throws ResponseException {
        RequestBuilder request = requestBuilderFactory
                .create(PATCH, "/videos/" + video.videoId)
                .withJson(serializer.serialize(video));

        JsonNode responseBody = requestExecutor.executeJson(request);

        return serializer.deserialize(responseBody.getObject());
    }


    public void delete(Video video) throws ResponseException {
        RequestBuilder request = requestBuilderFactory
                .create(DELETE, "/videos/" + video.videoId);

        requestExecutor.executeJson(request);
    }

    /////////////////////////Iterators//////////////////////////////

    public Iterable<Video> list() throws ResponseException, IllegalArgumentException {
        return list(new QueryParams());
    }

    public Iterable<Video> list(QueryParams queryParams) throws ResponseException, IllegalArgumentException {
        return new IteratorIterable<>(new PageIterator<>(new UriPageLoader<>(
                requestBuilderFactory
                        .create(GET, "/videos")
                        .withQueryParams(queryParams.toMap()),
                requestExecutor,
                serializer
        ), new PageQuery()));
    }

}
