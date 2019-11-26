package video.api.java.sdk.infrastructure.unirest.player;

import kong.unirest.HttpRequest;
import kong.unirest.JsonNode;
import video.api.java.sdk.domain.QueryParams;
import video.api.java.sdk.domain.exception.ResponseException;
import video.api.java.sdk.domain.player.Player;
import video.api.java.sdk.infrastructure.pagination.IteratorIterable;
import video.api.java.sdk.infrastructure.pagination.PageIterator;
import video.api.java.sdk.infrastructure.unirest.RequestBuilder;
import video.api.java.sdk.infrastructure.unirest.RequestExecutor;
import video.api.java.sdk.infrastructure.unirest.pagination.UriPageLoader;
import video.api.java.sdk.infrastructure.unirest.serializer.JsonSerializer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class PlayerClient implements video.api.java.sdk.domain.player.PlayerClient {

    private final RequestBuilder         requestBuilder;
    private final JsonSerializer<Player> serializer;
    private final RequestExecutor        requestExecutor;

    public PlayerClient(RequestBuilder requestBuilder, JsonSerializer<Player> serializer, RequestExecutor requestExecutor) {
        this.requestBuilder  = requestBuilder;
        this.serializer      = serializer;
        this.requestExecutor = requestExecutor;
    }

    public Player get(String playerId) throws ResponseException {

        HttpRequest request = requestBuilder.get("/players/" + playerId);

        JsonNode responseBody = requestExecutor.executeJson(request);

        return serializer.deserialize(responseBody.getObject());

    }

    public Player create(Player player) throws ResponseException {

        HttpRequest request = requestBuilder.post("/players")
                .body(serializer.serialize(player));

        JsonNode responseBody = requestExecutor.executeJson(request);

        return serializer.deserialize(responseBody.getObject());
    }

    public Player update(Player player) throws ResponseException {

        HttpRequest request = requestBuilder.patch("/players/" + player.playerId)
                .body(serializer.serialize(player));

        JsonNode responseBody = requestExecutor.executeJson(request);

        return serializer.deserialize(responseBody.getObject());
    }

    public Player uploadLogo(String playerId, File file, String link) throws ResponseException {
        try (FileInputStream inputStreamToFile = new FileInputStream(file)) {
            HttpRequest request = requestBuilder.post("/players/" + playerId + "/logo")
                    .field("file", inputStreamToFile, file.getName())
                    .field("link", link);

            JsonNode responseBody = requestExecutor.executeJson(request);

            inputStreamToFile.close();

            return serializer.deserialize(responseBody.getObject());

        } catch (IOException e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }
    }


    public void delete(String playerId) throws ResponseException {
        HttpRequest request = requestBuilder.delete("/players/" + playerId);

        requestExecutor.executeJson(request);
    }

    /////////////////////////Iterators//////////////////////////////


    public Iterable<Player> list() throws ResponseException, IllegalArgumentException {
        return search(new QueryParams());
    }

    public Iterable<Player> search(QueryParams queryParams) throws ResponseException, IllegalArgumentException {
        return new IteratorIterable<>(new PageIterator<>(new UriPageLoader<>(
                "/players",
                requestBuilder,
                requestExecutor,
                serializer
        ), queryParams));
    }

}
