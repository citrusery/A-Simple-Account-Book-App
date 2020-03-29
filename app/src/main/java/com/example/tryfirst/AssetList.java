package com.example.tryfirst;

import android.graphics.drawable.Drawable;
import android.view.View;

import java.util.Calendar;
import java.util.UUID;

//定义列表中的Item
public class AssetList {
    private String tType;            //收支种类
    private float tAmount;          //数额
    private int tIcon;              //图标
    private int date;               //日期
    private String currenttime;     //具体时间
    private String uuid;
    private long timestamp;
    private String time;
    private String uer_name;
    private View.OnClickListener chaBtnClickListener;
    private View.OnClickListener delBtnClickListener;
    private String remark;

    public AssetList(String tType,float tAmount){
        this.tType = tType;
        this.tAmount = tAmount;
        this.tIcon = setiIcon();
        uuid = UUID.randomUUID().toString();
        Calendar ca = Calendar.getInstance();
        this.date = (ca.get(Calendar.MONTH)+1)*100+(ca.get(Calendar.DATE));
    }



    private int setiIcon() {
        int tIcon1;
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
        return tIcon1;
    }

    public void setTime(String time){
        this.time = time;
    }

    public String gettType(){
        return tType;
    }

    public float gettAmount(){
        return tAmount;
    }

    public int gettIcon(){
        return tIcon;
    }

    public int gettdate(){

        return date;
    }

    public String getTime(){
        return time;
    }

    public String getRemark(){
        return remark;
    }

    public void setDelBtnClickListener(View.OnClickListener delBtnClickListener) {
        this.delBtnClickListener = delBtnClickListener;
    }

    public View.OnClickListener getDelBtnClickListener() {
        return delBtnClickListener;
    }

    public void setChaBtnClickListener(View.OnClickListener chaBtnClickListener) {
        this.chaBtnClickListener = chaBtnClickListener;
    }

    public View.OnClickListener getChaBtnClickListener() {
        return chaBtnClickListener;
    }

    public String getCurrenttime(){ return currenttime;}

    public void settType(String tType){
        this.tType = tType;
    }

    public void settAmount(float tAmount){
        this.tAmount = tAmount;
    }

    public void settIcon(int tIcon){
        this.tIcon = tIcon;
    }

    public void setdate(int tdate){
        this.date = tdate;
    }

    public void setCurrenttime(String currenttime){
        this.currenttime = currenttime;
    }

    public long getTimestamp(){
        timestamp = System.currentTimeMillis();
        return timestamp;
    }

    public String getUuid(){
        return uuid;
    }
    public String getUser_name(){
        return "admin";
    }
    public void setTimestamp(long timestamp){
        this.timestamp = timestamp;
    }

    public void setUuid(String uuid){
        this.uuid = uuid;
    }

    public void setUer_name(String usr_name){

    }


    public void setRemark(String remark) {
        this.remark = remark;
    }
}
