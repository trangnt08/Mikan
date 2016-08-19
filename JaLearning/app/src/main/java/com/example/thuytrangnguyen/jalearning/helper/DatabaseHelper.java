package com.example.thuytrangnguyen.jalearning.helper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.thuytrangnguyen.jalearning.object.Word;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thuy Trang Nguyen on 2/12/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DB_PATH = "/data/data/com.example.thuytrangnguyen.jalearning/databases/";
    public static final String DB_NAME = "japan1.sqlite";
    public static final String TABLE_BASIC = "basic";
    public static final String ID_WORD = "id";
    public static final String KEY_ID_RANK = "id_rank";
    public static final String WORD = "word";
    public static final String MEAN = "mean";
    public static final String STATUS = "status";
    public static final String A = "a";
    public static final String B = "b";
    public static final String C = "c";

    private Context context;
    private SQLiteDatabase db;

    public DatabaseHelper(Context context){
        super(context,DB_NAME,null,1);
        this.context = context;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public void openDataBase() {

        //Open the database
        String dbPath = context.getDatabasePath(DB_NAME).getPath();
        if (db!=null && db.isOpen()){
            return;
        }
        //String myPath = DB_PATH + DB_NAME;
        db = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READONLY);

    }
    public void closeDatabase(){
        if (db!=null){
            db.close();
        }
    }

    public List<Word> getListWord(){
        Word word = null;
        List<Word> wordsList = new ArrayList<>();
        openDataBase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+ TABLE_BASIC,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            word = new Word(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getInt(3),cursor.getInt(4),cursor.getString(5),cursor.getString(6),cursor.getString(7));
            wordsList.add(word);
            cursor.moveToNext();
        }
        cursor.close();
        closeDatabase();
        return wordsList;
    }
    public Cursor getAll(String sql) {
        openDataBase();
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        close();
        return cursor;
    }
    public Word getWord(String sql){
        Word word = null;
        Cursor cursor = getAll(sql);
        if (cursor!=null){
            word = cursorToWord(cursor);
            cursor.close();
        }
        return word;
    }

    private Word cursorToWord(Cursor cursor) {
        Word word = new Word();
        word.setId(cursor.getInt(cursor.getColumnIndex(ID_WORD)));
        word.setWord(cursor.getString(cursor.getColumnIndex(WORD)));
        word.setMean(cursor.getString(cursor.getColumnIndex(MEAN)));
        word.setStatus(cursor.getInt(cursor.getColumnIndex(STATUS)));
        word.setId_rank(cursor.getInt(cursor.getColumnIndex(KEY_ID_RANK)));
        word.setA(cursor.getString(cursor.getColumnIndex(A)));
        word.setB(cursor.getString(cursor.getColumnIndex(B)));
        word.setC(cursor.getString(cursor.getColumnIndex(C)));

        return word;
    }
}
