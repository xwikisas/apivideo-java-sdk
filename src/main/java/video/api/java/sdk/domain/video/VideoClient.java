package video.api.java.sdk.domain.video;

import video.api.java.sdk.domain.QueryParams;
import video.api.java.sdk.domain.exception.ResponseException;

import java.net.URISyntaxException;

public interface VideoClient {

    Iterable<Video> list() throws ResponseException;

    Video get(String VideoId) throws ResponseException;

    Video create(Video video) throws ResponseException;

    Video upload(String source) throws ResponseException;

    Video upload(String source, Video video) throws ResponseException;

    Video upload(String source, UploadProgressListener m) throws ResponseException;

    Video upload(String source, Video video, UploadProgressListener m) throws ResponseException;

    Iterable<Video> search(QueryParams queryParams) throws ResponseException, URISyntaxException;

    Video uploadThumbnail(Video video, String thumbnailSource) throws ResponseException;

    Video updateThumbnailWithTimeCode(Video video, String timecode) throws ResponseException;

    Video update(Video video) throws ResponseException;

    void delete(Video video) throws ResponseException;
}
