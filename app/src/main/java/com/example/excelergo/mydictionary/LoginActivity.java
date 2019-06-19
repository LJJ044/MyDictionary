package com.example.excelergo.mydictionary;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Map;

public class LoginActivity extends RegisterActivity {
    EditText etaccount;EditText etpsw;Button btnlog;Button btnregister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login);
        init();

        btnlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, String> userInfo = Utils.getUserInfo(getApplicationContext());
                String account = etaccount.getText().toString().trim();
                String psw = etpsw.getText().toString().trim();
// 校验号码和密码是否正确
                if (TextUtils.isEmpty(account)) {
                    Toast.makeText(getApplicationContext(), "请输入账号",
                            Toast.LENGTH_SHORT).show();

                } else if (TextUtils.isEmpty(psw)) {
                    Toast.makeText(getApplicationContext(), "请输入密码", Toast.LENGTH_SHORT).show();
                }else if (!etaccount.getText().toString().equals(userInfo.get("account"))) {

                    Toast.makeText(getApplicationContext(), "请输入正确的账号", Toast.LENGTH_SHORT).show();
                }else if (!TextUtils.isEmpty(psw) && !etpsw.getText().toString().equals(userInfo.get("psw"))) {

                    Toast.makeText(getApplicationContext(), "请输入正确的密码", Toast.LENGTH_SHORT).show();
                    etpsw.setText("");
                }
                    if (etaccount.getText().toString().equals(userInfo.get("account")) &&
                            etpsw.getText().toString().equals(userInfo.get("psw"))) {
                        Toast.makeText(getApplicationContext(), "登陆成功", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    } else if(!TextUtils.isEmpty(account)&&!TextUtils.isEmpty(psw)){
                        Toast.makeText(getApplicationContext(), "找不到该用户", Toast.LENGTH_SHORT).show();
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


    }

}
