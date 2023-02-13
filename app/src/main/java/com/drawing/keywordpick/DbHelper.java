package com.drawing.keywordpick;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

public class DbHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "keyword_list.db";

    public static final String TABLE_NAME = "entry";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME_TITLE = "title";
    public static final String COLUMN_NAME_CONTENT = "content";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_NAME_TITLE + " TEXT," +
                    COLUMN_NAME_CONTENT + " TEXT)";
    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    private static DbHelper inst;
    private SQLiteDatabase db;
    private Context context;

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        db = this.getWritableDatabase();    // Gets the data repository in write mode
        this.context = context;
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    /* DB를 가져오는 함수 */
    public static DbHelper getInst(Context context){
        if(inst == null){
            inst = new DbHelper(context);
        }
        return inst;
    }
    public ArrayList<MyData> getAllData(){
//        // id로 가져와서 추가하기
//        List itemIds = new ArrayList<>();
//        while(cursor.moveToNext()) {
//            long itemId = cursor.getLong(
//                    cursor.getColumnIndexOrThrow(FeedEntry._ID));
//            itemIds.add(itemId);
//        }
//        cursor.close();

        ArrayList<MyData> resultList = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor c = db.rawQuery(query, null);
        while (c.moveToNext()){
            MyData temp = new MyData(c.getString(0), c.getString(1),c.getString(2));
            resultList.add(temp);
        }
        c.close();

        return resultList;
    }
    public ArrayList<MyData> getData(String title){
        ArrayList<MyData> resultList = new ArrayList<>();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {COLUMN_ID, COLUMN_NAME_TITLE, COLUMN_NAME_CONTENT};

        // Filter results WHERE "title" = 'My Title'
        String selection = COLUMN_NAME_TITLE + " = ?";
        String[] selectionArgs = { title };

        // How you want the results sorted in the resulting Cursor
        String sortOrder = COLUMN_NAME_CONTENT + " DESC";

        Cursor c = db.query(
                TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null              // The sort order
        );

        while (c.moveToNext()){
            MyData temp = new MyData(c.getString(0), c.getString(1),c.getString(2));
            resultList.add(temp);
        }
        c.close();

        return resultList;
    }

    public void insertData(String title, String content){
        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_TITLE, title);
        values.put(COLUMN_NAME_CONTENT, content);
        Toast.makeText(context, "저장되었습니다!", Toast.LENGTH_SHORT).show();

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(TABLE_NAME, null, values);
        Log.d("저장실패", newRowId+"");
    }
    public void deleteData(String id){
        // Define 'where' part of query.
        String selection = COLUMN_ID + " LIKE ?";

        // Specify arguments in placeholder order.
        String[] selectionArgs = { id };

        // Issue SQL statement.
        int deletedRows = db.delete(TABLE_NAME, selection, selectionArgs);
        Log.d("삭제된 행수", deletedRows+"");
    }
    public void updateData(String id, String title, String content){
        // New value for one column
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_TITLE, title);
        values.put(COLUMN_NAME_CONTENT, content);

        // Which row to update, based on the title
        String selection = COLUMN_ID + " LIKE ?";
        String[] selectionArgs = { id };

        int count = db.update( TABLE_NAME, values, selection, selectionArgs);
        Log.d("업데이트된 행수", count+"");
    }

}
