package com.example.tryfirst;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.ramotion.foldingcell.FoldingCell;

import org.w3c.dom.Text;

import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

/**
 * Simple example of ListAdapter for using with Folding Cell
 * Adapter holds indexes of unfolded elements for correct work with default reusable views behavior
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public class FoldingCellListAdapter extends BaseAdapter{

    private LinkedList<AssetList> aDataList;
    private Context mContext;
    private String ttype;
    private HashSet<Integer> unfoldedIndexes = new HashSet<>();
    private View.OnClickListener defaultDelBtnClickListener;
    private View.OnClickListener defaultChaBtnClickListener;

    public FoldingCellListAdapter(LinkedList<AssetList> aDataList, Context mContext){
        this.aDataList = aDataList;
        this.mContext = mContext;
    }
/*
    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        // get item for selected view
        AssetList item = getItem(position);
        // if cell is exists - reuse it, if not - create the new one from resource
        FoldingCell cell = (FoldingCell) convertView;
        ViewHolder viewHolder;
        if (cell == null) {
            viewHolder = new ViewHolder();
            LayoutInflater vi = LayoutInflater.from(getContext());
            cell = (FoldingCell) vi.inflate(R.layout.cellfolded_list_view, parent, false);
            // binding view parts to view holder

            viewHolder.img_icon = convertView.findViewById(R.id.imageView_category1);
            viewHolder.type = convertView.findViewById(R.id.textView_remark1);
            viewHolder.amount = convertView.findViewById(R.id.textView_amount1);
            viewHolder.time = convertView.findViewById(R.id.textView_time1);
            cell.setTag(viewHolder);
        } else {
            // for existing cell set valid valid state(without animation)
            if (unfoldedIndexes.contains(position)) {
                cell.unfold(true);
            } else {
                cell.fold(true);
            }
            viewHolder = (ViewHolder) cell.getTag();
        }

        if (null == item)
            return cell;

        // bind data from selected element to view through view holder
        viewHolder.img_icon.setBackgroundResource(item.gettIcon());
        viewHolder.time.setText(item.getTime());
        viewHolder.type.setText(item.gettType());
        if(item.gettType().equals("收入")) {
            viewHolder.amount.setText("+" + item.gettAmount());
        }
        else{
            viewHolder.amount.setText("-" + item.gettAmount());
        }


        // set custom btn handler for list item from that item
   //     if (item.getRequestBtnClickListener() != null) {
     //       viewHolder.contentRequestBtn.setOnClickListener(item.getRequestBtnClickListener());
 //       } else {
            // (optionally) add "default" handler if no handler found in item
   //         viewHolder.contentRequestBtn.setOnClickListener(defaultRequestBtnClickListener);
    //    }

        return cell;
    }*/

    // simple methods for register cell state changes
    public void registerToggle(int position) {
        if (unfoldedIndexes.contains(position))
            registerFold(position);
        else
            registerUnfold(position);
    }

    public void registerFold(int position) {
        unfoldedIndexes.remove(position);
    }

    public void registerUnfold(int position) {
        unfoldedIndexes.add(position);
    }

    public View.OnClickListener getDefaultDelBtnClickListener() {
        return defaultDelBtnClickListener;
    }

    public void setDefaultDelBtnClickListener(View.OnClickListener defaultDelBtnClickListener) {
        this.defaultDelBtnClickListener = defaultDelBtnClickListener;
    }
    public View.OnClickListener getDefaultChaBtnClickListener() {
            return defaultChaBtnClickListener;
    }

    public void setDefaultChaBtnClickListener(View.OnClickListener defaultChaBtnClickListener) {
        this.defaultChaBtnClickListener = defaultChaBtnClickListener;
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
        convertView = LayoutInflater.from(mContext).inflate(R.layout.cellfolded_list_view,parent,false);
        ImageView img_icon = convertView.findViewById(R.id.imageView_category1);
        ImageView img_icon2 =convertView.findViewById(R.id.ticon2);
        TextView type = convertView.findViewById(R.id.textView_remark1);
        TextView amount = convertView.findViewById(R.id.textView_amount1);
        TextView time = convertView.findViewById(R.id.textView_time1);
        TextView type2 = convertView.findViewById(R.id.tv_type_folded);
        TextView amount2 = convertView.findViewById(R.id.tv_amout_folded);
        TextView time2 = convertView.findViewById(R.id.time_folded);
        TextView btn_del = convertView.findViewById(R.id.btn_del1);
        TextView btn_cha = convertView.findViewById(R.id.btn_change1);
        TextView remark = convertView.findViewById(R.id.content_of_remark);

        ttype = aDataList.get(position).gettType();
        img_icon.setBackgroundResource(aDataList.get(position).gettIcon());

        img_icon2.setBackgroundResource(aDataList.get(position).gettIcon());
        remark.setText(aDataList.get(position).getRemark());
        type.setText(ttype);
        type2.setText(ttype);
        if(ttype.equals("收入")) {
            amount.setText("+" + aDataList.get(position).gettAmount());
            amount2.setText("+"+ aDataList.get(position).gettAmount());
        }
        else{
            amount.setText("-" + aDataList.get(position).gettAmount());
            amount2.setText("-"+ aDataList.get(position).gettAmount());
        }

        btn_cha.setOnClickListener(defaultChaBtnClickListener);
        btn_del.setOnClickListener(defaultDelBtnClickListener);



        String currentTime= DateFormat.format("hh:mm", new Date()).toString();
        time.setText(aDataList.get(position).getTime());
        String ttime = aDataList.get(position).getTime();
        int day = aDataList.get(position).gettdate()%100;
        int mon = (aDataList.get(position).gettdate()-day)/100;
        time2.setText(mon+"月"+day+"日 "+ttime);

        return convertView;

    }




}
