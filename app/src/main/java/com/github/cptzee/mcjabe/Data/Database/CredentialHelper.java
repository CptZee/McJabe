package com.github.cptzee.mcjabe.Data.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.github.cptzee.mcjabe.Data.Account;
import com.github.cptzee.mcjabe.Data.Credential;

import java.util.ArrayList;
import java.util.List;

public class CredentialHelper extends SQLiteOpenHelper {
    public CredentialHelper(Context context) {
        super(context, "CredentialDB", null, 1);
    }

    private final String TABLENAME = "Credentials";

    @Override
    public void onCreate(SQLiteDatabase db) {
        try{
            db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLENAME + "(" +
                    "id INTEGER," +
                    "accountID INTEGER," +
                    "username TEXT," +
                    "password TEXT," +
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
    public void insert(Credential data){
        try{
            SQLiteDatabase db = this.getWritableDatabase();
            db.insert(TABLENAME, null, prepareData(data));
            db.close();
        }catch (SQLiteException e){
            Log.e("Database", "Unable to insert data to the " + TABLENAME + " table");
        }
    }

    public List<Credential> get(){
        List<Credential> list = new ArrayList<>();
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

    public void update(Credential data){
        try{
            SQLiteDatabase db = this.getWritableDatabase();
            String[] where = {String.valueOf(data.getId())};
            db.update(TABLENAME, prepareData(data), data.getId() + " = ? ", where);
            db.close();
        }catch (SQLiteException e){
            Log.e("Database", "Unable to update data from the " + TABLENAME + " table");
        }
    }

    public void remove(Credential data){
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

    private ContentValues prepareData(Credential data){
        ContentValues content = new ContentValues();
        content.put("accountID", data.getAccountID());
        content.put("username", data.getUsername());
        content.put("password", data.getPassword());
        content.put("active", 1);
        return content;
    }

    private Credential prepareData(Cursor cursor){
        Credential data = new Credential();
        data.setId(cursor.getInt(0));
        data.setAccountID(cursor.getInt(1));
        data.setUsername(cursor.getString(2));
        data.setPassword(cursor.getString(3));
        return data;
    }
}

