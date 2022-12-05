package com.s_rafid.smartpot.Controller;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.s_rafid.smartpot.Model.Tree;
import com.s_rafid.smartpot.Model.TreeModel;
import com.s_rafid.smartpot.View.IDashboardView;
import com.s_rafid.smartpot.View.ILoginView;

import java.util.ArrayList;
import java.util.List;

public class DashboardController implements IDashboardController{
    IDashboardView dashboardView;
    FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
    FirebaseFirestore firebaseFirestore=FirebaseFirestore.getInstance();
    public DashboardController(IDashboardView dashboardView) {
        this.dashboardView = dashboardView;
    }
    @Override
    public void OnLogout() {
        if(firebaseAuth.getCurrentUser()!=null){
            firebaseAuth.signOut();
        }
        dashboardView.OnLogout("Logout successful.");
    }

    @Override
    public boolean addTree(String name, String auth) {
        TreeModel t1=new TreeModel(name,auth,firebaseAuth.getCurrentUser().getEmail());
        //firebaseAuth.getCurrentUser().getEmail()
        if(t1.isValid()==1){
            firebaseFirestore.collection("Tree")
                    .document(t1.getAuth())
                    .set(t1);
            dashboardView.notify("Pot added successfully.");

        }
        else{
            dashboardView.notify("Check name and auth token again.");
        }
        return false;
    }
    @Override
    public ArrayList<Tree> getTree(List<String> list,List<String> listAuth){
        ArrayList<Tree> trees=new ArrayList<Tree>();
        firebaseFirestore.collection("Tree")
                .whereEqualTo("email",firebaseAuth.getCurrentUser().getEmail())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>(){

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(document.getString("name"), document.getString("auth"));
                                Tree temp = new Tree(document.getString("name"), document.getString("auth"));
                                if( list.contains(document.getString("name")) == false) {
                                    list.add(document.getString("name"));
                                    listAuth.add(document.getString("auth"));
                                    trees.add(temp);
                                }
                            }
                        }
                    }

                })
        .addOnFailureListener(new OnFailureListener(){
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("Error", String.valueOf(e));
                dashboardView.notify(e.getLocalizedMessage());
            }
        });
        return (trees);
    }
}
