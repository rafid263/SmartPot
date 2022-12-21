package com.s_rafid.smartpot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.s_rafid.smartpot.Controller.TreeController;
import com.s_rafid.smartpot.View.ITreeView;

public class TreeActivity extends AppCompatActivity implements ITreeView {

    String nameTree,authTree;
    TreeController treeController;
    TextView treeTemp,treeHum,treeMoist,treeWater,treePump,treeStatus;
    String tempTree="-",humTree="-",moistTree="-",waterTree="-",pumpTree="-";
    ImageView treeMood;
    int pumpStatus=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tree);
        Bundle extras = getIntent().getExtras();
        nameTree= extras.getString("name");
        authTree = extras.getString("auth");
        treeController=new TreeController(nameTree,authTree,this);
        TextView treeName = findViewById(R.id.treeNameDash);
        treeTemp = (TextView)findViewById(R.id.tempData);
        treeHum = (TextView)findViewById(R.id.hum);
        treeMoist = (TextView)findViewById(R.id.moist);
        treeWater = (TextView)findViewById(R.id.water);
        treePump = (TextView)findViewById(R.id.pump);
        treeMood = (ImageView)findViewById(R.id.face);
        treeStatus=(TextView)findViewById(R.id.status);
        treeName.setText(nameTree);
        treeTemp.setText(tempTree);
        treePump.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if(pumpStatus==0) {
                    treeController.startPump(1);
                }
                else{
                    treeController.startPump(0);
                }
            }
        });
        content();
        findViewById(R.id.cirDeleteButton).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                treeController.onDelete(authTree);
            }
        });

    }
    public void content(){
        treeTemp.setText(tempTree+"°C");
        treeHum.setText(humTree+"%");
        treeMoist.setText(moistTree+"%");
        treeWater.setText(waterTree);
        Log.e("Pump", String.valueOf(pumpStatus));
        if(pumpStatus==1){
            treePump.setTextColor(Color.GREEN);
            treePump.setText("Pump: ON");
        }
        else{
            treePump.setTextColor(Color.RED);
            treePump.setText("Pump: OFF");
        }
        int mood=0;
        try {
            if(tempTree.length()>1 && humTree.length()>1 && moistTree.length()>1) {
                mood = treeController.getMood(Integer.parseInt(tempTree), Integer.parseInt(humTree), Integer.parseInt(moistTree));
            }
        }
        catch (NumberFormatException e){
            mood =5;
        }
        /*
        0 ok
        1 h temp
        2 l temp
        3 l hum
        4 thirsty
         */
        Log.e("Data", String.valueOf(mood));
        if(mood==0){
            treeMood.setImageResource(getResources().getIdentifier("com.s_rafid.smartpot:drawable/happy", null, null));
            treeStatus.setText("Happy");
        }
        else if(mood==1){
            treeMood.setImageResource(getResources().getIdentifier("com.s_rafid.smartpot:drawable/hot", null, null));
            treeStatus.setText("Hot");
        }
        else if(mood==2){
            treeMood.setImageResource(getResources().getIdentifier("com.s_rafid.smartpot:drawable/cold", null, null));
            treeStatus.setText("Cold");
        }
        else if(mood==3){
            treeMood.setImageResource(getResources().getIdentifier("com.s_rafid.smartpot:drawable/humidity", null, null));
            treeStatus.setText("Humidity Issue");
        }
        else if(mood==4){
            treeMood.setImageResource(getResources().getIdentifier("com.s_rafid.smartpot:drawable/water", null, null));
            treeStatus.setText("Need Water");
        }
        refresh(1000);
    }
    public void refresh(int ms){
        final Handler handler = new Handler();
        final Runnable runnable=new Runnable(){

            @Override
            public void run() {
                treeController.getData(authTree);
                content();
            }
        };
        handler.postDelayed(runnable,ms);
    }

    @Override
    public void OnDeleteSuccess() {
        startActivity(new Intent(TreeActivity.this,DashboardActivity.class));
    }

    @Override
    public void OnUpdate(String[] data) {
        if(data[0].length()>4) {
            tempTree = data[0].substring(2, data[0].length() - 2);
        }
        if(data[1].length()>4) {
            humTree = data[1].substring(2, data[1].length() - 2);
        }
        if(data[2].length()>4) {
            moistTree = data[2].substring(2, data[2].length() - 2);
        }
        if(data[3].length()>4) {
            waterTree = data[3].substring(2, data[3].length() - 2)+"%";
        }
        if(data[4].length()>4) {
            pumpTree = data[4].substring(2, data[4].length() - 2);
            pumpStatus=Integer.parseInt(pumpTree);
        }

    }
}