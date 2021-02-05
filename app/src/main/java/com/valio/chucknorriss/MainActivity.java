package com.valio.chucknorriss;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.android.volley.VolleyError;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    private NetworkManager networkManager;
    private String[] categories;

    void setCategories(String[] newCategories) {
        categories = newCategories;
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
        networkManager.getCategories(this, new CategoriesCallback() {
            @Override
            public void onSuccess(String[] categories) {
                setCategories(categories);
            }
            @Override
            public void onError(VolleyError error) {
                System.out.print(error.getLocalizedMessage());
            }
        });
    }
}