package com.pensar.tabkids.appstore.makeaword;

import android.graphics.Point;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {


    private boolean isTouch;
    MainActivity mainActivity;
    int windowWidth,windowHeight;
    ArrayList<String> questionList;
    DragAndDrop dragAndDrop;
    int counter=0,teacherOption;
    String temp;
    HashMap<Integer,TextView> map;
    private static final int TOTAL_OPTIONS=8;
    int background[] ={R.drawable.one,R.drawable.two,R.drawable.three,
            R.drawable.six,R.drawable.seven,R.drawable.eight,R.drawable.nine,R.drawable.ten};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dragAndDrop = new DragAndDrop(mainActivity);
        map=new HashMap<Integer, TextView>();
        questionList = new ArrayList<String>();
        getResolution();
        CommonUtil.hideSystemUI(getWindow().getDecorView());


        //For all around text creating..

       /* for (int i =0;i<26;i++){
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
        }*/
        //For button textOption..
        getQuestionList();
        startApp();
    }

    public void startApp(){


        String question=questionList.get(counter);
        TextView textView = (TextView) findViewById(R.id.textAns3);
        String folderName=null;
        if (question.length()==8){
            folderName="4 letters";
            textView.setVisibility(View.VISIBLE);
        }else{
            folderName="3 letters";
            textView.setVisibility(View.GONE);
        }
        ImageView questionImage =(ImageView)findViewById(R.id.questionImage);
        questionImage.setImageBitmap(CommonUtil.getDataFromAsserts(this,folderName+"/"+question));
        temp=question.split("\\.")[0];
        Log.e("temp",""+temp);
        teacherOption=temp.length();
        for (int i =0;i<teacherOption; i++){
            TextView placeHolderView =(TextView)findViewById(getResources().getIdentifier("textAns"+i,"id",getPackageName()));
            placeHolderView.setBackgroundResource(R.drawable.blank);
        }
        setOption(temp);
        setMapValues();
    }
    public void getResolution(){
        Display display = getWindowManager().getDefaultDisplay();
        Point resolution = new Point();
        display.getSize(resolution);
        windowWidth=resolution.x;
        windowHeight=resolution.y;
    }
    public boolean isTouch() {
        return isTouch;
    }

    public void setTouch(boolean touch) {
        isTouch = touch;
    }

    public void getQuestionList(){
        ArrayList<String> threeLetterName= CommonUtil.listAssetFiles(this,"3 letters");
        ArrayList<String> fourLetterName=CommonUtil.listAssetFiles(this,"4 letters");
        Collections.shuffle(threeLetterName);
        Collections.shuffle(fourLetterName);
        for (int i =0 ; i<=4;i++){
            questionList.add(threeLetterName.get(i));
        }
        for (int j =0 ; j<=4;j++){
            questionList.add(fourLetterName.get(j));
        }
    }

    public void checkAnswer(){

    }
    public void setOption(String imageName){
        ArrayList<String> tempList=optionList(imageName);
        Log.e("tempList",""+tempList);
        for (int i=0;i<TOTAL_OPTIONS;i++){
            int id=getResources().getIdentifier("text"+i,"id",getPackageName());
            Log.e("id",""+id);
            TextView option=(TextView)findViewById(id);
            option.setText(tempList.get(i));
            int k=(i+1)%8;
            option.setBackgroundResource(background[k]);
            option.setOnTouchListener(new DragAndDrop(this));
        }
    }
    public ArrayList<String> optionList(String imageName){
        ArrayList<String> tempList=CommonUtil.spliteString(imageName);
        Random random=new Random();
        int total=TOTAL_OPTIONS-tempList.size();
        for (int i=0;i<total;){
            char st = (char)(65+random.nextInt(26));

            String tmpString=String.valueOf(st);

            if (!tempList.contains(tmpString)){
                tempList.add(tmpString);
                i++;
            }
        }
        Collections.shuffle(tempList);
        return tempList;
    }
    public void setMapValues(){
        for (int i=0;i<teacherOption;i++){
            map.put(i,null);
        }
    }

    public void doComplete() {
        Log.e("doComplete", "doComplete");

        ArrayList correctlist=CommonUtil.spliteString(temp);
        Log.e("correctlist","correctlist"+correctlist);
        for (int i = 0; i < correctlist.size(); i++) {
            if (correctlist.get(i).equals(map.get(i).getText().toString())){
                map.get(i).setBackgroundResource(R.drawable.five);
                Log.e("map","map"+map.get(i).getText().toString());
            }else {
                map.get(i).setBackgroundResource(R.drawable.four);
            }
        }
        Handler  handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                counter++;
                startApp();
            }
        },2000);
    }
}
