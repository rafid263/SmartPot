package com.s_rafid.smartpot.Controller;

import android.content.Intent;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.s_rafid.smartpot.LoginActivity;
import com.s_rafid.smartpot.Model.User;
import com.s_rafid.smartpot.R;
import com.s_rafid.smartpot.RegisterActivity;
import com.s_rafid.smartpot.View.ILoginView;
public class LoginController implements ILoginController {
    ILoginView loginView;
    FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
    public LoginController(ILoginView loginView) {
        this.loginView = loginView;
    }
    @Override
    public boolean loginStatus(){
        return firebaseAuth.getCurrentUser()!=null;
    }

    @Override
    public void OnReset(String email) {
        User user = new User(email,"resetPass");
        int loginCode = user.isValid();
        if(loginCode == 0)
        {
            loginView.OnLoginError("Please enter Email");
        }else  if (loginCode == 1){
            loginView.OnLoginError("Please enter A valid Email");
        }
        else {
            firebaseAuth.sendPasswordResetEmail(email)
                    .addOnCompleteListener(new OnCompleteListener<Void>(){

                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                loginView.OnLoginError("Reset mail sent to: "+email);
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener(){
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("Error", String.valueOf(e));
                            loginView.OnLoginError(e.getLocalizedMessage());
                        }
                    });
        }
    }

    @Override
    public void OnLogin(String email, String password) {
        User user = new User(email,password);
        int loginCode = user.isValid();
        if(loginCode == 0)
        {
            loginView.OnLoginError("Please enter Email");
        }else  if (loginCode == 1){
            loginView.OnLoginError("Please enter A valid Email");
        } else  if (loginCode == 2)
        {
            loginView.OnLoginError("Please enter Password");
        }else  if(loginCode == 3){
            loginView.OnLoginError("Please enter Password greater the 6 char");
        }
        else {
            firebaseAuth.signInWithEmailAndPassword(email,password)
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            loginView.OnLoginSuccess("Login Successful");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener(){
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("Error", String.valueOf(e));
                            loginView.OnLoginError(e.getLocalizedMessage());
                        }
                    });
        }
    }

}