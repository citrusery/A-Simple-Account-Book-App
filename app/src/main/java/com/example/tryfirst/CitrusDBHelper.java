package com.example.tryfirst;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.LinkedList;

public class CitrusDBHelper extends SQLiteOpenHelper {

    private String TAG = "CitrusDBHelper";

    public static final String DB_NAME ="Record";

    private static final String CREATE_RECORD_DB = "create table Record("
            + "id integer primary key autoincrement, "
            + "type String ,"
            + "uuid text, "
            + "amount float,"
            + "date integer,"
            + "time String,"
            + "remark String,"
            + "user_name String )";



    public CitrusDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_RECORD_DB);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addRecord(AssetList cdbr){
        SQLiteDatabase  db = this.getWritableDatabase();
        ContentValues values = new ContentValues();             //???
        values.put("uuid",cdbr.getUuid());
        values.put("type",cdbr.gettType());
        values.put("amount",cdbr.gettAmount());
        values.put("date",cdbr.gettdate());
        values.put("time",cdbr.getTime());
        values.put("remark",cdbr.getRemark());
        values.put("user_name",cdbr.getUser_name());
        db.insert(DB_NAME, null, values);
        values.clear();
        Log.d(TAG,cdbr.getUuid()+"added");


    }

    public void  removeRecord(String uesr_name1){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DB_NAME,"user_name = ?",new String[]{uesr_name1});
    }

    public void editRecord(String uuid,AssetList record){
        removeThisRecord(uuid);
        record.setUuid(uuid);
        addRecord(record);
    }

    public AssetList readRecord(String Uuid){
        SQLiteDatabase db = this.getWritableDatabase();
        AssetList cdbr1 = new AssetList("出错",0);
        Cursor cursor = db.rawQuery("select DISTINCT * from Record where uuid = ? order by time asc",new String[]{Uuid});
        if(cursor.moveToFirst()){
            do{
                String type = cursor.getString (cursor.getColumnIndex("type"));
                String uuid = cursor.getString(cursor.getColumnIndex("uuid"));
                float amount = cursor.getFloat(cursor.getColumnIndex("amount"));
                int date =  cursor.getInt(cursor.getColumnIndex("date"));
                String timeStamp = cursor.getString(cursor.getColumnIndex("time"));
                String remark = cursor.getString(cursor.getColumnIndex("remark"));
                String user_name = cursor.getString(cursor.getColumnIndex("user_name"));


                cdbr1.setRemark(remark);
                cdbr1.settType(type);
                cdbr1.settAmount(amount);
                cdbr1.setUuid(uuid);
                cdbr1.setdate(date);
                cdbr1.setTime(timeStamp);
                return cdbr1;
            }while (cursor.moveToNext());

        }
        cursor.close();
        return cdbr1;
    }

    public LinkedList<AssetList> readRecords(String user_name1){
        LinkedList<AssetList> records = new LinkedList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select DISTINCT * from Record where user_name = ? order by time asc",new String[]{user_name1});
        if(cursor.moveToFirst()){
            do{
                String type = cursor.getString (cursor.getColumnIndex("type"));
                String uuid = cursor.getString(cursor.getColumnIndex("uuid"));
                float amount = cursor.getFloat(cursor.getColumnIndex("amount"));
                int date =  cursor.getInt(cursor.getColumnIndex("date"));
                String timeStamp = cursor.getString(cursor.getColumnIndex("time"));
                String user_name = cursor.getString(cursor.getColumnIndex("user_name"));
                String remark = cursor.getString(cursor.getColumnIndex("remark"));

                AssetList cdbr = new AssetList(type,amount);
                cdbr.setUuid(uuid);
                cdbr.setdate(date);
                cdbr.setTime(timeStamp);
                cdbr.setRemark(remark);

                records.add(cdbr);

            }while (cursor.moveToNext());

        }
        cursor.close();
        return records;
    }


    public void removeThisRecord(String uuid) {

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DB_NAME,"uuid = ?",new String[]{uuid});

    }
}
