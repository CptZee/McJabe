package com.github.cptzee.mcjabe;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.cptzee.mcjabe.Data.Database.Adapter.FoodAdapter;
import com.github.cptzee.mcjabe.Data.Database.FoodHelper;
import com.github.cptzee.mcjabe.Data.Food;

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

        for(Food food: foodHelper.get()){
            Log.i("Food List", food.getName());
        }
    }
}