package com.example.tryfirst;

import android.content.Context;
import android.content.Intent;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ramotion.foldingcell.FoldingCell;

import java.util.Date;
import java.util.LinkedList;

import static java.security.AccessController.getContext;

public class AssetListAdapter extends BaseAdapter {
    private LinkedList<AssetList> aDataList;
    private Context mContext;
    private String ttype;

    public AssetListAdapter(LinkedList<AssetList> aDataList, Context mContext){
        this.aDataList = aDataList;
        this.mContext = mContext;
    }

    @Override
    public int getCount(){
        return aDataList.size();
    }

    @Override
    public Object getItem(int position){
        return null;
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent){
        convertView = LayoutInflater.from(mContext).inflate(R.layout.cell_list_view,parent,false);
        ImageView img_icon = convertView.findViewById(R.id.imageView_category);
        TextView type = convertView.findViewById(R.id.textView_remark);
        TextView amount = convertView.findViewById(R.id.textView_amount);
        TextView time = convertView.findViewById(R.id.textView_time);

        TextView menuView = convertView.findViewById(R.id.menu);
        ttype = aDataList.get(position).gettType();
        img_icon.setBackgroundResource(aDataList.get(position).gettIcon());
        type.setText(ttype);
        if(ttype.equals("收入")) {
            amount.setText("+" + String.valueOf(aDataList.get(position).gettAmount()));
        }
        else{
            amount.setText("-" + String.valueOf(aDataList.get(position).gettAmount()));
        }
        String currentTime= DateFormat.format("hh:mm", new Date()).toString();
        time.setText(aDataList.get(position).getTime());
        menuView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uuid;
                uuid = aDataList.get(position).getUuid();
                Intent intent = new Intent(mContext, ItemChangeActivity.class);
                intent.putExtra("uuid", uuid);
                mContext.startActivity(intent);

            }
        });

        return convertView;

    }




}
