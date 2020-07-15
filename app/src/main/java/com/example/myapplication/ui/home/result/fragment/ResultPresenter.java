package com.example.myapplication.ui.home.result.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Objects;

public class ResultPresenter extends ResultContract.Presenter {

    private FirebaseAuth fAuth;
    private FirebaseFirestore fStore;
    private String userID;
    private String gender;
    private String weight;
    private double A;
    private DatabaseReference reference;
    private double res;
    private  ArrayList<Date> date;

    public ResultPresenter(ResultContract.View view) {
        super(view);
    }

    public void getPersonalInformation() {
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userID = fAuth.getCurrentUser().getUid();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("users").document(userID);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    assert document != null;
                    if (document.exists()) {
                        gender = document.get("gender").toString();
                        weight = Objects.requireNonNull(document.get("weight")).toString();
                        getDrinks();
                    } else {
                        Log.d("Debug", "No such document");
                    }
                } else {
                    Log.d("Debug", "get failed with ", task.getException());
                }
            }
        });

    }

    public void getDrinks() {
        date = new ArrayList<Date>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("drinks").child(userID);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                A = 0;
                for (DataSnapshot s : snapshot.getChildren()) {
                    double drink = Double.parseDouble(s.child("quantity").getValue().toString());
                    drink *= 0.033814; // converting mL to Unica
                    double degree = Double.parseDouble(s.child("degree").getValue().toString());
                    drink *= (degree / 100);
                    A += drink;

                    String textHour = s.child("hour").getValue().toString();
                    String textDate = s.child("date").getValue().toString();
                    Date date1 = null;
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm");
                    try {
                        date1 = formatter.parse(textDate.concat(" ").concat(textHour));
                    } catch (Exception e) {

                    }
                    date.add(date1);
                }

                Collections.sort(date);
                Date elsoDatum;
                if(date.size() > 0){
                    elsoDatum = date.get(0);
                } else {
                    view.showResult(0);
                    return;
                }

                Date currentDate = new Date(System.currentTimeMillis());
                long secs = (currentDate.getTime() - elsoDatum.getTime()) / 1000;
                long hours = (secs / 3600);

                double w = Double.parseDouble(weight) * 2.2; // converting kg to pounds
                double r;
                // initialize gender constant
                if (gender.equals("Male")) {
                    r = 0.73;
                } else {
                    r = 0.66;
                }
                res = (A * 5.14) / (w * r);
                res -= 0.015 * hours;
                if (view != null){
                    view.showResult(res);
                    view.initializeSeekBar(elsoDatum,hours,res);
                    removeDrinks();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public void removeDrinks() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("drinks").child(userID);
        reference.removeValue();
    }


}
