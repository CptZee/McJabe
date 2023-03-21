package com.github.cptzee.mcjabe;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        ArrayList<Items> movieArrayList = new ArrayList<Items>();
        movieArrayList.add(new Items("Item 1", "Item Description", R.drawable.ic_launcher_background));
        movieArrayList.add(new Items("Item 2", "Item Description", R.drawable.ic_launcher_background));
        movieArrayList.add(new Items("Item 3", "Item Description", R.drawable.ic_launcher_background));

        ItemsAdapter itemsAdapter = new ItemsAdapter(this, movieArrayList);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(itemsAdapter);

    }
}