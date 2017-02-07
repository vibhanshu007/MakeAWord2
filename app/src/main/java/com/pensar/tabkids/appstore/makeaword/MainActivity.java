package com.pensar.tabkids.appstore.makeaword;

import android.graphics.Point;
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

    TextView textView;
    private boolean isTouch;
    DragAndDrop dragAndDrop;
    MainActivity mainActivity;
    int windowWidth,windowHeight;
    ArrayList<String> threeLetterName;
    ArrayList<String> fourLetterName;
    ArrayList<String> questionList;
    ArrayList<String> questionListWithoutPng;
    ArrayList<String> optionList;
    ImageView questionImage;
    int counter=0;
    int teacherOption;
    HashMap<Integer,TextView> map;
    private static final int TOTAL_OPTIONS=8;
    int background[] ={R.drawable.one,R.drawable.two,R.drawable.three,R.drawable.four,R.drawable
            .five,R.drawable.six,R.drawable.seven,R.drawable.eight,R.drawable.nine,R.drawable
            .ten};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dragAndDrop = new DragAndDrop(mainActivity);
        map=new HashMap<Integer, TextView>();
        questionList = new ArrayList<String>();
        optionList = new ArrayList<>();
        Display display = getWindowManager().getDefaultDisplay();
        Point resolution = new Point();
        display.getSize(resolution);
        windowWidth=resolution.x;
        windowHeight=resolution.y;
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

        threeLetterName = CommonUtil.listAssetFiles(this,"3 letters");
        fourLetterName = CommonUtil.listAssetFiles(this,"4 letters");
        Log.e("threeLetterName",""+threeLetterName);
        Log.e("fourLetterName",""+fourLetterName);
        Log.e("getQuestionList",""+getQuestionList());




        String question=questionList.get(counter);
        Log.e("teacherOption",""+teacherOption);
        textView = (TextView) findViewById(R.id.textAns4);
        String folderName=null;
        if (question.length()==4){
            folderName="4 letters";
            textView.setVisibility(View.VISIBLE);
        }else{
            folderName="3 letters";
            textView.setVisibility(View.GONE);
        }
        questionImage =(ImageView)findViewById(R.id.questionImage);
        questionImage.setImageBitmap(CommonUtil.getDataFromAsserts(this,folderName+"/"+question));
        String temp=question.split("\\.")[0];
        teacherOption=temp.length();
        setOption(temp);
        setMapValues();

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
            int k=(i+1)%10;
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
        Log.e("doComplete"   ,"doComplete");

    }
}
