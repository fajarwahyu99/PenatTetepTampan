package com.example.infolabsolution.lastmoviejar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.infolabsolution.lastmoviejar.Favorite;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.example.infolabsolution.lastmoviejar.DatabaseContract.FavoriteColumns.DESCRIPTION;
import static com.example.infolabsolution.lastmoviejar.DatabaseContract.FavoriteColumns.POSTER;
import static com.example.infolabsolution.lastmoviejar.DatabaseContract.FavoriteColumns.RELEASE_DATE;
import static com.example.infolabsolution.lastmoviejar.DatabaseContract.FavoriteColumns.TITLE;



public class FavoriteHelper {
    private static String DATABASE_TABLE = BuildConfig.TABLE_MOVIE;
    private Context context;
    private DatabaseHelper dataHelper;

    private SQLiteDatabase database;

    public FavoriteHelper(Context context){
        this.context = context;
    }

    public FavoriteHelper open() throws SQLException {
        dataHelper = new DatabaseHelper(context);
        database = dataHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        dataHelper.close();
    }

    public ArrayList<Favorite> query(){
        ArrayList<Favorite> arrayList = new ArrayList<Favorite>();
        Cursor cursor = database.query(DATABASE_TABLE,null,null,null,null,null,_ID +" DESC",null);
        cursor.moveToFirst();
        Favorite note;
        if (cursor.getCount()>0) {
            do {

                note = new Favorite();
                note.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                note.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
                note.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(DESCRIPTION)));
                note.setRelease_date(cursor.getString(cursor.getColumnIndexOrThrow(RELEASE_DATE)));
                note.setPoster(cursor.getString(cursor.getColumnIndexOrThrow(POSTER)));
                arrayList.add(note);
                cursor.moveToNext();

            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }


    public long insert(Favorite note){
        ContentValues initialValues =  new ContentValues();
        initialValues.put(TITLE, note.getTitle());
        initialValues.put(DESCRIPTION, note.getOverview());
        initialValues.put(RELEASE_DATE, note.getRelease_date());
        initialValues.put(POSTER, note.getPoster());
        return database.insert(DATABASE_TABLE, null, initialValues);
    }

    public int delete(int id){
        return database.delete(BuildConfig.TABLE_MOVIE, _ID + " = '"+id+"'", null);
    }


    public Cursor queryByIdProvider(String id){
        return database.query(DATABASE_TABLE,null
                ,_ID + " = ?"
                ,new String[]{id}
                ,null
                ,null
                ,null
                ,null);
    }
    public Cursor queryProvider(){
        return database.query(DATABASE_TABLE
                ,null
                ,null
                ,null
                ,null
                ,null
                ,_ID + " DESC");
    }
    public long insertProvider(ContentValues values){
        return database.insert(DATABASE_TABLE,null,values);
    }

    public int updateProvider(String id,ContentValues values){
        return database.update(DATABASE_TABLE,values,_ID +" = ?",new String[]{id} );
    }


    public int deleteProvider(String id){
        return database.delete(DATABASE_TABLE,_ID + " = ?", new String[]{id});
    }
}