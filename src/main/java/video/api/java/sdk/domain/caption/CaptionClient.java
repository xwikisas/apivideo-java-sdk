package video.api.java.sdk.domain.caption;

import video.api.java.sdk.domain.exception.ResponseException;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface CaptionClient {

    Caption get(String VideoId, String lang) throws ResponseException;

    List<Caption> getAll(String VideoId) throws ResponseException;

    Caption upload(String VideoId, File file, String lang) throws ResponseException, IOException;

    Caption updateDefault(String VideoId, String lang, boolean isDefault) throws ResponseException;

    void delete(String VideoId, String lang) throws ResponseException;


}
