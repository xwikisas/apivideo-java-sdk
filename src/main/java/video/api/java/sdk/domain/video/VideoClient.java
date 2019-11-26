package video.api.java.sdk.domain.video;

import video.api.java.sdk.domain.QueryParams;
import video.api.java.sdk.domain.exception.ResponseException;

import java.io.File;
import java.net.URISyntaxException;

public interface VideoClient {

    Iterable<Video> list() throws ResponseException;

    Video get(String videoId) throws ResponseException;

    Status getStatus(String videoId) throws ResponseException;

    Video create(Video video) throws ResponseException;

    Video upload(File file) throws ResponseException;

    Video upload(File file, Video video) throws ResponseException;

    Video upload(File file, UploadProgressListener m) throws ResponseException;

    Video upload(File file, Video video, UploadProgressListener m) throws ResponseException;

    Iterable<Video> search(QueryParams queryParams) throws ResponseException, URISyntaxException;

    Video uploadThumbnail(Video video, File file) throws ResponseException;

    Video updateThumbnail(Video video, String timecode) throws ResponseException;

    Video update(Video video) throws ResponseException;

    void delete(Video video) throws ResponseException;
}
