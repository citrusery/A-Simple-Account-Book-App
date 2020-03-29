package com.example.tryfirst;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.ramotion.foldingcell.FoldingCell;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;


public class MainFragment extends Fragment {

    public CitrusDBHelper citrusDBHelper;
    private Button btn;
    private TextView dateText;
    private Context mContext;
    private ListView asset_list;
    private View view;
    private int day;
    private int month;
    private float amount;
    private int choose;
    private LinkedList<AssetList> datalist = new LinkedList<>();
    private AssetListAdapter aAdapter;
    private FoldingCellListAdapter fAdapter;
    private AssetList cdbr;
    private int date;
    private Button clearup;

    private boolean setAdapter = true;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        view = inflater.inflate(R.layout.tab_main,container,false);
        Calendar ca = Calendar.getInstance();
        month=ca.get(Calendar.MONTH);//获取月份
        day=ca.get(Calendar.DATE);//获取日
        dateText =  view.findViewById(R.id.day_text);
        dateText.setText(MonthConvert(month) +" "+Integer.toString(day));
        asset_list=(ListView) view.findViewById(R.id.listView);
        date = day + 100*(month+1);
        citrusDBHelper = new CitrusDBHelper (getContext(),CitrusDBHelper.DB_NAME,null,1);
        initSetAdapter();
   //     citrusDBHelper.removeRecord("admin");
        display();
        if(setAdapter){
            if(fAdapter.getCount()>0){
                view.findViewById(R.id.no_record_layout).setVisibility(View.INVISIBLE);
            }

            if(fAdapter.getCount()==0){
                view.findViewById(R.id.no_record_layout).setVisibility(View.VISIBLE);
            }
        }
        else{
            if(aAdapter.getCount()>0){
                view.findViewById(R.id.no_record_layout).setVisibility(View.INVISIBLE);
            }

            if(aAdapter.getCount()==0){
                view.findViewById(R.id.no_record_layout).setVisibility(View.VISIBLE);
            }
        }

        clearup = view.findViewById(R.id.clearup);
        clearup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(setAdapter){
                    if (fAdapter.getCount() > 0) {
                        citrusDBHelper.removeRecord("admin");
                        display();
                        MainActivity activity = (MainActivity) getActivity();
                        activity.analclearup();
                    }
                }else{
                    if (aAdapter.getCount() > 0) {
                        citrusDBHelper.removeRecord("admin");
                        display();
                        MainActivity activity = (MainActivity)getActivity();
                        activity.analclearup();
                    }
                }
            }
        });




        if(setAdapter){
            asset_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                    // toggle clicked cell state
                    ((FoldingCell) view).toggle(false);
                    // register in adapter that state for selected cell is toggled
                    fAdapter.registerToggle(pos);
                }
            });
        }





        return view;
    }

    private void initSetAdapter() {
        SharedPreferences sp = getContext().getSharedPreferences("settInfo", MODE_PRIVATE);
        if(!sp.getString("sett4","").equals(null)) {
            setAdapter = Boolean.valueOf(sp.getString("sett4","" )).booleanValue();
        }
    }


    private String MonthConvert(int month) {
        switch (month){
            case 0:
                return "JANUARY";
            case 1:
                return "FEBRUARY";
            case 2:
                return "MARCH";
            case 3:
                return "APRIL";
            case 4:
                return "MAY";
            case 5:
                return "JUNE";
            case 6:
                return "JULY";
            case 7:
                return "AUGUST";
            case 8:
                return "SEPTEMBER";
            case 9:
                return "OCTOBER";
            case 10:
                return "NOVEMBER";
            case 11:
                return "DECEMBER";
        }
        return "UNKOWN ERROR";
    }

    public void AddRecord(Context mContext,float amount , int choose) {
        this.mContext = mContext;
        this.amount = amount;
        this.choose = choose;


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
                Toast.makeText(mContext, "请选择一个种类", Toast.LENGTH_SHORT).show();
                break;
        }
        cdbr.setTime(DateFormat.format("hh:mm", new Date()).toString());

        citrusDBHelper.addRecord(cdbr);


        display();
        if (setAdapter) {
            if (fAdapter.getCount() > 0) {
                view.findViewById(R.id.no_record_layout).setVisibility(View.INVISIBLE);
            }
        } else {
            if (aAdapter.getCount() > 0) {
                view.findViewById(R.id.no_record_layout).setVisibility(View.INVISIBLE);
            }
        }

    }

    public void display(){
        datalist.clear();
        datalist = citrusDBHelper.readRecords("admin");
        Collections.reverse(datalist);
        if(setAdapter) {
            useFoldedAdapter();
        }
        else{
            useAssetListAdapter();
        }
    }

    public void useFoldedAdapter(){
        fAdapter = new FoldingCellListAdapter(datalist,getContext());
        fAdapter.setDefaultDelBtnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                renewList(true);

            }
        });
        fAdapter.setDefaultChaBtnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getContext(), "这里执行修改操作", Toast.LENGTH_SHORT).show();
            }
        });
        asset_list.setAdapter(fAdapter);
    }

    public void useAssetListAdapter(){
        aAdapter = new AssetListAdapter((LinkedList<AssetList>)datalist,getContext());
        asset_list.setAdapter(aAdapter);
    }

    public void renewList(boolean tsetAdapter){
        if(tsetAdapter) {
            useFoldedAdapter();
        }
        else{
            useAssetListAdapter();
        }


    }

}
