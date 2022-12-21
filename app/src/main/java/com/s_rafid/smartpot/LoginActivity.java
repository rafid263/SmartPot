package com.s_rafid.smartpot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.s_rafid.smartpot.Controller.ILoginController;
import com.s_rafid.smartpot.Controller.LoginController;
import com.s_rafid.smartpot.View.ILoginView;

public class LoginActivity extends AppCompatActivity implements ILoginView {
    EditText email,password;
    ILoginController loginPresenter=new LoginController(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(loginPresenter.loginStatus()){
            startActivity(new Intent(LoginActivity.this,DashboardActivity.class));
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = (EditText) findViewById(R.id.editTextEmail);
        password = (EditText)findViewById(R.id.editTextPassword);
        findViewById(R.id.tv_register).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });
        findViewById(R.id.cirLoginButton).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                loginPresenter.OnLogin(email.getText().toString().trim(),password.getText().toString().trim());
            }
        });
        findViewById(R.id.resetPass).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                loginPresenter.OnReset(email.getText().toString().trim());
            }
        });
    }
    public void OnLoginSuccess(String message) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
        startActivity(new Intent(LoginActivity.this,DashboardActivity.class));
    }
    @Override
    public void OnLoginError(String message) {
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }
}