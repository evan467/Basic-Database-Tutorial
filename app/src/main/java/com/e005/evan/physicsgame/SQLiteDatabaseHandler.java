package com.e005.evan.physicsgame;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class SQLiteDatabaseHandler extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DATABASE_NAME = "QuestionsDB";
    private static final String TABLE_NAME = "Questions";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_QUESTION = "question";
    private static final String KEY_ANSWER = "answer";
    private static final String KEY_DIFFICULTY = "difficulty";




    private static final String[] COLUMNS = {KEY_ID, KEY_NAME, KEY_QUESTION, KEY_ANSWER, KEY_DIFFICULTY};

    public SQLiteDatabaseHandler(Context context){
        super(context, DATABASE_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String CREATE = "CREATE TABLE " + TABLE_NAME + " (" +
                KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                KEY_NAME + " TEXT, " + KEY_QUESTION + " TEXT, " + KEY_ANSWER + " TEXT, " + KEY_DIFFICULTY + " INTEGER)";

        db.execSQL(CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        this.onCreate(db);
    }

    public void deleteOne(Question question){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME,KEY_ID + " = ?", new String[] {String.valueOf(question.id)});
        db.close();
    }

    public Question getQ(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME,
                COLUMNS,
                KEY_ID + "= ?",
                new String[] {String.valueOf(id)},
                null,
                null,
                null,
                 null
                );
        if(cursor != null){
            cursor.moveToFirst();
        }

        Question q = new Question(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                Integer.parseInt(cursor.getString(4))
        );

        return q;
    }

    public List<Question> allQ(){
        List<Question> qS = null;
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);
        //NUll copy question
        Question q = null;

        if(cursor.moveToFirst()){
            qS = new LinkedList<>();
            do{
                q = new Question(Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        Integer.parseInt(cursor.getString(4))
                );
                qS.add(q);
            }while(cursor.moveToNext());
        }

        return qS;
    }

    public Question randQ(){
        List<Question> qS = null;

        SQLiteDatabase db = this.getWritableDatabase();
        Question ret = null;

        String query = "SELECT * FROM " + TABLE_NAME;

        Cursor cursor = db.rawQuery(query, null);
        Question q = null;

        if(cursor.moveToFirst()){
            qS = new LinkedList<>();
            do{
                q = new Question(Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        Integer.parseInt(cursor.getString(4))
                );
                qS.add(q);
            }while(cursor.moveToNext());
        }

        // Random time
        final int min = 0;
        final int max = qS.size()- 1;
        final int random = new Random().nextInt((max - min) + 1) + min;

        ret = qS.get(random);

        return ret;
    }

    public void addQ(Question question){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_NAME, question.name);
        values.put(KEY_QUESTION, question.question);
        values.put(KEY_ANSWER, question.answer);
        values.put(KEY_DIFFICULTY, question.difficulty);

        db.insert(TABLE_NAME, null, values);
        db.close();

    }

    public int updateQ(Question question){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_NAME, question.name);
        values.put(KEY_QUESTION, question.question);
        values.put(KEY_ANSWER, question.answer);
        values.put(KEY_DIFFICULTY, question.difficulty);

        int i = db.update(TABLE_NAME,
                values,
                KEY_ID + " = ?",
                new String[] {String.valueOf(question.id)}
                );
        db.close();
        return i;
    }

    public void removeAll(){
        String q = "DELETE FROM " + TABLE_NAME;
        getWritableDatabase().execSQL(q);
    }


}
