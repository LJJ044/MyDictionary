package com.example.excelergo.mydictionary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AlterActivity extends AppCompatActivity {
    UserInfo userInfo;EditText editText1;EditText editText2;Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_alter);
        userInfo=new UserInfo(getApplicationContext());
        editText1=findViewById(R.id.et_account_alter);
        editText2=findViewById(R.id.et_psw_alter);
        button=findViewById(R.id.btn_confirm);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String alter_name=editText1.getText().toString();
                String alter_pwd=editText2.getText().toString();
                RegisterInfo registerInfo=new RegisterInfo();
                registerInfo.setName(alter_name);
                registerInfo.setPwd(alter_pwd);
                userInfo.updateUser(registerInfo);
                Intent intent=new Intent(AlterActivity.this,MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

}
