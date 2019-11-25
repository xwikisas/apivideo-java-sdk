package video.api.java.sdk.infrastructure.unirest.player;

import kong.unirest.HttpRequest;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import video.api.java.sdk.domain.QueryParams;
import video.api.java.sdk.domain.RequestExecutor;
import video.api.java.sdk.domain.exception.ResponseException;
import video.api.java.sdk.domain.player.Player;
import video.api.java.sdk.infrastructure.pagination.IteratorIterable;
import video.api.java.sdk.infrastructure.pagination.PageIterator;
import video.api.java.sdk.infrastructure.unirest.RequestFactory;
import video.api.java.sdk.infrastructure.unirest.pagination.UriPageLoader;
import video.api.java.sdk.infrastructure.unirest.serializer.JsonSerializer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static kong.unirest.HttpMethod.*;

public class PlayerClient implements video.api.java.sdk.domain.player.PlayerClient {

    private final RequestFactory         requestFactory;
    private final JsonSerializer<Player> serializer;
    private final RequestExecutor        requestExecutor;

    public PlayerClient(RequestFactory requestFactory, JsonSerializer<Player> serializer, RequestExecutor requestExecutor) {
        this.requestFactory  = requestFactory;
        this.serializer      = serializer;
        this.requestExecutor = requestExecutor;
    }

    public Player get(String playerId) throws ResponseException {

        HttpRequest request = requestFactory.create(GET, "/players/" + playerId);

        HttpResponse<JsonNode> response = requestExecutor.executeJson(request);

        return getPlayerResponse(response);

    }

    public Player create(Player player) throws ResponseException {

        HttpRequest request = requestFactory.create(POST, "/players").body(serializer.serialize(player));

        HttpResponse<JsonNode> response = requestExecutor.executeJson(request);

        player = getPlayerResponse(response);
        return player;

    }


    public Player update(Player player) throws ResponseException {

        HttpRequest request = requestFactory.create(PATCH, "/players/" + player.playerId).body(serializer.serialize(player));

        HttpResponse<JsonNode> response = requestExecutor.executeJson(request);

        return getPlayerResponse(response);

    }

    public Player uploadLogo(String playerId, String logoSource, String link) throws ResponseException {
        try {


            File            FileToUpload      = new File(logoSource);
            FileInputStream inputStreamToFile = new FileInputStream(FileToUpload);
            HttpRequest request = requestFactory.create(POST, "/players/" + playerId + "/logo").field(
                    "file", inputStreamToFile,
                    kong.unirest.ContentType.APPLICATION_OCTET_STREAM, FileToUpload.getName())
                    .field("link", link);

            HttpResponse<JsonNode> responseSubmit = requestExecutor.executeJson(request);

            inputStreamToFile.close();
            return getPlayerResponse(responseSubmit);


        } catch (IOException e) {
            throw new IllegalArgumentException("uploadThumbnail : " + e.getMessage());
        }

    }


    public void delete(String playerId) throws ResponseException {
        HttpRequest request = requestFactory.create(DELETE, "/players/" + playerId);

        requestExecutor.executeJson(request);
    }

    /////////////////////////Iterators//////////////////////////////


    public Iterable<Player> list() throws ResponseException, IllegalArgumentException {
        return search(new QueryParams());
    }

    public Iterable<Player> search(QueryParams queryParams) throws ResponseException, IllegalArgumentException {
        return new IteratorIterable<>(new PageIterator<>(new UriPageLoader<>(
                "/players",
                requestFactory,
                requestExecutor,
                serializer
        ), queryParams));
    }


    /////////////////////////Functions//////////////////////////////

    private Player getPlayerResponse(HttpResponse<JsonNode> response) {
        return serializer.deserialize(response.getBody().getObject());
    }

}
