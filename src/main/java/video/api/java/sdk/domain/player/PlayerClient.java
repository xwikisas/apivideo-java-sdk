package video.api.java.sdk.domain.player;

import video.api.java.sdk.domain.QueryParams;
import video.api.java.sdk.domain.exception.ResponseException;


public interface PlayerClient {

    Player get(String playerId) throws ResponseException;

    Player create(Player player) throws ResponseException;

    Player update(Player player) throws ResponseException;

    Player uploadLogo(String playerId, String logoSource, String link) throws ResponseException;

    void delete(String playerId) throws ResponseException;

    Iterable<Player> list() throws ResponseException;

    Iterable<Player> search(QueryParams queryParams) throws ResponseException, IllegalArgumentException;

}
