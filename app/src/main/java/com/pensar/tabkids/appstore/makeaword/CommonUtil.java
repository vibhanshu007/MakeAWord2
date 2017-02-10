package com.pensar.tabkids.appstore.makeaword;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.view.View;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Locale;


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

    public static ArrayList<String> spliteString(String imageName){
        ArrayList<String> optionList = new ArrayList<String>();
        char option[]=imageName.toCharArray();
        for (int i = 0;i < option.length; i++){
            optionList.add(String.valueOf(option[i]).toUpperCase());

        }
        return optionList;
    }

    public static boolean isPointInsideView(float x, float y, View view){
        int location[] = new int[2];
        view.getLocationOnScreen(location);
        int viewX = location[0];
        int viewY = location[1];
        //Log.e("", "viewX = "+viewX+"viewY = "+viewY);
        //point is inside view bounds
        return (x > viewX - view.getWidth() / 2 && x < (viewX + view.getWidth())) &&
                (y > viewY - view.getHeight() / 2 && y < (viewY + view.getHeight()));
    }

    public static int id(Context context,int position,String commonText,String type){
        int id=context.getResources().getIdentifier(commonText+""+position,type,context.getPackageName());
        return id;
    }
    public static void startSound(Context context, final TextToSpeech toSpeech, final String text){

        //textToSpeech.setPitch(1.5f);
        toSpeech.setSpeechRate(0.5f);
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                toSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);
            }
        },context.getResources().getInteger(R.integer.speech_delay_time));
    }
}
