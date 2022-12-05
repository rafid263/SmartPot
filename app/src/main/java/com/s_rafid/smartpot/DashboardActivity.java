package com.s_rafid.smartpot;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.s_rafid.smartpot.Controller.DashboardController;
import com.s_rafid.smartpot.Controller.IDashboardController;
import com.s_rafid.smartpot.Controller.ILoginController;
import com.s_rafid.smartpot.Controller.LoginController;
import com.s_rafid.smartpot.Model.Tree;
import com.s_rafid.smartpot.View.IDashboardView;

import java.util.ArrayList;
import java.util.List;

public class DashboardActivity extends AppCompatActivity implements IDashboardView {

    IDashboardController dashboardPresenter=new DashboardController(this);
    ArrayList<Tree> trees = new ArrayList<Tree>();
    List<String> list = new ArrayList<>();
    List<String> listAuth = new ArrayList<>();
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_dashboard);
        content();

        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1,list);
        listView.setAdapter(arrayAdapter);
        findViewById(R.id.logout).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                dashboardPresenter.OnLogout();
            }
        });
        findViewById(R.id.cirAddTreeBtn).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                btn_showDialog(v);
            }
        });
    }
    public class MyAdapter extends ArrayAdapter<String> {
        Context context;
        String name[];
        String auth[];
        MyAdapter (Context c,String name[],String auth[]){
            super(c,R.layout.list_item,R.id.treeName,name);
            this.context=c;
            this.name=name;
            this.auth=auth;
        }
        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
            LayoutInflater layoutInflater=(LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View listView=layoutInflater.inflate(R.layout.list_item,null);
            TextView treeName=listView.findViewById(R.id.treeName);
            TextView treeAuth=listView.findViewById(R.id.treeAuth);
            Log.d("Set name:",name[position]);
            treeName.setText(name[position]);
            treeAuth.setText(auth[position]);
            return listView;
        }
    }

    public void content(){
        dashboardPresenter.getTree(list,listAuth);
        listView=findViewById(R.id.listTree);
        String[] nameTree=new String[list.size()];
        String[] authTree=new String[list.size()];
        for(int i=0;i<list.size();i++){
            nameTree[i]=list.get(i);
            authTree[i]=listAuth.get(i);
        }
        //Log.d("Error------------------", String.valueOf(trees.size()));
        MyAdapter adapter = new MyAdapter(this,nameTree,authTree);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getApplicationContext(),position,Toast.LENGTH_LONG).show();
                Intent i = new Intent(DashboardActivity.this, TreeActivity.class);
                i.putExtra("name",nameTree[position]);
                i.putExtra("auth",authTree[position]);
                startActivity(i);
            }
        });


        refresh(1000);
    }
    public void refresh(int ms){
        final Handler handler = new Handler();
        final Runnable runnable=new Runnable(){

            @Override
            public void run() {
                content();
            }
        };
        handler.postDelayed(runnable,ms);
    }
    public void btn_showDialog(View view){
        final AlertDialog.Builder alert=new AlertDialog.Builder(DashboardActivity.this);
        View mView=getLayoutInflater().inflate(R.layout.add_tree,null);
        final EditText treeName=(EditText)mView.findViewById(R.id.nameTree);
        final EditText treeAuth=(EditText)mView.findViewById(R.id.authTree);
        Button addTree=(Button)mView.findViewById(R.id.addTreeBtn);
        alert.setView(mView);
        final AlertDialog alertDialog = alert.create();
        alertDialog.setCanceledOnTouchOutside(true);
        addTree.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                dashboardPresenter.addTree(treeName.getText().toString(),treeAuth.getText().toString());
                alertDialog.dismiss();
               // trees= dashboardPresenter.getTree(list,trees);
            }
        });
        alertDialog.show();
    }
    @Override
    public void OnLogout(String message) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
        startActivity(new Intent(DashboardActivity.this,LoginActivity.class));
    }

    @Override
    public void notify(String message) {
        Toast.makeText(this,message,Toast.LENGTH_LONG).show();
    }
}