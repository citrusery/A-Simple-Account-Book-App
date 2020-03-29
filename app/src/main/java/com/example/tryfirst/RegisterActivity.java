package com.example.tryfirst;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends Activity {
    private Button tv_back;//返回按钮
    private Button btn_register;//注册按钮
    private EditText reg_username;
    private EditText reg_password;
    private EditText reg_password2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        reg_username = findViewById(R.id.account_input);
        reg_password = findViewById(R.id.password_input);
        reg_password2 = findViewById(R.id.password_input2);
        btn_register= findViewById(R.id.btn_enroll);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = reg_username.getText().toString().trim();
                String password = reg_password.getText().toString().trim();
                String password2 = reg_password2.getText().toString().trim();

                if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password) || TextUtils.isEmpty(password2)) {
                    Toast.makeText(RegisterActivity.this, "各项均不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    if (username.length() >= 6 && password.length() >= 6) {
                        if (TextUtils.equals(password, password2)) {

                            //执行注册操作
                            if (isExistUserName(username)) {
                                Toast.makeText(RegisterActivity.this, "此账户名已经存在", Toast.LENGTH_SHORT).show();
                                return;
                            } else {
                                Toast.makeText(RegisterActivity.this, "注册成功,请返回登录", Toast.LENGTH_SHORT).show();
                                saveRegisterInfo(username, password);
//                            跳转到Login
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(intent);
                            }
                        } else {
                            Toast.makeText(RegisterActivity.this, "两次输入的密码不一样", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(RegisterActivity.this, "用户名及密码长度均不能小于6", Toast.LENGTH_SHORT).show();
                    }
                }


            }
            });

    }

    private boolean isExistUserName(String userName){
        boolean has_userName=false;
        //mode_private SharedPreferences sp = getSharedPreferences( );
        // "loginInfo", MODE_PRIVATE
        SharedPreferences sp=getSharedPreferences("loginInfo", MODE_PRIVATE);
        //获取密码
        String spPsw=sp.getString(userName, "");//传入用户名获取密码
        //如果密码不为空则确实保存过这个用户名
        if(!TextUtils.isEmpty(spPsw)) {
            has_userName=true;
        }
        return has_userName;
    }

    private void saveRegisterInfo(String userName,String psw){
        //loginInfo表示文件名, mode_private SharedPreferences sp = getSharedPreferences( );
        SharedPreferences sp=getSharedPreferences("loginInfo", MODE_PRIVATE);
        //获取编辑器， SharedPreferences.Editor  editor -> sp.edit();
        SharedPreferences.Editor editor=sp.edit();
        //以用户名为key，密码为value保存在SharedPreferences中
        //key,value,如键值对，editor.putString(用户名，密码）;
        editor.putString(userName, psw);
        //提交修改 editor.commit();
        editor.commit();
    }
}


