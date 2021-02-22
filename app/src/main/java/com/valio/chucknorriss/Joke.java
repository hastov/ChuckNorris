package com.valio.chucknorriss;

import org.json.JSONException;
import org.json.JSONObject;

public class Joke extends Saying {

    String iconURL;
    Mood mood = Mood.FUNNY;

    Joke(JSONObject jsonObject) {
        text = jsonObject.optString("value");
        iconURL = jsonObject.optString("icon_url");
    }
}
