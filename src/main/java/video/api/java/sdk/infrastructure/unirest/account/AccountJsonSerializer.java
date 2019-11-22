package video.api.java.sdk.infrastructure.unirest.account;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import video.api.java.sdk.domain.account.Account;
import video.api.java.sdk.infrastructure.unirest.serializer.JsonSerializer;

import java.util.List;

public class AccountJsonSerializer implements JsonSerializer<Account> {


    public Account deserialize(JSONObject data) throws JSONException {

        Account account = new Account();
        if (data.has("quota")) {
            account.quota.quotaRemaining = data.getJSONObject("quota").getInt("quotaRemaining");
            account.quota.quotaUsed      = data.getJSONObject("quota").getInt("quotaUsed");
            account.quota.quotaTotal     = data.getJSONObject("quota").getInt("quotaTotal");
        }
        if (data.has("term")) {
            account.term.startAt = data.getJSONObject("term").getString("startAt");
            account.term.endAt   = data.getJSONObject("term").getString("endAt");
        }
        return account;
    }

    @Override
    public List<Account> deserialize(JSONArray data) throws JSONException {
        return null;
    }

    public JSONObject serialize(Account object) throws JSONException {

        JSONObject data = new JSONObject();

        data.put("quota", new JSONObject() {{
            put("quotaRemaining", object.quota.quotaRemaining);
            put("quotaUsed", object.quota.quotaUsed);
            put("quotaTotal", object.quota.quotaTotal);
        }});

        data.put("term", new JSONObject() {{
            put("startAt", object.term.startAt);
            put("endAt", object.term.endAt);
        }});

        return data;
    }

}
