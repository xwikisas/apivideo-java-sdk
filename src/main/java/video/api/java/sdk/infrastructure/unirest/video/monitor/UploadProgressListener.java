package video.api.java.sdk.infrastructure.unirest.video.monitor;

public interface UploadProgressListener {

    void onProgress(String field, String fileName, Long bytesWritten, Long totalBytes, float it, int len);

}
