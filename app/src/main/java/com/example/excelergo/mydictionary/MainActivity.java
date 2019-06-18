package com.example.excelergo.mydictionary;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import java.io.IOException;
import beans.SententenceBean;
import beans.WordsBean;
import util.WordsAction;



public class MainActivity extends AppCompatActivity {
    private ImageButton search_btn;
    private WordsAction wordsAction;
    private WordsBean words = new WordsBean();
    private EditText editText;
    private OkHttpClient okHttpClient;
    private MediaPlayer player = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        search_btn = findViewById(R.id.search_main_btn);
        editText = findViewById(R.id.et_main);
        ImageView imageView3=findViewById(R.id.SenVoice);
        wordsAction = WordsAction.getInstance(this);
        okHttpClient=new OkHttpClient();
        getData();

        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SearchHistoryActivity.class);
                startActivity(intent);
            }
        });

    }

    public void SearchResult(View view) {
        Intent intent = new Intent(MainActivity.this, SearchHistoryActivity.class);
        editText = findViewById(R.id.et_main);
        startActivity(intent);
    }
    private void getData() {
       new Thread(new Runnable() {
           @Override
           public void run() {
               Request request=new Request.Builder().url("http://open.iciba.com/dsapi/").build();
               try {
                   Response response=okHttpClient.newCall(request).execute();
                   String s = response.body().string();
                   final SententenceBean bean = new Gson().fromJson(s, SententenceBean.class);
                   runOnUiThread(new Runnable() {
                       @Override
                       public void run() {
                           TextView textView=findViewById(R.id.SenE);
                           TextView textView2=findViewById(R.id.SenE2);
                           TextView textView3=findViewById(R.id.tv_SenTime);
                           textView2.setText(bean.getNote());
                           textView.setText(bean.getContent());
                           textView3.setText(bean.getDateline());
                           ImageView imageView=findViewById(R.id.SenPic);
                           ImageView imageView2=findViewById(R.id.SenPic2);

                           Glide
                                   .with(getApplicationContext())
                                   .load(bean.getPicture())
                                   .into(imageView);
                           Glide
                                   .with(getApplicationContext())
                                   .load(bean.getFenxiang_img())
                                   .into(imageView2);
                       }
                   });
               } catch (IOException e) {
                   e.printStackTrace();
               }
           }
       }).start();
    }
}