package video.api.java.sdk.infrastructure.unirest.account;

import org.json.JSONException;
import org.json.JSONObject;
import video.api.java.sdk.domain.account.Account;
import video.api.java.sdk.infrastructure.unirest.serializer.JsonSerializer;

public class AccountJsonSerializer implements JsonSerializer<Account> {
    public Account deserialize(JSONObject data) throws JSONException {
        return new Account(
                data.has("quota") ? new Account.Quota(
                        data.getJSONObject("quota").getInt("quotaRemaining"),
                        data.getJSONObject("quota").getInt("quotaUsed"),
                        data.getJSONObject("quota").getInt("quotaTotal")
                ) : null
        );
    }
}
