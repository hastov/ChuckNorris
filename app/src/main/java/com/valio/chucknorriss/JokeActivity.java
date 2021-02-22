package com.valio.chucknorriss;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.net.Network;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;

public class JokeActivity extends AppCompatActivity {

    private ImageView jokeImageView;
    private TextView jokeTextView;
    private NetworkManager networkManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke);
        jokeImageView = findViewById(R.id.jokeImageView);
        jokeTextView = findViewById(R.id.jokeTextView);
        String category = getIntent().getStringExtra("category");
        if (category != null) {
            setTitle(category);
            getJoke(category);
        }
    }

    private void getJoke(String category) {
        networkManager = new NetworkManager();
        networkManager.getJoke(this, category, new JokeCallback() {
            @Override
            public void onSuccess(Joke joke) {
                jokeTextView.setText(joke.text);
                getBitmap(joke.iconURL);
            }
            @Override
            public void onError(VolleyError error) {
                System.out.print(error.getLocalizedMessage());
            }
        });
    }

    private void getBitmap(String url) {
        networkManager.getBitmap(this, url, new BitmapCallback() {
            @Override
            public void onSuccess(Bitmap bitmap) {
                jokeImageView.setImageBitmap(bitmap);
            }

            @Override
            public void onError(VolleyError error) {

            }
        });
    }
}