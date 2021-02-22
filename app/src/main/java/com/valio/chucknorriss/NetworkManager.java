package com.valio.chucknorriss;

import android.content.Context;
import android.graphics.Bitmap;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

interface CategoriesCallback {
    void onSuccess(String[] categories);
    void onError(VolleyError error);
}

interface JokeCallback {
    void onSuccess(Joke joke);
    void onError(VolleyError error);
}

interface BitmapCallback {
    void onSuccess(Bitmap bitmap);
    void onError(VolleyError error);
}

public class NetworkManager {

    private RequestQueue queue;
    private String apiURL = "https://api.chucknorris.io/jokes/";

    protected void getCategories(Context context, final CategoriesCallback callback) {
        String url = String.format("%scategories", apiURL);
        if (queue == null) {
            queue = Volley.newRequestQueue(context);
        }
        JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                String[] list = new String[response.length()];
                for(int i = 0; i < response.length(); i++){
                    list[i] = response.optString(i);
                }
                callback.onSuccess(list);
            }
        }, null);
        // Add the request to the RequestQueue.
        queue.add(request);
    }

    protected void getJoke(Context context, String category, final JokeCallback callback) {
        String url = String.format("%srandom?category=%s", apiURL, category);
        if (queue == null) {
            queue = Volley.newRequestQueue(context);
        }
        // Request a string response from the provided URL.
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {
                            Joke joke = new Joke(jsonObject);
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

    protected void getBitmap(Context context, String url, final BitmapCallback callback) {
        if (queue == null) {
            queue = Volley.newRequestQueue(context);
        }
        // Request a string response from the provided URL.
        ImageRequest request = new ImageRequest(url,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap bitmap) {
                        callback.onSuccess(bitmap);
                    }
                }, 0, 0, null,
                new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                    }
                });
        // Add the request to the RequestQueue.
        queue.add(request);
    }
}
