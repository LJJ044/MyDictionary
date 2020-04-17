package com.example.excelergo.mydictionary;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginActivity extends RegisterActivity {
    EditText etaccount;EditText etpsw;Button btnlog;Button btnregister;TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login);

        init();
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginActivity.this,AlterActivity.class);
                startActivity(intent);
            }
        });
        btnlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String account = etaccount.getText().toString().trim();
                String psw = etpsw.getText().toString().trim();
                RegisterInfo registerInfo=new RegisterInfo();
                registerInfo.setName(account);
                registerInfo.setPwd(psw);
                UserInfo userInfo=new UserInfo(getApplicationContext());
                Map<String,String> map=userInfo.queryUser(registerInfo);
                String name=map.get("name");
                String pwd=map.get("pwd");
                Map<String,String>map1=userInfo.queryByUserName(account);
                Map<String,String>map2=userInfo.queryByPwd(psw);


// 校验号码和密码是否正确
                if (TextUtils.isEmpty(account)) {
                    Toast.makeText(getApplicationContext(), "请输入账号", Toast.LENGTH_SHORT).show();

                } else if (TextUtils.isEmpty(psw)) {
                    Toast.makeText(getApplicationContext(), "请输入密码", Toast.LENGTH_SHORT).show();

                }else if(!account.equals(map2.get("name2"))){
                    Toast.makeText(getApplicationContext(), "请输入正确的账号", Toast.LENGTH_SHORT).show();

                }else if(!psw.equals(map1.get("pwd1"))){
                    Toast.makeText(getApplicationContext(), "请输入正确的密码", Toast.LENGTH_SHORT).show();
                    etpsw.setText("");
                }
                    if (account.equals(name) && psw.equals(pwd)) {
                        Toast.makeText(getApplicationContext(), "登陆成功", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.putExtra("name",name);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                }
        });

        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),RegisterActivity.class);
                startActivity(intent);
            }
        });
        }

    private void init(){
        etaccount=findViewById(R.id.et_account);
        etpsw=findViewById(R.id.et_psw);
        btnlog=findViewById(R.id.btn_log);
        btnregister=findViewById(R.id.btn_register);
        textView=findViewById(R.id.alter_pwd);

    }

}
