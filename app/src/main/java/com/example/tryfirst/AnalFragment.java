package com.example.tryfirst;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import static com.example.tryfirst.CitrusDBRecord.TAG;

public class AnalFragment extends Fragment {
    private View view;
    private Switch aSwitch;
    private TextView tvmon;
    private Button btncu;
    private int i =0;
    private int date;
    private float day_out=0;
    private float mon_out=0;
    private AssetList cdbr;
    public CitrusDBHelper citrusDBHelper;
    private boolean flag = true;
    private PieChart pieChart,pie_chat2;
    List<PieEntry> yvals = new ArrayList<>();
    List<PieEntry> dvals = new ArrayList<>();
    List<Integer> colors = new ArrayList<>();

    //add to pie!
    private String  type;
    private float tamount[] = new float[7];
    private float mamount[] = new float[7];
    private float damount[] = new float[7];
    LinkedList<AssetList> datalist;
    private float widget=0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        //页面
        view = inflater.inflate(R.layout.tab_anal,container,false);
        aSwitch = view.findViewById(R.id.sw);
        //获取日信息
        Calendar ca = Calendar.getInstance();
        int month=ca.get(Calendar.MONTH);//获取月份
        int day=ca.get(Calendar.DATE);//获取日
        date = day + 100*(month+1);
        pieChart=view.findViewById(R.id.pie_chart_view);
        initColor();
        //载入支付信息
        tvmon = view.findViewById(R.id.mon_pay);
        citrusDBHelper = new CitrusDBHelper (getContext(),CitrusDBHelper.DB_NAME,null,1);





