package com.pensar.tabkids.appstore.makeaword;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by root on 2/2/17.
 */

public class CommonUtil {

    public static void hideSystemUI(View v){
        v.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

    public static Bitmap getDataFromAsserts(Context context, String filePath){
        AssetManager assetManager = context.getAssets();

        Bitmap bitmap =null;
        InputStream inputStream;

        try {
            inputStream = assetManager.open(filePath);
            bitmap = BitmapFactory.decodeStream(inputStream);
        }catch (IOException e){

        }
        return bitmap;
    }

    public static BitmapDrawable getBitmapDrawableFromAsserts(Context context, String filepath){
        return new BitmapDrawable(context.getResources(),getDataFromAsserts(context,filepath));

    }

    public static ArrayList<String> listAssetFiles(Context context, String path) {
        ArrayList<String> nameList=new ArrayList<String>();
        String  list[];
       Resources resources=context.getResources();
        AssetManager assetManager=context.getAssets();
        try {
            list = assetManager.list(path);
            if (list.length > 0) {

                for (String file : list) {
                    nameList.add(file);

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return nameList;
    }
    }
