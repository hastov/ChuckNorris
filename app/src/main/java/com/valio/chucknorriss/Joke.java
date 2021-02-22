package com.valio.chucknorriss;

import org.json.JSONException;
import org.json.JSONObject;

public class Joke extends Saying {

    String iconURL;
    Mood mood = Mood.FUNNY;

    Joke(JSONObject jsonObject) throws JSONException {
        try {
            text = jsonObject.getString("value");
            iconURL = jsonObject.getString("icon_url");
        } catch (JSONException exception) {
            throw exception;
        }
    }
}