        resetmd();


        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked){                                             //表示月支出

                    flag = true;
                    tvmon.setText(mon_out + "￥");
                    showRingPieChart(flag);
                    pieChart.setVisibility(View.INVISIBLE);
                    pieChart.setVisibility(View.VISIBLE);
                }else {

                    flag =false;
                    tvmon.setText(day_out + "￥");
                    showRingPieChart(flag);
                    pieChart.setVisibility(View.INVISIBLE);
                    pieChart.setVisibility(View.VISIBLE);
                }
            }
        });
        if(datalist.size()==0){
            pieChart.setVisibility(View.INVISIBLE);

        }

        return view;

    }

    private void initColor() {
        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);




    }


    private void resetmd() {

        yvals.clear();
        dvals.clear();
        day_out = 0;
        mon_out=0;
        for(i=0;i<7;i+=1){
            tamount[i]=0;
        }

        datalist = citrusDBHelper.readRecords("admin");

        if(datalist.size()>0) {
            pieChart.setVisibility(View.VISIBLE);
            setWidget();

            for(i=0;i<6;i+=1){
                tamount[6]+=tamount[i];
                mamount[6]+=mamount[i];
                damount[6]+=damount[i];
            }
            resetPie();
        }
        tvmon.setText(mon_out+"￥");
        showRingPieChart(flag);
    }

    private void showPie(boolean k) {
        if(k) {
            switch (i) {
                case 0: {
                    yvals.add(new PieEntry(widget, "餐饮"));
                    break;
                }
                case 1: {
                    yvals.add(new PieEntry(widget, "购物"));
                    break;
                }
                case 2: {
                    yvals.add(new PieEntry(widget, "娱乐"));
                    break;
                }
                case 3: {
                    yvals.add(new PieEntry(widget, "交通"));
                    break;
                }
                case 4: {
                    yvals.add(new PieEntry(widget, "收入"));
                    break;
                }
                case 5: {
                    yvals.add(new PieEntry(widget, "其他"));
                    break;
                }

            }
        }
        else{ switch (i) {
            case 0: {
                dvals.add(new PieEntry(widget, "餐饮"));
                break;
            }
            case 1: {
                dvals.add(new PieEntry(widget, "购物"));
                break;
            }
            case 2: {
                dvals.add(new PieEntry(widget, "娱乐"));
                break;
            }
            case 3: {
                dvals.add(new PieEntry(widget, "交通"));
                break;
            }
            case 4: {
                dvals.add(new PieEntry(widget, "收入"));
                break;
            }
            case 5: {
                dvals.add(new PieEntry(widget, "其他"));
                break;
            }

        }

        }
    }



    private void showRingPieChart(boolean md) {
        PieChartManagger pieChartManagger=new PieChartManagger(pieChart);
        MainActivity activity = (MainActivity)getActivity();
        if(md){
            pieChartManagger.showRingPieChart(yvals,colors,!activity.getsettpieview());
        }
        else {
            pieChartManagger.showRingPieChart(dvals,colors,!activity.getsettpieview());
        }

    }


    private void setWidget() {
        for (i = 0; i < datalist.size(); i += 1) {
            cdbr = datalist.get(i);

            if (date == cdbr.gettdate()) {
                if (cdbr.gettType().equals("饮食")) {
                    damount[0] += cdbr.gettAmount();
                    day_out -= cdbr.gettAmount();
                } else if (cdbr.gettType().equals("购物")) {
                    damount[1] += cdbr.gettAmount();
                    day_out -= cdbr.gettAmount();
                } else if (cdbr.gettType().equals("娱乐")) {
                    damount[2] += cdbr.gettAmount();
                    day_out -= cdbr.gettAmount();
                } else if (cdbr.gettType().equals("交通")) {
                    damount[3] += cdbr.gettAmount();
                    day_out -= cdbr.gettAmount();

                } else if (cdbr.gettType().equals("收入")) {
                    damount[4] += cdbr.gettAmount();
                    day_out += cdbr.gettAmount();

                } else {
                    damount[5] += cdbr.gettAmount();
                    day_out -= cdbr.gettAmount();
                }
            }
            if ((date - cdbr.gettdate()) <= 100) {
                if (cdbr.gettType().equals("饮食")) {
                    mamount[0] += cdbr.gettAmount();
                    mon_out -= cdbr.gettAmount();
                } else if (cdbr.gettType().equals("购物")) {
                    mamount[1] += cdbr.gettAmount();
                    mon_out -= cdbr.gettAmount();
                } else if (cdbr.gettType().equals("娱乐")) {
                    mamount[2] += cdbr.gettAmount();
                    mon_out -= cdbr.gettAmount();
                } else if (cdbr.gettType().equals("交通")) {
                    mamount[3] += cdbr.gettAmount();
                    mon_out -= cdbr.gettAmount();

                } else if (cdbr.gettType().equals("收入")) {
                    mamount[4] += cdbr.gettAmount();
                    mon_out += cdbr.gettAmount();

                } else {
                    mamount[5] += cdbr.gettAmount();
                    mon_out -= cdbr.gettAmount();
                }
            }
        }

    }
    public void resetPie(){
        for (i = 0; i < 6; i += 1) {
            widget = (mamount[i] / mamount[6]);
            showPie(true);
        }
        for (i = 0; i < 6; i += 1) {
            widget = (damount[i] / damount[6]);
            showPie(false);
        }
    }



    public void clearup(){
        day_out=0;
        mon_out=0;
        tvmon.setText(mon_out+"￥");
        pieChart.setVisibility(View.INVISIBLE);

    }

    public void renew() {
        yvals.clear();
        dvals.clear();
        day_out = 0;
        mon_out=0;
        for(i=0;i<7;i+=1){
            tamount[i]=0;
            mamount[i]=0;
            damount[i]=0;
        }

        datalist = citrusDBHelper.readRecords("admin");

        if(datalist.size()>0) {
            pieChart.setVisibility(View.VISIBLE);
            setWidget();

            for(i=0;i<6;i+=1){
                tamount[6]+=tamount[i];
                mamount[6]+=mamount[i];
                damount[6]+=damount[i];
            }
            resetPie();

        }
        if(flag) {
            tvmon.setText(mon_out + "￥");
        }
        else{
            tvmon.setText(day_out+"￥");
        }
        showRingPieChart(flag);

    }


}
