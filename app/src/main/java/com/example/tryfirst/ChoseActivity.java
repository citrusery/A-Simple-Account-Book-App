package com.example.tryfirst;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import static com.example.tryfirst.LoginState.getLoginState;

public class ChoseActivity extends Activity {
    private Button btn_login;//登陆按钮
    private Button btn_register; //注册按钮

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clogin);
        btn_login = findViewById(R.id.signin);
        btn_register = findViewById(R.id.enroll);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //跳转到注册页面
                Intent intent = new Intent(ChoseActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
        btn_login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //跳转到登陆页面
                if(!getLoginState()) {
                    Intent intent = new Intent(ChoseActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
                else{
                    Intent intent = new Intent(ChoseActivity.this,MainActivity.class);
                    startActivity(intent);
                }
            }
        });


}
}
