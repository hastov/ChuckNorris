package com.valio.chucknorriss;

import android.content.Context;
import android.graphics.Bitmap;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

interface CategoriesListener {
    void onResponse(String[] categories);
}

interface JokeListener {
    void onResponse(Joke joke);
}

interface BitmapListener {
    void onResponse(Bitmap bitmap);
}

public class NetworkManager {

    private RequestQueue queue;
    private String apiURL = "https://api.chucknorris.io/jokes/";

    protected void getCategories(Context context, final CategoriesListener listener) {
        String url = String.format("%scategories", apiURL);
        // Check if a queue for requests has already been initialized
        if (queue == null) {
            queue = Volley.newRequestQueue(context);
        }
        // An interface object whose overridden method will be called by the library when it
        // receives a response. This way the code can be executed after a network delay.
        Response.Listener<JSONArray> responseListener = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                // Create a more abstract array of strings from the response to be used on the UI
                String[] list = new String[response.length()];
                for(int i = 0; i < response.length(); i++){
                    list[i] = response.optString(i);
                }
                // Call the parameter's method (predefined code) by passing it the array parameter
                listener.onResponse(list);
            }
        };
        JsonArrayRequest request = new JsonArrayRequest(url, responseListener, null);
        // Add the request to the RequestQueue which will then execute it
        queue.add(request);
    }

    protected void getJoke(Context context, String category, final JokeListener listener) {
        String url = String.format("%srandom?category=%s", apiURL, category);
        if (queue == null) {
            queue = Volley.newRequestQueue(context);
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        Joke joke = new Joke(jsonObject);
                        listener.onResponse(joke);
                    }
                }, null);
        // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest);
    }

    protected void getBitmap(Context context, String url, final BitmapListener listener) {
        if (queue == null) {
            queue = Volley.newRequestQueue(context);
        }
        ImageRequest request = new ImageRequest(url,
                // New response listener interface object that is passed to the network library.
                // Its overridden method will be called by the library when it receives a response
                // from the server.
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap bitmap) {
                        listener.onResponse(bitmap);
                    }
                }, 0, 0, null, null);
        // Add the request to the RequestQueue.
        queue.add(request);
    }
}
