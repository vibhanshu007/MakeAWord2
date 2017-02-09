package com.pensar.tabkids.appstore.makeaword;

import android.graphics.Point;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private boolean isTouch;
    MainActivity mainActivity;
    int windowWidth,windowHeight;
    ArrayList<String> questionList;
    DragAndDrop dragAndDrop;
    int counter=8,teacherOption;
    ArrayList<ArrayList<String>> resultList;
    String temp;
    HashMap<Integer,TextView> map;
    private static final int TOTAL_OPTIONS=8;
    private static final int completeSheetCounter=10;
    public static final String LETTER_THREE="3 letters";
    public static final String LETTER_FOUR="4 letters";
    int background[] ={R.drawable.one,R.drawable.two,R.drawable.three,
            R.drawable.six,R.drawable.seven,R.drawable.eight,R.drawable.nine,R.drawable.ten};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dragAndDrop = new DragAndDrop(mainActivity);
        map=new HashMap<Integer, TextView>();
        resultList = new ArrayList<ArrayList<String>>();
        questionList = new ArrayList<String>();
        getResolution();
        CommonUtil.hideSystemUI(getWindow().getDecorView());
        getQuestionList();
        startApp();
    }

    public void startApp(){
        String question=questionList.get(counter);
        TextView textView = (TextView) findViewById(R.id.textAns3);
        String folderName=null;
        if (question.length()==8){
            folderName=LETTER_FOUR;
            textView.setVisibility(View.VISIBLE);
        }else{
            folderName=LETTER_THREE;
            textView.setVisibility(View.GONE);
        }
        ImageView questionImage =(ImageView)findViewById(R.id.questionImage);
        questionImage.setImageBitmap(CommonUtil.getDataFromAsserts(this,folderName+"/"+question));
        temp=question.split("\\.")[0];
        teacherOption=temp.length();
        for (int i =0;i<teacherOption; i++){
            TextView placeHolderView =(TextView)findViewById(getResources().getIdentifier
                    ("textAns"+i, "id",getPackageName()));
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
        ArrayList<String> threeLetterName= CommonUtil.listAssetFiles(this,LETTER_THREE);
        ArrayList<String> fourLetterName=CommonUtil.listAssetFiles(this,LETTER_FOUR);
        Collections.shuffle(threeLetterName);
        Collections.shuffle(fourLetterName);
        for (int i =0 ; i<=4;i++){
            questionList.add(threeLetterName.get(i));
        }
        for (int j =0 ; j<=4;j++){
            questionList.add(fourLetterName.get(j));
        }
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
        ArrayList<String> tempList=new ArrayList<String>();
        tempList.add(temp);
        for (int i = 0; i < correctlist.size(); i++) {
            String userAnswer=map.get(i).getText().toString();
            tempList.add(userAnswer);
            if (correctlist.get(i).equals(userAnswer)){
                map.get(i).setBackgroundResource(R.drawable.five);
                Log.e("map","map"+userAnswer);
            }else {
                map.get(i).setBackgroundResource(R.drawable.four);
            }
        }

        resultList.add(tempList);
        Handler  handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                counter++;
                Log.e("counter = "+counter,"completeSheetCounter = "+completeSheetCounter);
                if (counter==completeSheetCounter) {
                    showHistory();
                }else{
                    startApp();
                }
            }
        },2000);
    }

    public void showHistory(){
        setContentView(R.layout.result_list);
        ResultHistory resultHistory = new ResultHistory(this,R.layout.history,resultList);
        Log.e("resultHistory","resultHistory"+resultHistory);
        ((ListView)findViewById(R.id.result_list)).setAdapter(resultHistory);
    }
}
