package com.example.tryfirst;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Layout;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.LinkedList;

import io.alterac.blurkit.BlurKit;
import io.alterac.blurkit.BlurLayout;

public class ItemChangeActivity extends Activity {
    private String uuid;
    private Button change;
    private Button delete;
    private Button back;
    private TextView tv_amout;
    private TextView tv_type;
    private TextView ctime;
    private TextView tv_remark;

    private ImageView IVicon;
    private AssetList cdbr1;
    private CitrusDBHelper citrusDBHelper;
    private LinearLayout blurr;
    private int mon;
    private int day;
    private String time;
    private boolean isChanged ;
    private LinearLayout lltime;
    private LinearLayout llta;
    private BlurLayout blurLayout;


    //时间组修改
    private EditText edimon;
    private EditText ediday;
    private EditText edihor;
    private EditText edimin;

    private EditText edremark;

    private EditText tiamout;
    private EditText titype;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itemchange);
        //获取传入的uuid
        Intent intent = getIntent();
        if(intent != null){
            //获取intent中的参数
            uuid = intent.getStringExtra("uuid");
        }

        initCop();

        getThisData();
        setListenner();






    }


    private void setListenner() {
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(!isChanged){
                    isChanged = true;
                    changeData();

                } else{
                    isChanged = false;
                    upData();


                }

            }

        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ItemChangeActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                citrusDBHelper.removeThisRecord(uuid);
                Toast.makeText(ItemChangeActivity.this, "删除成功！", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(ItemChangeActivity.this,MainActivity.class);
                startActivity(intent);

            }
        });
    }

    private void upData() {
        cdbr1.settType(titype.getText().toString().trim());
        cdbr1.settAmount(Float.valueOf(tiamout.getText().toString().trim()));
        int tdate=0;
        tdate += Integer.valueOf(edimon.getText().toString().trim());
        tdate = 100 * tdate + Integer.valueOf(ediday.getText().toString().trim());
        cdbr1.setdate(tdate);
        cdbr1.setRemark(edremark.getText().toString().trim());
        cdbr1.setTime(edihor.getText().toString().trim()+":"+ edimin.getText().toString().trim());
        tv_amout.setText(String.valueOf(cdbr1.gettAmount()));
        tv_type.setText(cdbr1.gettType());

        dealTime();
        dealIcon();
        citrusDBHelper.editRecord(uuid,cdbr1);
        tv_amout.setVisibility(View.VISIBLE);
        tv_type.setVisibility(View.VISIBLE);
        ctime.setVisibility(View.VISIBLE);
        lltime.setVisibility(View.GONE);
        llta.setVisibility(View.GONE);
        Toast.makeText(ItemChangeActivity.this, "修改成功！", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(ItemChangeActivity.this,MainActivity.class);
        startActivity(intent);
    }

    private void changeData() {
        tv_amout.setVisibility(View.GONE);
        tv_type.setVisibility(View.GONE);
        ctime.setVisibility(View.GONE);
        tv_remark.setVisibility(View.GONE);
        edimon.setText(String.valueOf(mon));
        ediday.setText(String.valueOf(day));
        String[] tmp = cdbr1.getTime().split(":");
        edihor.setText(tmp[0]);
        edimin.setText(tmp[1]);
        edremark.setText(cdbr1.getRemark());
        titype.setText(cdbr1.gettType());
        tiamout.setText(String.valueOf(cdbr1.gettAmount()));
        edremark.setVisibility(View.VISIBLE);
        lltime.setVisibility(View.VISIBLE);
        llta.setVisibility(View.VISIBLE);
        change.setText("提交");



    }

    private void getThisData() {
       cdbr1 = citrusDBHelper.readRecord(uuid);
       tv_amout.setText(String.valueOf(cdbr1.gettAmount()));
       tv_type.setText(cdbr1.gettType());
       tv_remark.setText(cdbr1.getRemark());
       dealTime();
       dealIcon();


    }

    private void dealIcon() {
        int tIcon1;
        String tType = cdbr1.gettType();
        if(tType.equals("收入")){
            tIcon1 = R.drawable.icon_ticket;
        }else if(tType.equals("交通")){
            tIcon1 = R.drawable.icon_transport;
        }else if(tType.equals("购物")){
            tIcon1 = R.drawable.icon_shopping;
        }else if(tType.equals("饮食")){
            tIcon1 = R.drawable.icon_food;
        }else if(tType.equals("娱乐")){
            tIcon1 = R.drawable.icon_entertain;
        }else {
            tIcon1 = R.drawable.icon_appstore;
        }
        IVicon.setBackgroundResource(tIcon1);

    }

    private void dealTime() {
        time = cdbr1.getTime();
        day = cdbr1.gettdate()%100;
        mon = (cdbr1.gettdate()-day)/100;
        ctime.setText(mon+"月"+day+"日 "+time);
    }

    private void initCop() {
        tv_remark=findViewById(R.id.tv_remark);
        tv_amout =findViewById(R.id.tv_amout);
        tv_type = findViewById(R.id.tv_type);
        change = findViewById(R.id.btn_change);
        delete = findViewById(R.id.btn_del);
        back = findViewById(R.id.btn_back1);
        ctime = findViewById(R.id.time);
        IVicon = findViewById(R.id.ticon);
        blurr =findViewById(R.id.tbblur);
        citrusDBHelper = new CitrusDBHelper(this,CitrusDBHelper.DB_NAME,null,1);
        isChanged =false;
        blurLayout = findViewById(R.id.blurLayout);

        //修改组件
        lltime = findViewById(R.id.tochangetime);
        edimon = findViewById(R.id.inputmon);
        edimin = findViewById(R.id.inputmin);
        ediday = findViewById(R.id.inputday);
        edihor = findViewById(R.id.inputhour);
        edremark= findViewById(R.id.eTRemark);
        tiamout = findViewById(R.id.toinputamout);
        titype = findViewById(R.id.toinputtype);
        lltime = findViewById(R.id.tochangetime);
        llta = findViewById(R.id.toinputta);


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)

    {

        if (keyCode == KeyEvent.KEYCODE_BACK )
        {
            Intent intent = new Intent(ItemChangeActivity.this, MainActivity.class);
            startActivity(intent);
        }
        return false;

    }
    @Override
    protected void onStart() {
        super.onStart();
        blurLayout.startBlur();
    }

    @Override
    protected void onStop() {
        blurLayout.pauseBlur();
        super.onStop();
    }

}
