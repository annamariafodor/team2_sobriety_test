package com.example.myapplication;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.solver.widgets.Snapshot;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentSnapshot;

import java.lang.annotation.Documented;

public class MyHolder extends RecyclerView.ViewHolder {



    TextView quantity,degree,date,time,mQuantity,mDegree,mDate,mTime;
    ImageButton delete,edit;


    public MyHolder(@NonNull View itemView) {
        super(itemView);

        this.mQuantity=itemView.findViewById(R.id.textQuantity);
        this.mDegree=itemView.findViewById(R.id.textDegree);
        this.mDate=itemView.findViewById(R.id.textDate);
        this.mTime=itemView.findViewById(R.id.textTime);

        this.quantity=itemView.findViewById(R.id.inputQuantity);
        this.degree=itemView.findViewById(R.id.inputDegree);
        this.date=itemView.findViewById(R.id.inputDate);
        this.time=itemView.findViewById(R.id.inputTime);
        this.delete=itemView.findViewById(R.id.deleteButton);
        this.edit=itemView.findViewById(R.id.editButton);


    }


}
