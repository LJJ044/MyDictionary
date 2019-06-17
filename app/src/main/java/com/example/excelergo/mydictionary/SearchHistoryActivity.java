package com.example.excelergo.mydictionary;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.searchview.ICallBack;
import com.example.searchview.SearchView;
import com.example.searchview.bCallBack;


public class SearchHistoryActivity extends AppCompatActivity {
private SearchView searchView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_history);
        searchView=(SearchView)findViewById(R.id.search_view);

        searchView.setOnClickSearch(new ICallBack() {
            @Override
            public void SearchAciton(String string) {
                Intent intent=new Intent(SearchHistoryActivity.this,SearchResultActivity.class);
                intent.putExtra("word",string);
                startActivity(intent);
            }
        });

        // 5. 设置点击返回按键后的操作（通过回调接口）
        searchView.setOnClickBack(new bCallBack() {
            @Override
            public void BackAciton() {
                finish();
            }
        });

    }

}