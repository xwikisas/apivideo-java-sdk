package video.api.java.sdk.domain.video;

import video.api.java.sdk.domain.QueryParams;
import video.api.java.sdk.domain.exception.ResponseException;
import video.api.java.sdk.infrastructure.pagination.PageIterator;
import video.api.java.sdk.infrastructure.unirest.video.monitor.UploadProgressListener;

import java.net.URISyntaxException;

public interface VideoClient {

    PageIterator<Video> list() throws ResponseException, IllegalArgumentException;

    Video get(String VideoId) throws ResponseException;

    Video create(Video video) throws ResponseException, IllegalArgumentException;

    Video upload(String source) throws ResponseException, IllegalArgumentException;

    Video upload(String source, Video video) throws ResponseException, IllegalArgumentException;

    Video upload(String source, UploadProgressListener m) throws ResponseException, IllegalArgumentException;

    Video upload(String source, Video video, UploadProgressListener m) throws ResponseException, IllegalArgumentException;
    //    Video createAndUpload(Video video, String source) throws  ResponseException, IllegalArgumentException;

    PageIterator search(QueryParams queryParams) throws ResponseException, IllegalArgumentException, URISyntaxException;

    Video uploadThumbnail(Video video, String thumbnailSource) throws ResponseException, IllegalArgumentException;

    Video updateThumbnailWithTimeCode(Video video, String timecode) throws ResponseException;

    Video update(Video video) throws ResponseException;

    int delete(Video video) throws ResponseException;


}
