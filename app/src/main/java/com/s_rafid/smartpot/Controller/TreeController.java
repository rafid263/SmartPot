package com.s_rafid.smartpot.Controller;

import android.app.Activity;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.s_rafid.smartpot.Model.Tree;
import com.s_rafid.smartpot.View.IRegisterView;
import com.s_rafid.smartpot.View.ITreeView;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class TreeController {
    String name,auth;
    ITreeView treeView;
    FirebaseFirestore firebaseFirestore=FirebaseFirestore.getInstance();
    public TreeController(String name,String auth,ITreeView treeView) {
        this.name = name;
        this.auth = auth;
        this.treeView=treeView;
    }

    public void onDelete(String auth){
        firebaseFirestore.collection("Tree")
                .document(auth)
                .delete()
                .addOnCompleteListener(new OnCompleteListener<Void>(){

                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                       treeView.OnDeleteSuccess();
                    }
                });
    }


    public void getData(String auth){
        String[] data=new String[5];
        OkHttpClient client = new OkHttpClient();
        Executor executor= Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {

            @Override
            public void run() {
                for(int i=0;i<5;i++) {
                    Request request = new Request.Builder()
                            .url("http://103.198.136.121:8080/" + auth + "/get/V"+i)
                            .build();

                    Response response = null;
                    try {
                        response = client.newCall(request).execute();
                        //treeTemp.setText(response.body().string());
                        //Log.d("Value temp:",response.body().string());
                        //treeView.OnUpdate(response.body().string());
                        data[i]=response.body().string();
                    } catch (IOException e) {
                        e.printStackTrace();
                        data[i]="-";
                    }
                }
                treeView.OnUpdate(data);
            }
        });
    }
    public void startPump(int i){
        OkHttpClient client = new OkHttpClient();
        Executor executor= Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {

                    Request request = new Request.Builder()
                            .url("http://103.198.136.121:8080/" + auth + "/update/V4?value="+i)
                            .build();

                    Response response = null;
                    try {
                        response = client.newCall(request).execute();
                        //treeTemp.setText(response.body().string());
                        //Log.d("Value temp:",response.body().string());
                        //treeView.OnUpdate(response.body().string());

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            }
        });
    }
    public int getMood(int t,int h,int m){
        Tree t1=new Tree(this.name,this.auth);
        Log.e("Data",""+t+"+++"+h);
        return t1.getMood(t,h,m);
    }
}
