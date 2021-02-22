package com.valio.chucknorriss;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.android.volley.VolleyError;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    private NetworkManager networkManager;

    void viewCategories(String[] categories) {
        recyclerView = findViewById(R.id.recyclerView);
        CategoriesAdapter adapter = new CategoriesAdapter(this, categories);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (networkManager == null) {
            networkManager = new NetworkManager();
        }

        // An interface object whose overridden method should be called by the NetworkManager when
        // it parses the response
        CategoriesCallback callback = new CategoriesCallback() {
            @Override
            public void onSuccess(String[] categories) {
                viewCategories(categories);
            }
        };
        networkManager.getCategories(this, callback);
    }
}