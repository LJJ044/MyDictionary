package com.example.excelergo.mydictionary;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import beans.WordsBean;
import util.WordsAction;

public class WordsBookMain extends AppCompatActivity {
    private ListView listView_words;
    private  List<WordPos> list;
    private MyAdapter myAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_words_book_main);
        listView_words = findViewById(R.id.lv_words2);
        init();

        listView_words.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(WordsBookMain.this, SearchResultActivity.class);
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
                view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_words_book_item2,null);
                TextView textView=view.findViewById(R.id.wordbook_word2);
                TextView textView1=view.findViewById(R.id.wordbook_pos2);
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
                Toast.makeText(WordsBookMain.this,"该记录已删除",Toast.LENGTH_SHORT).show();
                break;
            case 1:
                break;
        }

        return super.onContextItemSelected(item);

    }
}


