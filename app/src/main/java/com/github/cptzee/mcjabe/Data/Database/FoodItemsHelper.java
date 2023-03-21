package com.github.cptzee.mcjabe.Data.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.github.cptzee.mcjabe.Data.FoodItems;

import java.util.ArrayList;
import java.util.List;

public class FoodItemsHelper  extends SQLiteOpenHelper {
    public FoodItemsHelper(Context context) {
        super(context, "FoodItemsDB", null, 1);
    }

    private final String TABLENAME = "FoodItems";

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLENAME + "(" +
                    "id INTEGER," +
                    "foodID INTEGER," +
                    "quantity INTEGER," +
                    "orderID INTEGER," +
                    "active INTEGER);");
        } catch (SQLiteException e) {
            Log.e("Database", "Unable to create " + TABLENAME + " table.");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            db.execSQL("DROP TABLE " + TABLENAME);
            onCreate(db);
        } catch (SQLiteException e) {
            Log.e("Database", "Unable to upgrade " + TABLENAME + " table.");
        }
    }

    //CRUD
    public void insert(FoodItems data) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            db.insert(TABLENAME, null, prepareData(data));
            db.close();
        } catch (SQLiteException e) {
            Log.e("Database", "Unable to insert data to the " + TABLENAME + " table");
        }
    }

    public List<FoodItems> get() {
        List<FoodItems> list = new ArrayList<>();
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor reader = db.rawQuery("SELECT * FROM " + TABLENAME, null);
            while (reader.moveToNext())
                list.add(prepareData(reader));
            db.close();
        } catch (SQLiteException e) {
            Log.e("Database", "Unable to retrieve data from the " + TABLENAME + " table");
        }
        return list;
    }

    public void update(FoodItems data) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            String[] where = {String.valueOf(data.getId())};
            db.update(TABLENAME, prepareData(data), data.getId() + " = ? ", where);
            db.close();
        } catch (SQLiteException e) {
            Log.e("Database", "Unable to update data from the " + TABLENAME + " table");
        }
    }

    public void remove(FoodItems data) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues content = new ContentValues();
            content.put("active", 0);
            String[] where = {String.valueOf(data.getId())};
            db.update(TABLENAME, content, data.getId() + " = ? ", where);
            db.close();
        } catch (SQLiteException e) {
            Log.e("Database", "Unable to remove data from the " + TABLENAME + " table");
        }
    }

    private ContentValues prepareData(FoodItems data) {
        ContentValues content = new ContentValues();
        content.put("foodID", data.getFoodID());
        content.put("quantity", data.getQuantity());
        content.put("orderID", data.getOrderID());
        content.put("active", 1);
        return content;
    }

    private FoodItems prepareData(Cursor cursor) {
        FoodItems data = new FoodItems();
        data.setId(cursor.getInt(0));
        data.setFoodID(cursor.getInt(1));
        data.setQuantity(cursor.getInt(2));
        data.setOrderID(cursor.getInt(3));
        return data;
    }
}