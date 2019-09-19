package video.api.java.sdk.domain.video;

public interface UploadProgressListener {

    void onProgress(String field, String fileName, Long bytesWritten, Long totalBytes, float it, int len);

}
