package com.pensar.tabkids.appstore.makeaword;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.CharacterPickerDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CommonUtil.hideSystemUI(getWindow().getDecorView());
        int background[] ={R.drawable.one,R.drawable.two,R.drawable.three,R.drawable.four,R.drawable
                .five,R.drawable.six,R.drawable.seven,R.drawable.eight,R.drawable.nine,R.drawable
                .ten};


        for (int i =0;i<26;i++){
            char st = (char)(65+i);
            String textId = "text"+st;
            int textID = getResources().getIdentifier(textId,"id",getPackageName());
            textView = (TextView)findViewById(textID);
            textView.setHeight(50);
            textView.setWidth(50);
            textView.setText(String.valueOf(st));
            textView.setTextColor(getResources().getColor(R.color.wight));
            int k=(i+1)%10;
            textView.setBackground(getResources().getDrawable(R.drawable.test));
            textView.setBackgroundResource(background[k]);
            textView.setGravity(Gravity.CENTER);

        }

    }
}
