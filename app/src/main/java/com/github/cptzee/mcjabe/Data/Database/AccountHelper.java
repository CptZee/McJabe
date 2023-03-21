package com.github.cptzee.mcjabe.Data.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import com.github.cptzee.mcjabe.Data.Account;

import java.util.ArrayList;
import java.util.List;

public class AccountHelper extends SQLiteOpenHelper {
    public AccountHelper(Context context) {
        super(context, "AccountDB", null, 1);
    }

    private final String TABLENAME = "Accounts";

    @Override
    public void onCreate(SQLiteDatabase db) {
        try{
            db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLENAME + "(" +
                    "id INTEGER," +
                    "firstName TEXT," +
                    "lastName TEXT," +
                    "contactNo TEXT," +
                    "email TEXT," +
                    "streetName TEXT," +
                    "barangay TEXT," +
                    "city TEXT," +
                    "province TEXT," +
                    "active INTEGER);");
        }catch (SQLiteException e){
            Log.e("Database", "Unable to create " + TABLENAME + " table.");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try{
            db.execSQL("DROP TABLE " + TABLENAME);
            onCreate(db);
        }catch (SQLiteException e){
            Log.e("Database", "Unable to upgrade " + TABLENAME + " table.");
        }
    }
    //CRUD
    public void insert(Account data){
        try{
            SQLiteDatabase db = this.getWritableDatabase();
            db.insert(TABLENAME, null, prepareData(data));
            db.close();
        }catch (SQLiteException e){
            Log.e("Database", "Unable to insert data to the " + TABLENAME + " table");
        }
    }

    public List<Account> get(){
        List<Account> list = new ArrayList<>();
        try{
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor reader = db.rawQuery("SELECT * FROM " + TABLENAME, null);
            while (reader.moveToNext())
                list.add(prepareData(reader));
            db.close();
        }catch (SQLiteException e){
            Log.e("Database", "Unable to retrieve data from the " + TABLENAME + " table");
        }
        return list;
    }

    public void update(Account data){
        try{
            SQLiteDatabase db = this.getWritableDatabase();
            String[] where = {String.valueOf(data.getId())};
            db.update(TABLENAME, prepareData(data), data.getId() + " = ? ", where);
            db.close();
        }catch (SQLiteException e){
            Log.e("Database", "Unable to update data from the " + TABLENAME + " table");
        }
    }

    public void remove(Account data){
        try{
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues content = new ContentValues();
            content.put("active", 0);
            String[] where = {String.valueOf(data.getId())};
            db.update(TABLENAME, content, data.getId() + " = ? ", where);
            db.close();
        }catch (SQLiteException e){
            Log.e("Database", "Unable to remove data from the " + TABLENAME + " table");
        }
    }

    private ContentValues prepareData(Account data){
        ContentValues content = new ContentValues();
        content.put("firstName", data.getFirstName());
        content.put("lastName", data.getLastName());
        content.put("contactNo", data.getContactNo());
        content.put("email", data.getEmail());
        content.put("streetName", data.getStreetName());
        content.put("barangay", data.getBarangay());
        content.put("city", data.getCity());
        content.put("province", data.getProvince());
        content.put("active", 1);
        return content;
    }

    private Account prepareData(Cursor cursor){
        Account data = new Account();
        data.setId(cursor.getInt(0));
        data.setFirstName(cursor.getString(1));
        data.setLastName(cursor.getString(2));
        data.setContactNo(cursor.getString(3));
        data.setEmail(cursor.getString(4));
        data.setStreetName(cursor.getString(5));
        data.setBarangay(cursor.getString(6));
        data.setCity(cursor.getString(7));
        data.setProvince(cursor.getString(8));
        return data;
    }
}
