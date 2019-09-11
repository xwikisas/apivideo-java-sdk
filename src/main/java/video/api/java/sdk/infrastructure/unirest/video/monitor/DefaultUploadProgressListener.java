package video.api.java.sdk.infrastructure.unirest.video.monitor;

public class DefaultUploadProgressListener implements UploadProgressListener {


    @Override
    public void onProgress(String field, String fileName, Long bytesWritten, Long totalBytes, float it, int len) {

        float chunkProgress = 100 * (float) bytesWritten / (float) totalBytes;
        if (len != 0 && len != 1) {
            System.out.print("\r progress for this chunk : " + chunkProgress + "%  -------------  ");
            System.out.print("progress for the complete video : " + (it + (chunkProgress) / (float) len) + "%");
        } else {
            System.out.print("\rVideo progress : " + chunkProgress + "%");
        }
    }
}
