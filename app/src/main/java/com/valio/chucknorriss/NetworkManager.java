package com.valio.chucknorriss;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

interface CategoriesCallback {
    void onSuccess(String[] categories);
    void onError(VolleyError error);
}

interface JokeCallback {
    void onSuccess(String joke);
    void onError(VolleyError error);
}

public class NetworkManager {

    private RequestQueue queue;

    protected void getCategories(Context context, final CategoriesCallback callback) {
        String url = "https://api.chucknorris.io/jokes/categories";
        if (queue == null) {
            queue = Volley.newRequestQueue(context);
        }
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String noBrackets = response.replace("[", "").replace("]","")
                                .replace("\"","");
                        callback.onSuccess(noBrackets.split(","));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onError(error);
            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    protected void getJoke(Context context, String category, final JokeCallback callback) {
        String url = String.format("https://api.chucknorris.io/jokes/random?category=%s", category);
        if (queue == null) {
            queue = Volley.newRequestQueue(context);
        }
        // Request a string response from the provided URL.
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String joke = response.getString("value");
                            callback.onSuccess(joke);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                    }
                });
        // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest);
    }
}