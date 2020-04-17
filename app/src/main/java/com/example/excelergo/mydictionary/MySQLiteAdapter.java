package com.example.excelergo.mydictionary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import db.WordWorkOpenHelper;

public class MySQLiteAdapter {
    private SQLiteDatabase db;
    private final String CREATE_WORDS = "WordsExplanation";
    private Context context;
    public void openDB(){
        WordWorkOpenHelper helper = new WordWorkOpenHelper(context);
        db = helper.getWritableDatabase();
        db=helper.getReadableDatabase();
    }
    public MySQLiteAdapter(Context context) {
        this.context = context;
    }
    public void insert(WordPos wordPos){
        //long rowId=-1;
        openDB();
        Cursor cursor=db.query(CREATE_WORDS,null,"word=? and explanation=?",new String[]{wordPos.getWord(),wordPos.getPos()},null,null,null);
        if(cursor.getCount()>0){
            //rowId=0;//标识数据记录已存在
            Toast.makeText(context,"该记录已存在",Toast.LENGTH_SHORT).show();
        }else if(cursor.getCount()==0) {
            ContentValues values = new ContentValues();
            values.put("word", wordPos.getWord());
            values.put("explanation", wordPos.getPos());
            db.insert(CREATE_WORDS, null, values);
        }
        db.close();
        //return rowId;
    }
    public List<WordPos> queryAll(){
        List<WordPos> list=new ArrayList<>();
        openDB();
        Cursor cursor = db.query(CREATE_WORDS, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do{

                String word = cursor.getString(cursor.getColumnIndex("word"));
                String explanation = cursor.getString(cursor.getColumnIndex("explanation"));
                WordPos wordPos=new WordPos();
                wordPos.setWord(word);
                wordPos.setPos(explanation);
                if(!list.contains(wordPos)) {
                    list.add(wordPos);
                }
                Log.i("数据库操作", "查询成功");
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }
        public void deleteData(WordPos wordPos){
        openDB();
        db.delete(CREATE_WORDS,"word=? and explanation=?",new String[]{wordPos.getWord(),wordPos.getPos()});
        db.close();

        }
}
