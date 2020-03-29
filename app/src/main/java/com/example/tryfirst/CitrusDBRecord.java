package com.example.tryfirst;

import java.io.Serializable;
import java.util.Calendar;
import java.util.UUID;

public class CitrusDBRecord implements Serializable {
    public static String TAG ="CitrusDBRecord";


    private String uuid;
    private String type;
    private float amount;
    private int date;
    private long timeStamp;
    private String user_name;

    public CitrusDBRecord(){
        uuid = UUID.randomUUID().toString();
        timeStamp = System.currentTimeMillis();
        Calendar ca = Calendar.getInstance();
        date = (ca.get(Calendar.MONTH))*10+(ca.get(Calendar.DATE));
    }

    public static String getTAG() {
        return TAG;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String  getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;           //凭借外部输入

    }

    public int getDate(){
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public void setUser_name(String user_name){
        this.user_name = user_name;
    }

    public String getUser_name(){
        return user_name;
    }
    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

}
