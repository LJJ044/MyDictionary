package com.example.excelergo.mydictionary;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.searchview.SearchView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import beans.WordsBean;
import db.WordWorkOpenHelper;
import util.WordsAction;

public class WordsBook extends AppCompatActivity {
    private ListView listView_words;
    private WordsAction wordsAction;
    private WordsBean words = new WordsBean();
    private  List<WordPos> list;
    private MyAdapter myAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_words_book);
        listView_words = findViewById(R.id.lv_words);
        wordsAction = WordsAction.getInstance(this);
        Intent intent = getIntent();
        String wordbook = intent.getStringExtra("wordbook");

            words = wordsAction.getWordsFromSQLite(wordbook);
            String word = words.getKey();
            String explanation = words.getPosAcceptation();
            MySQLiteAdapter adapter=new MySQLiteAdapter(getApplicationContext());
            WordPos wordPos=new WordPos();
            wordPos.setWord(word);
            wordPos.setPos(explanation);
            adapter.insert(wordPos);
            init();

        listView_words.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(WordsBook.this, SearchResultActivity.class);
                String word=list.get(i).getWord();
                intent.putExtra("word",word);
                startActivity(intent);
            }
        });

        }
        private void init(){
            MySQLiteAdapter adapter=new MySQLiteAdapter(getApplicationContext());
            list=adapter.queryAll();
            myAdapter=new MyAdapter();
            listView_words.setAdapter(myAdapter);
            //注册语境菜单
            registerForContextMenu(listView_words);

        }
        class MyAdapter extends BaseAdapter{
            @Override
            public int getCount() {
                return list.size();
            }

            @Override
            public Object getItem(int i) {
                return list.get(i);
            }

            @Override
            public long getItemId(int i) {
                return i;
            }

            @Override
            public View getView(int i, View view, ViewGroup viewGroup) {
                view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_words_book_item,null);
                TextView textView=view.findViewById(R.id.wordbook_word);
                TextView textView1=view.findViewById(R.id.wordbook_pos);
                WordPos wordPos=list.get(i);
                textView.setText(wordPos.getWord());
                textView1.setText(wordPos.getPos());
                return view;
            }
        }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        if(v==listView_words) {
            menu.add(0, 0, 0, "删除");
            menu.add(0, 1, 0, "取消");
        }
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo menuInfo=(AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        int i=menuInfo.position;
        switch (item.getItemId()){
            case 0:
                WordPos wordPos=new WordPos();
                String word=list.get(i).getWord();
                String pos=list.get(i).getPos();
                wordPos.setWord(word);
                wordPos.setPos(pos);
                MySQLiteAdapter adapter=new MySQLiteAdapter(getApplicationContext());
                adapter.deleteData(wordPos);
                list.remove(list.get(i));
                myAdapter.notifyDataSetChanged();
                adapter.queryAll();
                Toast.makeText(WordsBook.this,"该记录已删除",Toast.LENGTH_SHORT).show();
                break;
            case 1:
                break;
        }

        return  super.onContextItemSelected(item);

    }
}


