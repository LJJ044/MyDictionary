package com.example.excelergo.mydictionary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

import beans.WordsBean;
import util.WordsAction;


public class MainActivity extends AppCompatActivity {
private ImageButton search_btn;
private WordsAction wordsAction;
private WordsBean words=new WordsBean();
private EditText editText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        search_btn=findViewById(R.id.search_main_btn);
        editText=findViewById(R.id.et_main);
        wordsAction=WordsAction.getInstance(this);
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wordsAction.getWordsFromSQLite("");
                Intent intent=new Intent(MainActivity.this,SearchHistoryActivity.class);
                startActivity(intent);
            }
        });

    }
    public void SearchResult(View view){
        Intent intent=new Intent(MainActivity.this,SearchHistoryActivity.class);
        editText=findViewById(R.id.et_main);
        startActivity(intent);
    }
}
