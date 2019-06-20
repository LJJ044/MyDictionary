package com.example.excelergo.mydictionary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.searchview.SearchView;

import java.util.ArrayList;
import java.util.List;

public class WordsBook extends AppCompatActivity {
private ListView listView_words;
private WordAdapter wordAdapter;
private List<String> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_words_book);
        listView_words=findViewById(R.id.lv_words);
        Intent intent=getIntent();
        String words= intent.getStringExtra("wordbook");
        list=new ArrayList<>();
        list.add(words);
        wordAdapter=new WordAdapter();
        listView_words.setAdapter(wordAdapter);

    }
   class WordAdapter extends BaseAdapter{
       @Override
       public int getCount() {
           return list.size();
       }

       @Override
       public Object getItem(int position) {
           return list.get(position);
       }

       @Override
       public long getItemId(int position) {
           return position;
       }

       @Override
       public View getView(int position, View convertView, ViewGroup parent) {
           View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_words_book_item,null);
           TextView textView=view.findViewById(R.id.wordbook_word);
           textView.setText(list.get(position));
           return view;
       }
   }
}
