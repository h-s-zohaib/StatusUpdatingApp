package com.example.statusupdatingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.statusupdatingapp.Adapters.FBadapter;
import com.example.statusupdatingapp.ModelClasses.ModelClass;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    EditText usernameET,userstatusET;
    RecyclerView objRecyclerview;
    FirebaseFirestore objFirebaseFirestore;
    FBadapter objFBadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try{
            usernameET= findViewById(R.id.username);
            userstatusET = findViewById(R.id.userstatus);
            objRecyclerview = findViewById(R.id.RV);
            objFirebaseFirestore = FirebaseFirestore.getInstance();
            addStatusToRv();
        }
        catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

public void addstatus(View view){
        try{
            if(!usernameET.getText().toString().isEmpty()
            && !userstatusET.getText().toString().isEmpty()){
                Map<String , String> objectMap= new HashMap<>();
                objectMap.put("status", userstatusET.getText().toString());
                objFirebaseFirestore.collection("Status")
                        .document(usernameET.getText().toString())
                        .set(objectMap)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(MainActivity.this, "status is added", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(MainActivity.this, "fails to add status", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
            else {
                Toast.makeText(this, "Please Enter Values", Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
}

    private void addStatusToRv(){

        try {
            Query objQuery = objFirebaseFirestore.collection("status");
            FirestoreRecyclerOptions<ModelClass> objOptions
                    =new FirestoreRecyclerOptions.Builder<ModelClass>()
                    .setQuery(objQuery,ModelClass.class)
                    .build();
            objFBadapter=new FBadapter(objOptions);
            objRecyclerview.setAdapter(objFBadapter);
            objRecyclerview.setLayoutManager(new LinearLayoutManager(this));

        }
        catch (Exception e){

            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        }


    @Override
    protected void onStart() {
        super.onStart();
        objFBadapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        objFBadapter.stopListening();
    }
}
