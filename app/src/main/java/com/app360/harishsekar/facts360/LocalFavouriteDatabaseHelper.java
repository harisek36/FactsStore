package com.app360.harishsekar.facts360;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by harishsekar on 7/20/17.
 */

public class LocalFavouriteDatabaseHelper extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = "LikedFacts_localDB.db";
    private static final int DATABASE_VERSION = 1;

    public static final String LOCAL_FACTS_TABLE_NAME = "local_facts_table";

    public static final String LOCAL_FACTS_COLUMN_NAME_ID = "_id";
    public static final String LOCAL_FACTS_COLUMN_NAME_FACTS = "liked_facts";
    public static final String LOCAL_FACTS_COLUMN_NAME_STORED_INDEX_POSITION_NUMBER = "index_position";
//    public static ArrayList<String> cursor_list =  new ArrayList<>();

    public static int index = 0;


    public LocalFavouriteDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory,DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String SQL_CREATE_LOCAL_LIKED_FACTS_TABLE =
                " CREATE TABLE " + LOCAL_FACTS_TABLE_NAME + " ( " +
                        LOCAL_FACTS_COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        LOCAL_FACTS_COLUMN_NAME_FACTS + " TEXT NOT NULL" +

                        " ); ";

        sqLiteDatabase.execSQL(SQL_CREATE_LOCAL_LIKED_FACTS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        final String SQL_DELETE_LOCAL_LIKED_FACTS_ENTRIES =  " DROP TABLE IF EXISTS " + LOCAL_FACTS_TABLE_NAME;
        sqLiteDatabase.execSQL(SQL_DELETE_LOCAL_LIKED_FACTS_ENTRIES);
        onCreate(sqLiteDatabase);

    }

    public boolean isFacts_in_likedDB(String check_fact){


        final String fact_check_query = " SELECT * FROM " + LOCAL_FACTS_TABLE_NAME + " WHERE " + LOCAL_FACTS_COLUMN_NAME_FACTS + "=\"" + check_fact + "\"; ";

        SQLiteDatabase sqLiteDatabase;
        sqLiteDatabase = getWritableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery(fact_check_query,null);

        if((cursor.getCount()== 1)){

            cursor.close();
            sqLiteDatabase.close();
            return true;
        }
        else{
            cursor.close();
            sqLiteDatabase.close();
            return false;
        }

    }


    // to insert into a favourite fact into local SQLite Database

    public boolean check_for_duplicate_insertion_of_Fact(FactsBroker factsBroker){

        final String check_fact = factsBroker.getBroker_Facts();

        final String duplicate_fact_check_query = " SELECT * FROM " + LOCAL_FACTS_TABLE_NAME + " WHERE " + LOCAL_FACTS_COLUMN_NAME_FACTS + "=\"" + check_fact + "\"; ";

        SQLiteDatabase sqLiteDatabase;
        sqLiteDatabase = getWritableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery(duplicate_fact_check_query,null);

        if(cursor.getCount() == 0){
            insert_favourite_facts_into_local_db(factsBroker);
            
            cursor.close();
            sqLiteDatabase.close();
            return true;
        }
        else{
            cursor.close();
            sqLiteDatabase.close();
            return false;
        }

    }

    public void insert_favourite_facts_into_local_db(FactsBroker factsBroker){

        SQLiteDatabase sqLiteDatabase;
        ContentValues contentValues = new ContentValues();

        contentValues.put(LOCAL_FACTS_COLUMN_NAME_FACTS,factsBroker.getBroker_Facts());

        sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.insert(LOCAL_FACTS_TABLE_NAME,null,contentValues);
        sqLiteDatabase.close();
    }

    // to remove a fact from the favourite list ( Local DB)

    public boolean delete_from_Table(String fact_tobe_deleted){

        String DELETE_FACT_QUERY =               " DELETE FROM " + LOCAL_FACTS_TABLE_NAME + " WHERE " + LOCAL_FACTS_COLUMN_NAME_FACTS + "=\"" + fact_tobe_deleted + "\";";
        String SELECT_BEFORE_DELETE_FACT_QUERY = " SELECT * FROM " + LOCAL_FACTS_TABLE_NAME + " WHERE " + LOCAL_FACTS_COLUMN_NAME_FACTS + "=\"" + fact_tobe_deleted + "\";";
        SQLiteDatabase sqLiteDatabase;
        sqLiteDatabase = getWritableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery(SELECT_BEFORE_DELETE_FACT_QUERY, null);
        if(cursor.getCount() == 1){
            cursor.close();
            sqLiteDatabase.execSQL(DELETE_FACT_QUERY);
//            cursor_list.remove(DELETE_FACT_QUERY);
            sqLiteDatabase.close();
            return true;
        }
        else{
            cursor.close();
            sqLiteDatabase.close();
            return false;
        }

    }

    public boolean databasehasFacts() {

        boolean hasFacts = false;

        ArrayList<String> cursor_list =  new ArrayList<>();

        String dbString = "";

        String query_string = " SELECT * FROM " + LOCAL_FACTS_TABLE_NAME;

        SQLiteDatabase sqLiteDatabase;
        sqLiteDatabase = getWritableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery(query_string, null);

        cursor.moveToFirst();

        while(!cursor.isAfterLast()) {

            if (cursor.getString(cursor.getColumnIndex(LOCAL_FACTS_COLUMN_NAME_FACTS)) != null) {

                dbString = cursor.getString(cursor.getColumnIndex(LOCAL_FACTS_COLUMN_NAME_FACTS));
                cursor_list.add(dbString);
                hasFacts = true;

            }

            cursor.moveToNext();


        }
        cursor.close();
        sqLiteDatabase.close();

        return hasFacts;
    }


    // to print the local DataBase using Cursor

    public ArrayList<String> databaseToString() {

        ArrayList<String> cursor_list =  new ArrayList<>();

        String dbString = "";

        String query_string = " SELECT * FROM " + LOCAL_FACTS_TABLE_NAME;

        SQLiteDatabase sqLiteDatabase;
        sqLiteDatabase = getWritableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery(query_string, null);

        cursor.moveToFirst();

        while(!cursor.isAfterLast()) {

            if (cursor.getString(cursor.getColumnIndex(LOCAL_FACTS_COLUMN_NAME_FACTS)) != null) {

                dbString = cursor.getString(cursor.getColumnIndex(LOCAL_FACTS_COLUMN_NAME_FACTS));
                cursor_list.add(dbString);

            }

            cursor.moveToNext();


        }
        cursor.close();
        sqLiteDatabase.close();

        return cursor_list;
    }





}
