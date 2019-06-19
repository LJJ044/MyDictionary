package com.example.excelergo.mydictionary;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    EditText etaccount;EditText etpsw;Button btnregister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_register);
        init();
        }

        public void onClick(View view) {

                String account = etaccount.getText().toString().trim();
                String psw = etpsw.getText().toString();
                if(TextUtils.isEmpty(account)) {
                    Toast.makeText(this, "请输入账号",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(psw)) {
                    Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
                    return;
                }
                // 登录成功
            //Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
            //Log.i("MainActivity", "记住密码: " + account + ", " + psw);
// 保存用户信息
            boolean isSaveSuccess = Utils.saveUserInfo(this, account,
                    psw);
            if(isSaveSuccess) {
                Toast.makeText(this, "注册成功",
                    Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "注册失败",
                        Toast.LENGTH_SHORT).show();
            }
            Intent intent=new Intent(getApplicationContext(),LoginActivity.class);
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

