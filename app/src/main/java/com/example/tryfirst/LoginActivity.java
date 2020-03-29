package com.example.tryfirst;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;


import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.example.tryfirst.LoginState.setLogin_state;

public class LoginActivity  extends Activity {
    private String local_account , local_passwd ;
    private String input_account , input_passwd;
    private EditText account,passwd;
    private Button btn_login1;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        account = findViewById(R.id.account_input);
        passwd = findViewById(R.id.password_input);
        btn_login1 = findViewById(R.id.btn_login);

        btn_login1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sp = getSharedPreferences("loginInfo", MODE_PRIVATE);


                input_account = account.getText().toString().trim();
                input_passwd = passwd.getText().toString().trim();
                local_passwd = sp.getString(input_account,"");
                //先实现通admin登陆
                if (TextUtils.isEmpty(input_account) || TextUtils.isEmpty(input_passwd)){
                    Toast.makeText(LoginActivity.this, "各项均不能为空", Toast.LENGTH_SHORT).show();
                }
                else{
                    localMatch();
                //    netMatch();
                  //  setLogin_state(true);
                }
            }
        });

    }


    public void localMatch(){
        if (input_passwd.equals(local_passwd)) {
            Toast.makeText(LoginActivity.this, "登录成功！", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }
        //    }
        else {
            Toast.makeText(LoginActivity.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
        }
    }

    public void netMatch(){
        String url = "http://139.196.124.158:5000/login";//替换成自己的服务器地址
        SendMessage(url, input_account, input_passwd);
    }

    private void SendMessage(String url, final String userName, String passWord) {
        OkHttpClient client = new OkHttpClient();
        FormBody.Builder formBuilder = new FormBody.Builder();
        formBuilder.add("username", userName);
        formBuilder.add("password", passWord);
        Request request = new Request.Builder().url(url).post(formBuilder.build()).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(LoginActivity.this, "服务器错误", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                final String res = response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (res.equals("0")) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(LoginActivity.this, "该用户名已被注册", Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(LoginActivity.this, "成功", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(intent);
                                }
                            });

                        }

                    }
                });
            }
        });
    }

    private void acceptServer() throws IOException {
        //1.创建客户端Socket，指定服务器地址和端口
        Socket socket = new Socket("169.254.254.41", 12345);
        //2.获取输出流，向服务器端发送信息
        OutputStream os = socket.getOutputStream();//字节输出流
        PrintWriter pw = new PrintWriter(os);//将输出流包装为打印流
        //获取客户端的IP地址
        InetAddress address = InetAddress.getLocalHost();
        String ip = address.getHostAddress();

        pw.flush();
        socket.shutdownOutput();//关闭输出流
        socket.close();
    }


}
