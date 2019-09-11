package video.api.java.sdk.infrastructure.unirest.video;

import kong.unirest.*;
import org.json.JSONObject;
import video.api.java.sdk.domain.QueryParams;
import video.api.java.sdk.domain.RequestExecutor;
import video.api.java.sdk.domain.exception.ClientException;
import video.api.java.sdk.domain.exception.ResponseException;
import video.api.java.sdk.domain.video.Video;
import video.api.java.sdk.domain.video.models.Status;
import video.api.java.sdk.infrastructure.pagination.Page;
import video.api.java.sdk.infrastructure.pagination.PageIterator;
import video.api.java.sdk.infrastructure.pagination.PageLoader;
import video.api.java.sdk.infrastructure.unirest.serializer.JsonSerializer;
import video.api.java.sdk.infrastructure.unirest.video.monitor.DefaultUploadProgressListener;
import video.api.java.sdk.infrastructure.unirest.video.monitor.UploadProgressListener;
import video.api.java.sdk.infrastructure.unirest.video.serializers.StatusSerializer;

import java.io.*;
import java.util.HashMap;

import static java.lang.Math.min;

public class VideoClient implements video.api.java.sdk.domain.video.VideoClient, PageLoader<Video> {

    private final        JsonSerializer<Video> serializer;
    private final        RequestExecutor       requestExecutor;
    private final        String                baseUri;
    private final        StatusSerializer      statusSerializer;
    private static final int                   CHUNK_SIZE = 50 * 1024 * 1024; // 50 Mo

    public VideoClient(JsonSerializer<Video> serializer, RequestExecutor requestExecutor, String baseUri) {
        this.serializer       = serializer;
        this.requestExecutor  = requestExecutor;
        this.baseUri          = baseUri;
        this.statusSerializer = new StatusSerializer();
    }

    public Video get(String videoId) throws ResponseException {
        HttpRequest request = Unirest.get(baseUri + "/videos/" + videoId);

        HttpResponse<JsonNode> response = requestExecutor.executeJson(request);

        return getVideoResponse(response);
    }

    public Status getStatus(String videoId) throws ResponseException {
        HttpRequest request = Unirest.get(baseUri + "/videos/" + videoId + "/status");

        HttpResponse<JsonNode> response = requestExecutor.executeJson(request);

        return statusSerializer.deserialize(response.getBody().getObject());
    }

    public Video create(Video video) throws ResponseException {
        if (video.title == null) {
            video.title = "";
        }
        JSONObject prop = serializer.serializeProperties(video);

        HttpRequest request = Unirest.post(baseUri + "/videos").body(prop);

        HttpResponse<JsonNode> response = requestExecutor.executeJson(request);

        return getVideoResponse(response);
    }

    public Video upload(String source, UploadProgressListener listener) throws ResponseException {
        return upload(source, new Video(), listener);
    }

    public Video upload(String source, Video video) throws ResponseException {
        if (video.title == null)
            return upload(source);
        return upload(source, video, new DefaultUploadProgressListener());
    }

    public Video upload(String source) throws ResponseException {
        return upload(source, new Video(), new DefaultUploadProgressListener());
    }

    public Video upload(String source, Video video, UploadProgressListener listener) throws ResponseException {

        File fileToUpload = new File(source);
        if (!fileToUpload.exists() || !fileToUpload.isFile()) {
            throw new IllegalArgumentException("Can't open file " + source);
        }
        String videoId;
        if (video.title == null && video.videoId == null) {
            video.title = fileToUpload.getName();
            Video videoCreated = create(video);
            videoId = videoCreated.videoId;
        } else if (video.videoId == null) {
            Video videoCreated = create(video);
            videoId = videoCreated.videoId;
        } else {
            videoId = video.videoId;
        }
        int size = (int) fileToUpload.length();
        System.out.println("size = " + size);
        try {
            System.out.println("videoId : " + videoId);
            Thread.sleep(150);
        } catch (InterruptedException e) {
            throw new IllegalArgumentException("upload video -->Source :" + source + "\n Message-->" + e.getMessage());

        }

        try {
            // Upload in a single request when file is small enough
            if (size <= 0) {
                throw new IllegalArgumentException(source + " is empty");
            }
            //upload video (less than chunkSize)
            if (size < CHUNK_SIZE) {
                FileInputStream inputStreamToFile = new FileInputStream(fileToUpload);
                HttpRequest request = Unirest.post(baseUri + "/videos/" + videoId + "/source")
                        .field("file", inputStreamToFile,
                               ContentType.APPLICATION_OCTET_STREAM, fileToUpload.getName())
                        .uploadMonitor((field, fileName, bytesWritten, totalBytes) ->
                                               listener.onProgress(field, fileName, bytesWritten, totalBytes, 0, 1));

                HttpResponse<JsonNode> responseSubmit = requestExecutor.executeJson(request);

                inputStreamToFile.close();
                return getVideoResponse(responseSubmit);
            } else {
                RandomAccessFile       randomAccessFile = new RandomAccessFile(source, "r");
                int                    copiedBytes      = 0;
                int                    chunkCount       = size / CHUNK_SIZE + 1;
                HttpResponse<JsonNode> responseSubmit   = null;
                for (int i = 0; i < chunkCount; i++) {

                    String chunkFileName = "upload-chunk-";
                    int    from          = copiedBytes;
                    copiedBytes = min(copiedBytes + CHUNK_SIZE, size);
                    int ChunkFileSize = copiedBytes - from;

                    String tmpdir = System.getProperty("java.io.tmpdir");
                    System.out.println(tmpdir);
                    final FileInputStream chunkStream = new FileInputStream(source);
                    chunkStream.skip(from);
                    byte[] b         = new byte[ChunkFileSize];
                    File   chunkFile = File.createTempFile(tmpdir, chunkFileName);

                    RandomAccessFile RandomAccessFileChunk = new RandomAccessFile(chunkFile, "rw");
                    randomAccessFile.readFully(b);
                    RandomAccessFileChunk.write(b, 0, ChunkFileSize);
                    final InputStream stream      = new FileInputStream(chunkFile);
                    String            rangeHeader = "bytes " + from + "-" + (copiedBytes - 1) + "/" + size;
                    HashMap<String, String> headers = new HashMap<String, String>() {{
                        put("Content-Range", rangeHeader);
                    }};
                    final float it = (float) 100 * (i) / (float) chunkCount;

                    HttpRequest request = Unirest.post(baseUri + "/videos/" + videoId + "/source")
                            .headers(headers)
                            .field("file", stream, ContentType.APPLICATION_OCTET_STREAM, chunkFile.getName())
                            .uploadMonitor(
                                    (field, fileName, bytesWritten, totalBytes) ->
                                            listener.onProgress(field, fileName, bytesWritten, totalBytes, it, chunkCount)
                            );

                    responseSubmit = requestExecutor.executeJson(request);
                    stream.close();

                    chunkFile.deleteOnExit();

                    System.out.println("Chunk" + (i + 1));

                }

                return getVideoResponse(responseSubmit);
            }
        } catch (IOException e) {
            throw new IllegalArgumentException(e.getMessage());
        }

    }


