package com.example.tryfirst;

import android.content.Context;

import java.util.LinkedList;

public class AllSet {
    private static final String TAG = "AllSet";
    private static AllSet instance;
    public CitrusDBHelper databaseHelper;
    private Context context;
    public MainActivity mainActivity;
    public LinkedList<CategoryRes> costRes = new LinkedList<>();
    public LinkedList<CategoryRes> earnRes = new LinkedList<>();
    private static int[] costIconRes = {
            R.drawable.icon_food_white,
            R.drawable.icon_shopping_white,
            R.drawable.icon_entertain_white,
            R.drawable.icon_transport_white,
            R.drawable.icon_ticket_white,
            R.drawable.icon_appstore_white

    };
    private static int[] costIconResBlack = {
            R.drawable.icon_food,
            R.drawable.icon_shopping,
            R.drawable.icon_entertain,
            R.drawable.icon_transport,
            R.drawable.icon_ticket,
            R.drawable.icon_appstore


    };

    private static String[] costTitle = {"餐饮", "购物", "娱乐", "交通", "收入", "其他"
    };

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
        databaseHelper = new CitrusDBHelper(context, CitrusDBHelper.DB_NAME, null, 1);

        for (int i = 0; i < costTitle.length; i++) {
            CategoryRes res = new CategoryRes();
            res.title = costTitle[i];
            res.resBlack = costIconResBlack[i];
            res.resWhite = costIconRes[i];
            costRes.add(res);
        }


    }

    public static AllSet getInstance() {
        if (instance == null) {
            instance = new AllSet();
        }
        return instance;
    }

    public int getResourceIcon(String category) {
        for (CategoryRes res :
                costRes) {
            if (res.title.equals(category)) {
                return res.resWhite;
            }
        }

        return costRes.get(0).resWhite;
    }
}

