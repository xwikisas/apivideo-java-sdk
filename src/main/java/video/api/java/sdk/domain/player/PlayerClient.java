package video.api.java.sdk.domain.player;

import video.api.java.sdk.domain.QueryParams;
import video.api.java.sdk.domain.exception.ResponseException;
import video.api.java.sdk.infrastructure.pagination.PageIterator;


public interface PlayerClient {

    Player get(String playerId) throws ResponseException;

    Player create(Player player) throws ResponseException;

    Player update(Player player) throws ResponseException;

    Player uploadLogo(String playerId, String logoSource, String link) throws ResponseException;

    int delete(String playerId) throws ResponseException;

    PageIterator<Player> list() throws ResponseException;

    PageIterator<Player> search(QueryParams queryParams) throws ResponseException, IllegalArgumentException;

}
