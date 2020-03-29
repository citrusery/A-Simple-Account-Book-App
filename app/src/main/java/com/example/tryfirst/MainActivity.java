package com.example.tryfirst;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import android.text.Layout;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity {
    //设置碎片

    private List<Fragment> list;
    private LinearLayout popup;
    private EditText inputt;
    private Button fab;
    private Button btndeal0;
    private Button btndeal1;
    private Button btndeal2;
    private Button btndeal3;
    private Button btndeal4;
    private Button btndeal5;
    private Button btnsure;
    private Boolean flag = true;
    private float amount = 0f;              //输入金额


    private ViewPager mviewpager;           //声明viewPager对象
    private ImageView tabline;              //滑动条

    private RadioButton rb1;
    private RadioButton rb2;
    private RadioButton rb3;


    private int tabLineLength;              // 1/3屏幕宽
    private int tansy;
    private int currentPage = 1;            //设置初始页面为第二页
    private int choose = -1;                 //选择选项

    private MainFragment tab2;
    private AnalFragment tab1;
    private SettFragment tab3;
    private TextView tvmd;


    private boolean settaddidisplay = false;
    private boolean setttheme = false;
    private boolean settpieview = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_main);
        // 初始化滑动条1/3
        initTabLine();

        // 初始化界面
        initView();

        initDeal();




    }



    private void initTabLine() {
        Display display = getWindow().getWindowManager().getDefaultDisplay();   //获得屏幕信息
        DisplayMetrics metrics = new DisplayMetrics();                          //屏幕宽度
        display.getMetrics(metrics);
        //设置1/3屏幕宽度
        tabLineLength = metrics.widthPixels / 3;
        tansy = metrics.heightPixels;

        //获得控件实例
        tabline = findViewById(R.id.tabline);


    }

    private void initView() {
        list = new ArrayList<>();
        //实例化对象

        mviewpager = findViewById(R.id.viewpager);
        rb1 = findViewById(R.id.rb_anal);
        rb2 = findViewById(R.id.rb_main);
        rb3 = findViewById(R.id.rb_sett);
        popup = findViewById(R.id.ll_pop);
        fab = findViewById(R.id.floatingActionButton);
        inputt = findViewById(R.id.input);

    //


        tab1 = new AnalFragment();
        tab2 = new MainFragment();
        tab3 = new SettFragment();

        list.add(tab1);
        list.add(tab2);
        list.add(tab3);
        //设置适配器
        FragmentPagerAdapter adapter = new FragmentPagerAdapter(
                getSupportFragmentManager()) {

            @Override
            public int getCount() {
                return list.size();
            }

            @Override
            public Fragment getItem(int arg0) {
                return list.get(arg0);
            }


        };

        mviewpager.setAdapter(adapter);

        //设置滑动监视

        mviewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

                // 取得该控件的实例
                LinearLayout.LayoutParams ll = (android.widget.LinearLayout.LayoutParams) tabline
                        .getLayoutParams();




                if (currentPage == 0 && arg0 == 0) { // 0->1移动(第一页到第二页)
                    ll.leftMargin = (int) (currentPage * tabLineLength + arg1
                            * tabLineLength);
                } else if (currentPage == 1 && arg0 == 1) { // 1->2移动（第二页到第三页）
                    ll.leftMargin = (int) (currentPage * tabLineLength + arg1
                            * tabLineLength);
                } else if (currentPage == 1 && arg0 == 0) { // 1->0移动（第二页到第一页）
                    ll.leftMargin = (int) (currentPage * tabLineLength - ((1 - arg1) * tabLineLength));
                } else if (currentPage == 2 && arg0 == 1) { // 2->1移动（第三页到第二页）
                    ll.leftMargin = (int) (currentPage * tabLineLength - (1 - arg1)
                            * tabLineLength);
                    if(tab3.getFlag()) {
                    //    Toast.makeText(MainActivity.this,"已改变",Toast.LENGTH_SHORT).show();

                        tab2.renewList(tab3.getSet4());
                    }
                }

                tabline.setLayoutParams(ll);

            }

            @Override
            public void onPageSelected(int position) {
                //选择页面时，三个选项字体变为蓝色
                reSetColorofBtn();
                if(!flag){
                    popup.setVisibility(View.GONE);

                    fab.setTranslationY(0);
                    flag=true;
                }
               InputMethod.closeInputMethod(MainActivity.this,popup);


                //再次改变选择页：
                switch (position) {
                    case 0:
                        rb1.setTextColor(Color.rgb(192, 200, 250));
                        break;
                    case 1:
                        rb2.setTextColor(Color.rgb(192, 200, 250));
                        break;
                    case 2:
                        rb3.setTextColor(Color.rgb(192, 200, 250));
                        break;
                }

                currentPage = position;
            }


            @Override
            public void onPageScrollStateChanged(int arg0) {
                // TODO Auto-generated method stub

            }


        });

        // 点击事件
        rb1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                if(flag){
                    reSetColorofBtn();
                    rb1.setTextColor(Color.rgb(192, 200, 250));
                    mviewpager.setCurrentItem(0);
                }
            }
        });
        rb2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(flag){
                    reSetColorofBtn();
                    rb2.setTextColor(Color.rgb(192, 200, 250));
                    mviewpager.setCurrentItem(1);
                }
             //   popup.setVisibility(View.VISIBLE);
            }
        });
        rb3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(flag){
                    reSetColorofBtn();
                    rb3.setTextColor(Color.rgb(192, 200, 250));
                    mviewpager.setCurrentItem(2);
                }
            }
        });

        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                SharedPreferences sp = getSharedPreferences("settInfo", MODE_PRIVATE);
                if(Boolean.valueOf(sp.getString("sett1","")).booleanValue()){
                    displayAddHalf();
                } else{
                    displayAddAll();
                }
            }
        });





    }

    private void displayAddAll() {
        Intent intent = new Intent(MainActivity.this, AddRecordAllActivity.class);
        new ActSwitchAnimTool(MainActivity.this).setAnimType(ActSwitchAnimTool.MODE_SPREAD)
                .target(fab)
                .setmColorStart(Color.parseColor("#FF5777"))
                .setmColorEnd(Color.parseColor("#FF5777"))
                .startActivity(intent, true)
                .build();
    }

    private void displayAddHalf() {
        if(flag){
            reSetColorofBtn();
            rb2.setTextColor(Color.rgb(192, 200, 250));




            mviewpager.setCurrentItem(1);
            final TranslateAnimation ctrlAnimation = new TranslateAnimation(
                    TranslateAnimation.RELATIVE_TO_SELF,0,TranslateAnimation.RELATIVE_TO_SELF,0,
                    TranslateAnimation.RELATIVE_TO_SELF,1,TranslateAnimation.RELATIVE_TO_SELF,0);
            ctrlAnimation.setDuration(100);
            popup.postDelayed(new Runnable() {
                @Override
                public void run() {
                    popup.setVisibility(View.VISIBLE);
                    popup.startAnimation(ctrlAnimation);
                }
            },0);
            fab.postDelayed(new Runnable() {
                @Override
                public void run() {
                    fab.setTranslationY(-   tansy / 3 - 50 );
                }
            },50 );

            flag=false;
        }
        else{
            popup.setVisibility(View.GONE);
            InputMethod.closeInputMethod(MainActivity.this,popup);
            fab.setTranslationY(0);
            flag=true;
        }
    }

    public void reSetColorofBtn(){
        rb1.setTextColor(Color.GRAY);
        rb2.setTextColor(Color.GRAY);
        rb3.setTextColor(Color.GRAY);

    }
    private void initDeal() {
        btndeal0 = findViewById(R.id.butd0);
        btndeal1 = findViewById(R.id.butd1);
        btndeal2 = findViewById(R.id.butd2);
        btndeal3 = findViewById(R.id.butd3);
        btndeal4 = findViewById(R.id.butd4);
        btndeal5 = findViewById(R.id.butd5);
        btnsure = findViewById(R.id.sure);





                btndeal0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setChoose(0);
                setColor(0);
            }
        });
        btndeal1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setChoose(1);
                setColor(1);
            }
        });
        btndeal2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setChoose(2);
                setColor(2);
            }
        });
        btndeal3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setChoose(3);
                setColor(3);
            }
        });
        btndeal4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setChoose(4);
                setColor(4);
            }
        });
        btndeal5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setChoose(5);
                setColor(5);
            }
        });
        btnsure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(inputt.getText().toString().trim().equals("")) {

                    Toast.makeText(MainActivity.this, "输入不能为空！", Toast.LENGTH_SHORT).show();
                }
                else if(choose==-1) {
                    Toast.makeText(MainActivity.this, "请选择一个种类！", Toast.LENGTH_SHORT).show();
                }else{
                    amount = Float.parseFloat(inputt.getText().toString().trim());
                    inputt.setText("");
                    InputMethod.closeInputMethod(MainActivity.this,popup);
                    tab2.AddRecord(MainActivity.this, amount, choose);
                    tab1.renew();




                }
            }
        });

    }
    private void setColor(int i) {
        switch (i){
            case 0:
                reSetColor();
                btndeal0.setAlpha(0.6f);
                break;
            case 1:
                reSetColor();
                btndeal1.setAlpha(0.6f);
                break;
            case 2:
                reSetColor();
                btndeal2.setAlpha(0.6f);
                break;
            case 3:
                reSetColor();
                btndeal3.setAlpha(0.6f);
                break;
            case 4:
                reSetColor();
                btndeal4.setAlpha(0.6f);
                break;
            case 5:
                reSetColor();
                btndeal5.setAlpha(0.6f);
                break;
            case -1:
                reSetColor();
                break;



        }
    }
    private void setChoose(int i) {
        if (choose!= i) {
            choose = i;
        }

    }
    private void reSetColor() {
        btndeal0.setAlpha(1.0f);
        btndeal1.setAlpha(1.0f);
        btndeal2.setAlpha(1.0f);
        btndeal3.setAlpha(1.0f);
        btndeal4.setAlpha(1.0f);
        btndeal5.setAlpha(1.0f);

    }
    public void analclearup(){
        tab1.clearup();     //权宜之计
    }
    
    public boolean getsettpieview(){
        return tab3.getSet2();
    }

    public static void renewTList(){


    }


}