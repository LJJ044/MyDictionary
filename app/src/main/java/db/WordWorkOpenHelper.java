package db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class WordWorkOpenHelper extends SQLiteOpenHelper {
    public WordWorkOpenHelper(Context context) {
        super(context, "wordbook.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table WordsExplanation(id Integer primary key autoincrement,word varchar(200),explanation varchar(200))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }
}