    public Video uploadThumbnail(Video video, String thumbnailSource) throws ResponseException, IllegalArgumentException {
        try {

            File            FileToUpload      = new File(thumbnailSource);
            FileInputStream inputStreamToFile = new FileInputStream(FileToUpload);
            HttpRequest request = Unirest.post(baseUri + "/videos/" + video.videoId + "/thumbnail").field("file", inputStreamToFile,
                                                                                                          ContentType.APPLICATION_OCTET_STREAM, FileToUpload.getName());

            //Post thumbnail
            HttpResponse<JsonNode> responseSubmit = requestExecutor.executeJson(request);

            inputStreamToFile.close();
            return getVideoResponse(responseSubmit);

        } catch (IOException e) {
            throw new IllegalArgumentException("uploadThumbnail -->Source :" + thumbnailSource + "\n Message-->" + e.getMessage());
        }
    }


    public Video updateThumbnailWithTimeCode(Video video, String timeCode) throws ResponseException {

        HttpRequest request = Unirest.patch(baseUri + "/videos/" + video.videoId).body(
                "{\"timecode\":\"" + timeCode + "\"}"
        );

        //Patch thumbnail
        HttpResponse<JsonNode> response = requestExecutor.executeJson(request);

        return getVideoResponse(response);


    }

    public Video update(Video video) throws ResponseException {


        HttpRequest request = Unirest.patch(baseUri + "/videos/" + video.videoId).body(serializer.serializeProperties(video));

        HttpResponse<JsonNode> response = requestExecutor.executeJson(request);

        return getVideoResponse(response);


    }


    public int delete(Video video) throws ResponseException {

        HttpRequest request = Unirest.delete(baseUri + "/videos/" + video.videoId);

        HttpResponse<JsonNode> response = requestExecutor.executeJson(request);

        return response.getStatus();
    }


    /////////////////////////Iterators//////////////////////////////

    public PageIterator<Video> list() throws ResponseException, IllegalArgumentException {

        QueryParams queryParams = new QueryParams();

        return new PageIterator<>(this, queryParams);
    }

    public PageIterator<Video> search(QueryParams queryParams) throws ResponseException, IllegalArgumentException {

        return new PageIterator<>(this, queryParams);
    }

    /////////////////////////Functions//////////////////////////////

    public String toString(Video video) {
        return serializer.serialize(video).toString();
    }

    public JSONObject toJSONObject(Video video) {
        return serializer.serialize(video);
    }

    private Video getVideoResponse(HttpResponse<JsonNode> response) throws ResponseException {
        try {
            return serializer.deserialize(response.getBody().getObject());
        } catch (NullPointerException e) {
            throw new ClientException(response, "Body's response does not containe any Object");
        }
    }

    @Override
    public Page<Video> load(QueryParams queryParams) throws ResponseException, IllegalArgumentException {

        String      url     = queryParams.create(baseUri + "/videos/");
        HttpRequest request = Unirest.get(url);

        HttpResponse<JsonNode> response = requestExecutor.executeJson(request);

        return new Page<>(
                serializer.deserialize(response.getBody().getObject().getJSONArray("data")),
                response.getBody().getObject().getJSONObject("pagination").getInt("pagesTotal"),
                response.getBody().getObject().getJSONObject("pagination").getInt("currentPage")
        );

    }
}
