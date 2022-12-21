package com.s_rafid.smartpot;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.s_rafid.smartpot.Controller.ILoginController;
import com.s_rafid.smartpot.Controller.IRegisterController;
import com.s_rafid.smartpot.Controller.LoginController;
import com.s_rafid.smartpot.Controller.RegisterController;
import com.s_rafid.smartpot.View.ILoginView;
import com.s_rafid.smartpot.View.IRegisterView;

public class RegisterActivity extends AppCompatActivity implements IRegisterView {
    EditText email,password;
    IRegisterController registerPresenter;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        email = (EditText) findViewById(R.id.editTextEmail);
        password = (EditText)findViewById(R.id.editTextPassword);
        registerPresenter = new RegisterController(this);
        progressDialog = new ProgressDialog(this);
        findViewById(R.id.tv_login).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
            }
        });
        findViewById(R.id.cirSignupButton).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Log.d("In signup","Signup btn");
                progressDialog.show();
                Log.d(email.getText().toString().trim(),password.getText().toString().trim());
                registerPresenter.OnRegister(email.getText().toString().trim(),password.getText().toString().trim());
                progressDialog.cancel();
            }
        });
    }

    @Override
    public void OnRegisterSuccess(String message) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
        startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
    }

    @Override
    public void OnRegisterError(String message) {
        Toast.makeText(this,message, Toast.LENGTH_LONG).show();
    }
}