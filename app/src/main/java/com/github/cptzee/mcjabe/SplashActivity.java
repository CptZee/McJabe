package com.github.cptzee.mcjabe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.github.cptzee.mcjabe.Data.Database.AccountHelper;
import com.github.cptzee.mcjabe.Data.Database.CredentialHelper;
import com.github.cptzee.mcjabe.Data.Database.FoodHelper;
import com.github.cptzee.mcjabe.Data.Database.FoodItemsHelper;
import com.github.cptzee.mcjabe.Data.Database.OrderHelper;
import com.github.cptzee.mcjabe.Data.Food;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        SharedPreferences pref = getSharedPreferences("FirstLaunch", MODE_PRIVATE);

        boolean firstLaunch = pref.getBoolean("first-launch", true);

        if(firstLaunch)
            setupDatabase(pref);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN );

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
                startActivity(intent);
                finish();

            }
        }, 3000);
    }

    private void setupDatabase(SharedPreferences pref){
        SharedPreferences.Editor editor = pref.edit();

        AccountHelper accountHelper = new AccountHelper(this);
        CredentialHelper credentialHelper = new CredentialHelper(this);
        FoodHelper foodHelper = new FoodHelper(this);
        FoodItemsHelper foodItemsHelper = new FoodItemsHelper(this);
        OrderHelper orderHelper = new OrderHelper(this);

        accountHelper.onCreate(accountHelper.getWritableDatabase());
        credentialHelper.onCreate(credentialHelper.getWritableDatabase());
        foodHelper.onCreate(foodHelper.getWritableDatabase());
        foodItemsHelper.onCreate(foodItemsHelper.getWritableDatabase());
        orderHelper.onCreate(orderHelper.getWritableDatabase());

        String[] foodNames = {"Puto", "Kare Kare", "Beef Empanada"};
        String[] foodDescriptions = {"There's no better way to start this guide on " +
                "Filipino desserts than with puto, one of the most famous dessert snacks " +
                "in the Philippines.",
                "Kare Kare is a traditional Filipino stew complimented with a thick savory peanut sauce.",
                " Try this Beef Empanada recipe with a savory filling and a buttery, flaky crust."};
        double[] foodPrices = {100.00, 300.00, 75.00};
        String[] foodCategories = {"Desert", "Main", "Appetizer"};

        for(int i = 1; i <= 3; i++){
            Food data = new Food();
            data.setName(foodNames[i]);
            data.setDescription(foodDescriptions[i]);
            data.setPrice(foodPrices[i]);
            data.setCategory(foodCategories[i]);
            foodHelper.insert(data);
        }

        editor.putBoolean("first-launch", false);
        editor.commit();
    }
}