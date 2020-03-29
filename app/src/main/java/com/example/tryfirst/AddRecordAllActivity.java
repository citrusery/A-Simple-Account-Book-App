package com.example.tryfirst;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

import io.alterac.blurkit.BlurLayout;

import static java.security.AccessController.getContext;

public class AddRecordAllActivity extends Activity implements View.OnClickListener{

    private static String TAG = "AddRecordActivity";

    private EditText editText;
    private TextView amountText;
    private String userInput = "";

    private BlurLayout blurLayout;

    private Button back;
    private Button btndeal0;
    private Button btndeal1;
    private Button btndeal2;
    private Button btndeal3;
    private Button btndeal4;
    private Button btndeal5;
    private int choose=0;
    private CitrusDBHelper citrusDBHelper;
    private AssetList cdbr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addrecord);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        citrusDBHelper = new CitrusDBHelper (AddRecordAllActivity.this,CitrusDBHelper.DB_NAME,null,1);
        blurLayout = findViewById(R.id.blurit);
        amountText = (TextView) findViewById(R.id.textView_amount);
        editText = findViewById(R.id.editText);
        back = findViewById(R.id.temp);
        initDeal();


        initbtn();
        handleBackspace();
        handleDot();
        handleDone();
        
    }

    private void handleDot() {
        findViewById(R.id.keyboard_dot).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!userInput.contains(".")) {
                    userInput += ".";
                }
            }
        });
    }

    private void handleDone() {
        findViewById(R.id.keyboard_done).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!userInput.equals("")) {
                    float amount = Float.valueOf(userInput);
                    addRecord(amount,choose);
                    Intent intent = new Intent(AddRecordAllActivity.this, MainActivity.class);
                    startActivity(intent);


                } else {
                    Toast.makeText(getApplicationContext(), "金额不能为0", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
    private void initDeal() {
        btndeal0 = findViewById(R.id.btn_type1);
        btndeal1 = findViewById(R.id.btn_type2);
        btndeal2 = findViewById(R.id.btn_type3);
        btndeal3 = findViewById(R.id.btn_type4);
        btndeal4 = findViewById(R.id.btn_type5);
        btndeal5 = findViewById(R.id.btn_type6);
        reSetColor();

        new ActSwitchAnimTool(this)
                .receiveIntent(getIntent())
                .setAnimType(ActSwitchAnimTool.MODE_SHRINK)
                .target(back)
                .build();



        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddRecordAllActivity.this, MainActivity.class);
                startActivity(intent);

            }
        });
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
    }

    private void setColor(int i) {
        switch (i){
            case 0:
                reSetColor();
                btndeal0.setAlpha(1.0f);
                break;
            case 1:
                reSetColor();
                btndeal1.setAlpha(1.0f);
                break;
            case 2:
                reSetColor();
                btndeal2.setAlpha(1.0f);
                break;
            case 3:
                reSetColor();
                btndeal3.setAlpha(1.0f);
                break;
            case 4:
                reSetColor();
                btndeal4.setAlpha(1.0f);
                break;
            case 5:
                reSetColor();
                btndeal5.setAlpha(1.0f);
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
        btndeal0.setAlpha(0.5f);
        btndeal1.setAlpha(0.5f);
        btndeal2.setAlpha(0.5f);
        btndeal3.setAlpha(0.5f);
        btndeal4.setAlpha(0.5f);
        btndeal5.setAlpha(0.5f);

    }

    private void handleBackspace() {
        findViewById(R.id.keyboard_backspace).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userInput.length() > 0) {
                    userInput = userInput.substring(0, userInput.length() - 1);
                }
                if (userInput.length() > 0 && userInput.charAt(userInput.length() - 1) == '.') {
                    userInput = userInput.substring(0, userInput.length() - 1);
                }
                updateAmountText();
            }
        });
    }

    private void initbtn() {
        findViewById(R.id.keyboard_one).setOnClickListener(this);
        findViewById(R.id.keyboard_two).setOnClickListener(this);
        findViewById(R.id.keyboard_three).setOnClickListener(this);
        findViewById(R.id.keyboard_four).setOnClickListener(this);
        findViewById(R.id.keyboard_five).setOnClickListener(this);
        findViewById(R.id.keyboard_six).setOnClickListener(this);
        findViewById(R.id.keyboard_seven).setOnClickListener(this);
        findViewById(R.id.keyboard_eight).setOnClickListener(this);
        findViewById(R.id.keyboard_nine).setOnClickListener(this);
        findViewById(R.id.keyboard_zero).setOnClickListener(this);

    }

    @Override

    public boolean onKeyDown(int keyCode, KeyEvent event)

    {

        if (keyCode == KeyEvent.KEYCODE_BACK )

        {
            Intent intent = new Intent(AddRecordAllActivity.this, MainActivity.class);
            startActivity(intent);
        }

        return false;

    }


    @Override
    public void onClick(View v) {
        Button button = (Button) v;
        String input = button.getText().toString();
        if (userInput.contains(".")) {
            if (userInput.split("\\.").length == 1 || userInput.split("\\.")[1].length() < 2) {
                userInput += input;
            }
        } else {
            userInput += input;
        }
        updateAmountText();
    }

    private void updateAmountText() {
        if (userInput.contains(".")) {
            if (userInput.split("\\.").length == 1) {
                amountText.setText(userInput + "00");
            } else if (userInput.split("\\.")[1].length() == 1) {
                amountText.setText(userInput + "0");
            } else if (userInput.split("\\.")[1].length() == 2) {
                amountText.setText(userInput);
            }
        } else {
            if (userInput.equals("")) {
                amountText.setText("0.00");
            } else {
                amountText.setText(userInput + ".00");
            }
        }
    }

    public void addRecord( float amount , int choose) {
        switch (choose) {
            case 0:
                cdbr = new AssetList("饮食", amount);
                break;
            case 1:
                cdbr = new AssetList("购物", amount);
                break;
            case 2:
                cdbr = new AssetList("娱乐", amount);
                break;
            case 3:
                cdbr = new AssetList("交通", amount);
                break;
            case 4:
                cdbr = new AssetList("收入", amount);
                break;
            case 5:
                cdbr = new AssetList("其他", amount);
                break;
            case -1:
                Toast.makeText(AddRecordAllActivity.this, "请选择一个种类", Toast.LENGTH_SHORT).show();
                break;
        }
        cdbr.setTime(DateFormat.format("hh:mm", new Date()).toString());
        cdbr.setRemark(editText.getText().toString().trim());
        citrusDBHelper.addRecord(cdbr);
        Toast.makeText(AddRecordAllActivity.this, "添加成功！", Toast.LENGTH_SHORT).show();
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
