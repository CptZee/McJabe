package com.github.cptzee.mcjabe;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.cptzee.mcjabe.Data.Adapter.FoodAdapter;
import com.github.cptzee.mcjabe.Data.Database.FoodHelper;

import java.util.ArrayList;

public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);

        FoodHelper foodHelper = new FoodHelper(this);
        FoodAdapter foodAdapter = new FoodAdapter(foodHelper.get());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(foodAdapter);

    }
}