package com.example.mysqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

class MyDBHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "AS_CRUD.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "practice_library";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_DESC = "desc";
    private static final String COLUMN_STOCK = "stock";
     MyDBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE " + TABLE_NAME + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_TITLE + " TEXT, " + COLUMN_DESC + " TEXT, " + COLUMN_STOCK + " INTEGER);";
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    void addData(String title, String desc, int stock){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put(COLUMN_TITLE, title);
        content.put(COLUMN_DESC, desc);
        content.put(COLUMN_STOCK, stock);

        long result = db.insert(TABLE_NAME, null, content);
        if(result == -1){
            Toast.makeText(context, "Failed to add data", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Added data successfully", Toast.LENGTH_SHORT).show();

        }
    }
    Cursor readAllData(){
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(db != null){
            cursor =  db.rawQuery(query, null);
        }
        return cursor;
    }

    void updateData(String rowID, String title, String desc, int stock){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put(COLUMN_TITLE, title);
        content.put(COLUMN_DESC, desc);
        content.put(COLUMN_STOCK, stock);

        long result = db.update(TABLE_NAME, content, "_id=?", new String[]{rowID});
        if(result == - 1){
            Toast.makeText(context, "⚠\uFE0F Error updating data", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "\uD83D\uDCDD Data has been updated", Toast.LENGTH_SHORT).show();

        }
    }
    void deleteOneRow(String rowID){
         SQLiteDatabase db = this.getWritableDatabase();
         long result = db.delete(TABLE_NAME, "_id=?", new String[]{rowID});
         if(result == -1){
             Toast.makeText(context, "⚠\uFE0F Error deleting data", Toast.LENGTH_SHORT).show();
         }else{
             Toast.makeText(context, "\uD83E\uDDF9\uD83E\uDEA3 Data deletion successful.", Toast.LENGTH_SHORT).show();
         }
    }

}
