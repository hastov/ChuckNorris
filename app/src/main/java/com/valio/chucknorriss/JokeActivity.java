package com.valio.chucknorriss;

import androidx.appcompat.app.AppCompatActivity;

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
            fetchJoke(category);
        }
    }

    private void fetchJoke(String category) {
        networkManager = new NetworkManager();
        networkManager.getJoke(this, category, new JokeCallback() {
            @Override
            public void onSuccess(String joke) {
                jokeTextView.setText(joke);
            }
            @Override
            public void onError(VolleyError error) {
                System.out.print(error.getLocalizedMessage());
            }
        });
    }
}