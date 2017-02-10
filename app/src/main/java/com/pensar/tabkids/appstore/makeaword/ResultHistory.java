package com.pensar.tabkids.appstore.makeaword;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.util.Property;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 9/2/17.
 */

public class ResultHistory extends ArrayAdapter{
    Context context;
    Button backButton;
    List<ArrayList<String>> list;
    public ResultHistory(Context context, int resource, List objects) {
        super(context, resource, objects);
        this.context=context;
        this.list=objects;
    }

        @Override
        public View getView ( int position, View convertView, ViewGroup parent){

            LayoutInflater inflater = (LayoutInflater) context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View rowView = inflater.inflate(R.layout.history, null);
            Log.e("convert", "" + rowView.findViewById(R.id.history_image));
            ImageView imageView = (ImageView) rowView.findViewById(R.id.history_image);
            ArrayList<String> tempList = list.get(position);
            String imageName = list.get(position).get(0);
            String folderName = null;

            if (imageName.length() == 4) {
                folderName = MainActivity.LETTER_FOUR;
            } else {
                folderName = MainActivity.LETTER_THREE;
            }
            imageView.setImageBitmap(CommonUtil.getDataFromAsserts(context, folderName
                    + "/" + imageName + ".png"));
            ArrayList correctlist = CommonUtil.spliteString(imageName);

            for (int i = 0; i < correctlist.size(); i++) {
                int id = context.getResources().getIdentifier("historyOption"
                        + i, "id", context.getPackageName());
                Log.e("id", "" + id);
                TextView historyOption = (TextView) rowView.findViewById(id);
                String userAnswer = tempList.get(i + 1);
                historyOption.setText(userAnswer);

                if (correctlist.get(i).equals(userAnswer)) {
                    historyOption.setBackgroundResource(R.drawable.five);
                } else {
                    historyOption.setBackgroundResource(R.drawable.four);
                }
            }
            return rowView;
        }
    }
