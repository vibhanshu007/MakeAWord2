package com.pensar.tabkids.appstore.makeaword;

import android.graphics.Point;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.CharacterPickerDialog;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    private boolean isTouch;
    DragAndDrop dragAndDrop;
    MainActivity mainActivity;
    int windowWidth,windowHeight;
    ArrayList<String> threeLetterName;
    ArrayList<String> fourLetterName;
    ArrayList<String> questionList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dragAndDrop = new DragAndDrop(mainActivity);
        questionList = new ArrayList<String>();
        Display display = getWindowManager().getDefaultDisplay();
        Point resolution = new Point();
        display.getSize(resolution);
        windowWidth=resolution.x;
        windowHeight=resolution.y;
        CommonUtil.hideSystemUI(getWindow().getDecorView());
        int background[] ={R.drawable.one,R.drawable.two,R.drawable.three,R.drawable.four,R.drawable
                .five,R.drawable.six,R.drawable.seven,R.drawable.eight,R.drawable.nine,R.drawable
                .ten};


        for (int i =0;i<26;i++){
            char st = (char)(65+i);
            String textId = "text"+st;
            final int textID = getResources().getIdentifier(textId,"id",getPackageName());
            textView = (TextView)findViewById(textID);
            textView.setHeight(50);
            textView.setWidth(50);
            textView.setText(String.valueOf(st));
            textView.setTextColor(getResources().getColor(R.color.wight));
            int k=(i+1)%10;
            textView.setBackgroundResource(background[k]);
            textView.setGravity(Gravity.CENTER);
            textView.setOnTouchListener(new DragAndDrop(this));
        }
        threeLetterName = CommonUtil.listAssetFiles(this,"3 letters");
        fourLetterName = CommonUtil.listAssetFiles(this,"4 letters");
        Log.e("threeLetterName",""+threeLetterName);
        Log.e("fourLetterName",""+fourLetterName);
        Log.e("getQuestionList",""+getQuestionList());


    }
    public boolean isTouch() {
        return isTouch;
    }

    public void setTouch(boolean touch) {
        isTouch = touch;
    }
    public ArrayList<String> getQuestionList(){
        Collections.shuffle(threeLetterName);
        Collections.shuffle(fourLetterName);
        for (int i =0 ; i<=4;i++){
            questionList.add(threeLetterName.get(i));
        }
        for (int j =0 ; j<=4;j++){
            questionList.add(fourLetterName.get(j));
        }
        return questionList ;
    }

}
