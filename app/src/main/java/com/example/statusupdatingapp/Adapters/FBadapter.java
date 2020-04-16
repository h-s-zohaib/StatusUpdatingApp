package com.example.statusupdatingapp.Adapters;

import android.icu.text.Transliterator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.statusupdatingapp.ModelClasses.ModelClass;
import com.example.statusupdatingapp.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class FBadapter extends FirestoreRecyclerAdapter<ModelClass,FBadapter.fbViewHolder > {


    public FBadapter(@NonNull FirestoreRecyclerOptions<ModelClass> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull fbViewHolder fbViewHolder, int i, @NonNull ModelClass modelClass) {
        String UserName=getSnapshots().getSnapshot(i).getId();
        fbViewHolder.usernametv.setText(UserName);
        fbViewHolder.userstatustv.setText(modelClass.getStatus());
    }

    @NonNull
    @Override
    public fbViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new fbViewHolder(LayoutInflater.from(parent.getContext())
        .inflate(R.layout.single_row, parent , false));
    }

    public class fbViewHolder extends RecyclerView.ViewHolder{

        TextView usernametv,userstatustv;
        public fbViewHolder(@NonNull View singleRow) {
            super(singleRow);
            usernametv= singleRow.findViewById(R.id.usernametv);
            userstatustv = singleRow.findViewById(R.id.userstatustv);
        }
    }
}
