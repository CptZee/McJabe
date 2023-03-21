package com.github.cptzee.mcjabe.Data.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.github.cptzee.mcjabe.Data.Food;

import java.util.ArrayList;
import java.util.List;

public class FoodHelper extends SQLiteOpenHelper {
    public FoodHelper(Context context) {
        super(context, "FoodDB", null, 1);
    }

    private final String TABLENAME = "Foods";

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLENAME + "(" +
                    "id INTEGER," +
                    "name TEXT," +
                    "description TEXT," +
                    "price REAL," +
                    "category TEXT," +
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
    public void insert(Food data) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            db.insert(TABLENAME, null, prepareData(data));
            db.close();
        } catch (SQLiteException e) {
            Log.e("Database", "Unable to insert data to the " + TABLENAME + " table");
        }
    }

    public List<Food> get() {
        List<Food> list = new ArrayList<>();
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor reader = db.rawQuery("SELECT * FROM " + TABLENAME, null);
            while (reader.moveToNext())
                list.add(prepareData(reader));
            //db.close();
        } catch (SQLiteException e) {
            Log.e("Database", "Unable to retrieve data from the " + TABLENAME + " table");
        }
        return list;
    }

    public void update(Food data) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            String[] where = {String.valueOf(data.getId())};
            db.update(TABLENAME, prepareData(data), data.getId() + " = ? ", where);
            db.close();
        } catch (SQLiteException e) {
            Log.e("Database", "Unable to update data from the " + TABLENAME + " table");
        }
    }

    public void remove(Food data) {
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

    private ContentValues prepareData(Food data) {
        ContentValues content = new ContentValues();
        content.put("name", data.getName());
        content.put("description", data.getDescription());
        content.put("price", data.getPrice());
        content.put("category", data.getCategory());
        content.put("active", 1);
        return content;
    }

    private Food prepareData(Cursor cursor) {
        Food data = new Food();
        data.setId(cursor.getInt(0));
        data.setName(cursor.getString(1));
        data.setDescription(cursor.getString(2));
        data.setPrice(cursor.getDouble(3));
        data.setCategory(cursor.getString(4));
        return data;
    }
}