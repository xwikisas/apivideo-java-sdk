package video.api.java.sdk.infrastructure.unirest.player;

import kong.unirest.HttpRequest;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import org.json.JSONObject;
import video.api.java.sdk.domain.QueryParams;
import video.api.java.sdk.domain.RequestExecutor;
import video.api.java.sdk.domain.exception.ResponseException;
import video.api.java.sdk.domain.pagination.Page;
import video.api.java.sdk.domain.player.Player;
import video.api.java.sdk.infrastructure.pagination.IteratorIterable;
import video.api.java.sdk.infrastructure.pagination.PageIterator;
import video.api.java.sdk.infrastructure.pagination.PageLoader;
import video.api.java.sdk.infrastructure.unirest.serializer.JsonSerializer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class PlayerClient implements video.api.java.sdk.domain.player.PlayerClient, PageLoader<Player> {

    private final JsonSerializer<Player> serializer;
    private final RequestExecutor        requestExecutor;
    private final String                 baseUri;

    public PlayerClient(JsonSerializer<Player> serializer, RequestExecutor requestExecutor, String baseUri) {
        this.serializer      = serializer;
        this.requestExecutor = requestExecutor;
        this.baseUri         = baseUri;
    }


    public Player get(String playerId) throws ResponseException {

        HttpRequest request = Unirest.get(baseUri + "/players/" + playerId);

        HttpResponse<JsonNode> response = requestExecutor.executeJson(request);

        return getPlayerResponse(response);

    }

    public Player create(Player player) throws ResponseException {

        HttpRequest request = Unirest.post(baseUri + "/players").body(serializer.serialize(player));

        HttpResponse<JsonNode> response = requestExecutor.executeJson(request);

        player = getPlayerResponse(response);
        return player;

    }


    public Player update(Player player) throws ResponseException {

        HttpRequest request = Unirest.patch(baseUri + "/players/" + player.playerId).body(serializer.serialize(player));

        HttpResponse<JsonNode> response = requestExecutor.executeJson(request);

        return getPlayerResponse(response);

    }

    public Player uploadLogo(String playerId, String logoSource, String link) throws ResponseException {
        try {


            File            FileToUpload      = new File(logoSource);
            FileInputStream inputStreamToFile = new FileInputStream(FileToUpload);
            HttpRequest request = Unirest.post(baseUri + "/players/" + playerId + "/logo").field(
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


    public int delete(String playerId) throws ResponseException {

        HttpRequest request = Unirest.delete(baseUri + "/players/" + playerId);

        HttpResponse<JsonNode> response = requestExecutor.executeJson(request);

        return response.getStatus();
    }
    /////////////////////////Iterators//////////////////////////////


    public Iterable<Player> list() throws ResponseException, IllegalArgumentException {

        QueryParams queryParams = new QueryParams();
        return new IteratorIterable<>(new PageIterator<>(this, queryParams));
    }

    public Iterable<Player> search(QueryParams queryParams) throws ResponseException, IllegalArgumentException {

        return new IteratorIterable<>(new PageIterator<>(this, queryParams));
    }


    /////////////////////////Functions//////////////////////////////

    private Player getPlayerResponse(HttpResponse<JsonNode> response) {
        return serializer.deserialize(response.getBody().getObject());
    }


    @Override
    public Page<Player> load(QueryParams queryParams) throws ResponseException {
        String      url     = queryParams.create(baseUri + "/players/");
        HttpRequest request = Unirest.get(url);

        HttpResponse<JsonNode> response = requestExecutor.executeJson(request);

        return new Page<>(
                serializer.deserialize(response.getBody().getObject().getJSONArray("data")),
                response.getBody().getObject().getJSONObject("pagination").getInt("pagesTotal"),
                response.getBody().getObject().getJSONObject("pagination").getInt("currentPage")
        );


    }

}
