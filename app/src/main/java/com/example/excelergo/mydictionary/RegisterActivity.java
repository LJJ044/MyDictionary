package com.example.excelergo.mydictionary;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    EditText etaccount;EditText etpsw;Button btnregister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_register);

        init();
        }

        public void onClick(View view) {

                String account = etaccount.getText().toString();
                String psw = etpsw.getText().toString();
                if(TextUtils.isEmpty(account)) {
                    Toast.makeText(this, "请输入账号",
                            Toast.LENGTH_SHORT).show();
                }
                if(TextUtils.isEmpty(psw)) {
                    Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
                }

// 保存用户信息
            UserInfo userInfo=new UserInfo(getApplicationContext());
            RegisterInfo registerInfo=new RegisterInfo();
                registerInfo.setName(account);
                registerInfo.setPwd(psw);
                Long success=userInfo.insertUser(registerInfo);
               if(success>0) {
                   Toast.makeText(getApplicationContext(), "注册成功", Toast.LENGTH_SHORT).show();
               }
            Intent intent=new Intent(getApplicationContext(),LoginActivity.class);
               intent.putExtra("name",account);
               intent.putExtra("pwd",psw);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }

    private void init(){
        etaccount=findViewById(R.id.et_account);
        etpsw=findViewById(R.id.et_psw);
        btnregister=findViewById(R.id.btn_register);
        btnregister.setOnClickListener(this);
    }
}

