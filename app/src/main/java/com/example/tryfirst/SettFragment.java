package com.example.tryfirst;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import static android.content.Context.MODE_PRIVATE;


public class SettFragment extends Fragment {

    private boolean settaddidisplay = false;            //全屏设定
    private boolean setttheme = false;
    private boolean settpieview = false;
    private boolean settitem = false;
    private Switch sett1;
    private Switch sett2;
    private Switch sett3;
    private Switch sett4;
    private MainActivity ac;

    private View view;
    private boolean flag = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        view =  inflater.inflate(R.layout.tab_sett,container,false);

        ac = new MainActivity();
        initBtn();
        initSett();
        btnListener();

        saveSettInfo();
        return view;
    }

    private void btnListener() {
        sett1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked){
                    settaddidisplay = true;

                }else {
                    settaddidisplay = false;
                }
                saveSettInfo();
            }
        });
        sett2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    settpieview = true;

                }else {
                    settpieview = false;
                }
                saveSettInfo();
            }
        });
        sett3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Toast.makeText(getContext(),"这里在一段时间都将只会是橙子",Toast.LENGTH_SHORT).show();
            }
        });
        sett4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    settitem = true;

                }else {
                    settitem = false;
                }
                flag = true;

                saveSettInfo();

            }
        });
    }


    private void initBtn(){
        sett1 = view.findViewById(R.id.settadddisplay);
        sett2 = view.findViewById(R.id.settpieview);
        sett3 = view.findViewById(R.id.setttheme);
        sett4 = view.findViewById(R.id.setitem);

    }
    private void initSett() {
        readSettInfo();
        setswitch();
    }

    private void setswitch() {
        if(settaddidisplay){
            sett1.setChecked(false);
        }else {
            sett1.setChecked(true);
        }

        if(settpieview){
            sett2.setChecked(true);
        } else {
            sett2.setChecked(false);
        }

        if(settitem){
            sett4.setChecked(true);
        }else {
            sett4.setChecked(false);
        }
    }

    public boolean getSet1(){
        return settaddidisplay;
    }

    public boolean getSet2(){
        return settpieview;
    }

    public boolean getSet3(){
        return setttheme;
    }

    public boolean getSet4() { return  settitem; }

    private void readSettInfo(){
        SharedPreferences sp = getContext().getSharedPreferences("settInfo", MODE_PRIVATE);
        if(!sp.getString("sett1","").equals(null)) {
            settaddidisplay = Boolean.valueOf(sp.getString("sett1", "")).booleanValue();
        }
        if(!sp.getString("sett2","").equals(null)) {
            settpieview = Boolean.valueOf(sp.getString("sett2","")).booleanValue();
        }
        if(!sp.getString("sett4","").equals(null)) {
            settitem = Boolean.valueOf(sp.getString("sett4","" )).booleanValue();
        }

    }

    private void saveSettInfo(){
        //loginInfo表示文件名, mode_private SharedPreferences sp = getSharedPreferences( );
        SharedPreferences sp=getContext().getSharedPreferences("settInfo", MODE_PRIVATE);
        //获取编辑器， SharedPreferences.Editor  editor -> sp.edit();
        SharedPreferences.Editor editor=sp.edit();
        //以用户名为key，密码为value保存在SharedPreferences中
        //key,value,如键值对，editor.putString(用户名，密码）;
        editor.putString("sett1",String.valueOf(settaddidisplay));
        editor.putString("sett2",String.valueOf(settpieview));
        editor.putString("sett4",String.valueOf(settitem));
        //提交修改 editor.commit();
        editor.commit();
    }

    public boolean getFlag() {
        return flag;
    }
}
