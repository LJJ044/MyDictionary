package com.example.excelergo.mydictionary;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.w3c.dom.Text;

import java.io.IOException;
import beans.SententenceBean;



public class MainActivity extends AppCompatActivity implements MediaService.IMediaStateListener, ServiceConnection {
    private ImageButton search_btn;
    private EditText editText;
    private ImageButton img_user;
    private OkHttpClient okHttpClient;
    private MediaService mediaService;
    private long exitTime = 0;
    private Handler mHandler = new Handler();
    private Runnable mFinish = new Runnable() {
        @Override
        public void run() {
           finish();
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        search_btn = findViewById(R.id.search_main_btn);
        editText = findViewById(R.id.et_main);
        img_user=findViewById(R.id.img_user);
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
    img_user.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent=new Intent(MainActivity.this,LoginActivity.class);
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
                           ImageView imageView1=findViewById(R.id.SenVoice);
                           final String s=bean.getTts();
                           if (mediaService == null) {
                               // bind service
                               Intent intent = new Intent(MainActivity.this, MediaService.class);
                               MainActivity.this.bindService(intent, MainActivity.this, Context.BIND_AUTO_CREATE);
                           }
                           imageView1.setOnClickListener(new View.OnClickListener() {
                               @Override
                               public void onClick(View view) {

                                   if(mediaService!=null){
                                     Toast.makeText(MainActivity.this,"获取网络语音中",Toast.LENGTH_SHORT).show();
                                       mediaService.playMusic(s);
                                   }
                                   Log.d("语音","有内容");
                               }
                           });


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

    @Override
    public void onServiceConnected(ComponentName componentName, IBinder service) {
        Log.d("onServiceConnected","连上了");
        MediaService.MediaBinder mediaBinder = (MediaService.MediaBinder) service;
        mediaService = mediaBinder.getService();
        mediaService.setMediaStateListener(MainActivity.this);
        }


    @Override
    public void onServiceDisconnected(ComponentName componentName) {

    }

    @Override
    public void onPrepared(int duration) {

    }

    @Override
    public void onProgressUpdate(int currentPos, int duration) {

    }

    @Override
    public void onSeekComplete() {

    }

    @Override
    public void onCompletion() {

    }

    @Override
    public boolean onInfo(int what, int extra) {
        return false;
    }

    @Override
    public boolean onError(int what, int extra) {
        return false;
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                exitTime = System.currentTimeMillis();
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            } else {
                mHandler.postDelayed(mFinish, 0);
            }
            //return super.onKeyDown(keyCode, event);

        }
        return false;
    }
}
