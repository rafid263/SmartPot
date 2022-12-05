package com.s_rafid.smartpot.Controller;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.s_rafid.smartpot.Model.User;
import com.s_rafid.smartpot.View.ILoginView;
import com.s_rafid.smartpot.View.IRegisterView;

public class RegisterController implements IRegisterController {
    FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
    IRegisterView registerView;
    public RegisterController(IRegisterView registerView) {
        this.registerView = registerView;
    }
    @Override
    public void OnRegister(String email, String password) {
        User user = new User(email,password);
        int loginCode = user.isValid();
        Log.d("User","Created");
        if(loginCode == 0)
        {
            registerView.OnRegisterError("Please enter Email");
        }else  if (loginCode == 1){
            registerView.OnRegisterError("Please enter A valid Email");
        } else  if (loginCode == 2)
        {
            registerView.OnRegisterError("Please enter Password");
        }else  if(loginCode == 3){
            registerView.OnRegisterError("Please enter Password greater the 6 char");
        }
        else {
            firebaseAuth.createUserWithEmailAndPassword(email,password)
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            registerView.OnRegisterSuccess("Registration Successful");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener(){
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("Error", String.valueOf(e));
                            registerView.OnRegisterError(e.getLocalizedMessage());
                        }
                    });

        }
    }

}