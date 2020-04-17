package com.example.excelergo.mydictionary;
import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.mylhyl.acp.Acp;
import com.mylhyl.acp.AcpListener;
import com.mylhyl.acp.AcpOptions;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import beans.SententenceBean;
import util.FileUtil;


public class MainActivity extends AppCompatActivity implements MediaService.IMediaStateListener, ServiceConnection,View.OnClickListener {
    private ImageButton search_btn;
    private EditText editText;
    private ImageView img_user;
    private ImageButton img_wordbook;
    private OkHttpClient okHttpClient;
    private MediaService mediaService;
    private MediaPlayer mMediaPlayer;
    private long exitTime = 0;
    private Handler mHandler;
    private TextView tv_user,tv_login,tv_gallery,tv_camera;
    private LinearLayout main_page,pop_options;
    private Bitmap head;
    private UserInfo userInfo;
    private SententenceBean bean;
    String headFilePath=Environment.getExternalStorageDirectory()+"/VocabularyBuilder/myHead/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Acp.getInstance(this).request(new AcpOptions.Builder().setPermissions(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.INTERNET,
                Manifest.permission.CAMERA
        ).build(), new AcpListener() {
            @Override
            public void onGranted() {

            }

            @Override
            public void onDenied(List<String> permissions) {

            }
        });
        initView();
        initClickListener();
        FileUtil.getInstance().createSDDir(Environment.getExternalStorageDirectory()+"/VocabularyBuilder/","myHead");
        loadTouxiang();
        okHttpClient=new OkHttpClient();
        userInfo=new UserInfo(getApplicationContext());
        tv_user.setText("你好，"+userInfo.queryFistUser());
        String user=getIntent().getStringExtra("name");
        if(user!=null) {
            tv_user.setText("你好，" + user);
        }
        getData();
    }

    public void SearchResult(View view) {
        Intent intent = new Intent(MainActivity.this, SearchHistoryActivity.class);
        startActivity(intent);
    }
    private void initView(){
        search_btn = (ImageButton) findViewById(R.id.search_main_btn);
        editText = (EditText) findViewById(R.id.et_main);
        img_user=(ImageView) findViewById(R.id.img_user);
        img_wordbook=(ImageButton) findViewById(R.id.wordbook);
        tv_user=(TextView) findViewById(R.id.tv_username);
        tv_login=(TextView)findViewById(R.id.login);
        tv_gallery=(TextView)findViewById(R.id.open_gallery);
        tv_camera=(TextView)findViewById(R.id.open_camera);
        main_page=(LinearLayout) findViewById(R.id.main_area);
        pop_options=(LinearLayout)findViewById(R.id.pop_options);
    }
    private void initClickListener(){
        img_user.setOnClickListener(this);
        editText.setOnClickListener(this);
        img_wordbook.setOnClickListener(this);
        tv_login.setOnClickListener(this);
        tv_gallery.setOnClickListener(this);
        tv_camera.setOnClickListener(this);
        main_page.setOnClickListener(this);
    }
    private void loadTouxiang() {
        Bitmap bitmap = BitmapFactory.decodeFile(headFilePath + "head.jpg");
        if (bitmap != null) {
            img_user.setImageBitmap(ImgeClipUtil.ClipSquareBitmap(bitmap, 200, bitmap.getWidth()));
        }
    }
    @Override
    public void onClick(View view) {
        Uri uri;
        File camerafile = new File(headFilePath + "head.jpg");
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) {
            uri = Uri.fromFile(camerafile);
        } else {
            //ContentValues values=new ContentValues(1);
            //values.put(MediaStore.Images.Media.DATA,camerafile.getPath());
            //uri=getActivity().getApplication().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values);
            uri = FileProvider.getUriForFile(MainActivity.this, "com.example.excelergo.mydictionary.provider", camerafile);
        }
        switch (view.getId()) {
            //点击外部区域关闭选项界面
            case R.id.main_area:
                if (pop_options.getVisibility() == View.VISIBLE) {
                    Animation animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.popup_menu_anim2);
                    pop_options.startAnimation(animation);
                    pop_options.setVisibility(View.GONE);
                }
                break;
                //点击弹出选项界面
            case R.id.img_user:
                if(pop_options.getVisibility()==View.GONE) {
                    Animation animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.popup_menu_anim);
                    pop_options.startAnimation(animation);
                    pop_options.setVisibility(View.VISIBLE);
                }
                break;
                //点击登录
            case R.id.login:
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                break;
            //打开相册
            case R.id.open_gallery:
                Intent intent_g = new Intent(Intent.ACTION_PICK, null);
                intent_g.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent_g, 1);
                pop_options.setVisibility(View.GONE);
                break;
            //打开相机
            case R.id.open_camera:
                Intent intent_c = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent_c.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                startActivityForResult(intent_c, 2);// 采用ForResult打开
                pop_options.setVisibility(View.GONE);
                break;
                //打开单词本
            case R.id.wordbook:
                Intent intent=new Intent(MainActivity.this,WordsBookMain.class);
                startActivity(intent);
                break;
                //点击搜索单词
            case R.id.et_main:
                Intent intent_et = new Intent(MainActivity.this, SearchHistoryActivity.class);
                startActivity(intent_et);
                break;

        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Uri uri = null;
        File camerafile = new File(headFilePath + "head.jpg");
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) {
            uri = Uri.fromFile(camerafile);
        } else {
            uri = FileProvider.getUriForFile(MainActivity.this, "com.example.excelergo.mydictionary.provider", camerafile);
        }
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    cropPhoto(data.getData());// 裁剪图片
                }
                break;
            case 2:
                if (resultCode == RESULT_OK) {
                    cropPhoto(uri);// 裁剪图片
                }
                break;
            case 3:
                if (data != null) {
                    Bundle extras = data.getExtras();
                    head = extras.getParcelable("data");
                    if (head != null) {
                        setPicToView(head);// 保存在SD卡中
                        img_user.setImageBitmap(ImgeClipUtil.ClipSquareBitmap(head, 200, head.getWidth()));// 用ImageView显示出来

                    }
                    break;
                }
                super.onActivityResult(requestCode, resultCode, data);
        }
    }
    //将Bitmap保存到本地
    private void setPicToView(Bitmap bitmap) {
        try {
            File file1 = new File(headFilePath);
            if (!file1.exists()) {
                file1.mkdir();
            }
            File file2 = new File(headFilePath + "head.JPG");
            FileOutputStream fos = new FileOutputStream(file2);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 调用系统的裁剪功能
     *
     * @param uri
     */
    public void cropPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        //授予相机权限
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        //裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高(图片质量)
        intent.putExtra("outputX", 500);
        intent.putExtra("outputY", 500);
        //发送数据
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 3);
    }

    private void getData() {
       new Thread(new Runnable() {
           @Override
           public void run() {
               Request request=new Request.Builder().url("http://open.iciba.com/dsapi/").build();
               try {
                   Call call=okHttpClient.newCall(request);
                   Response response=call.execute();
                   String s = response.body().string();
                   Log.i("词典","yes");
                   bean = new Gson().fromJson(s, SententenceBean.class);
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
                           final ImageButton imageView1=findViewById(R.id.SenVoice);

                           /*if (mediaService == null) {
                               // bind service
                               Intent intent = new Intent(MainActivity.this, MediaService.class);
                               MainActivity.this.bindService(intent, MainActivity.this, Context.BIND_AUTO_CREATE);
                           }*/
                           imageView1.setOnClickListener(new View.OnClickListener() {
                               @Override
                               public void onClick(View view) {
                                  if(mMediaPlayer==null){
                                      loadMusic();
                                  }else if(mMediaPlayer.isPlaying()){
                                      mMediaPlayer.stop();
                                      mMediaPlayer.release();
                                      mMediaPlayer=null;
                                      loadMusic();
                                   }else {
                                       Toast.makeText(MainActivity.this, "获取网络语音中", Toast.LENGTH_SHORT).show();
                                       mMediaPlayer.start();
                                   }
                                   /*if (mediaService != null&&(!mMediaPlayer.isPlaying())) {
                                       Toast.makeText(MainActivity.this, "获取网络语音中", Toast.LENGTH_SHORT).show();
                                       mediaService.resumeMusic(musicUrl);

                                   }*/
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
    private void loadMusic(){
        mMediaPlayer = new MediaPlayer();
        Toast.makeText(MainActivity.this, "获取网络语音中", Toast.LENGTH_SHORT).show();
        try {
            String musicUrl=bean.getTts();
            mMediaPlayer.setDataSource(musicUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mMediaPlayer.prepareAsync();
        mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.start();

            }
        });
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
        mHandler=new Handler();
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                exitTime = System.currentTimeMillis();
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            } else {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                }, 0);
            }
            //return super.onKeyDown(keyCode, event);

        }
        return false;
    }
}
