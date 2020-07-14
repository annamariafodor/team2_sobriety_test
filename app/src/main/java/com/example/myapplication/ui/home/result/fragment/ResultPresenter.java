package com.example.myapplication.ui.home.result.fragment;

import android.util.Log;

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

    public ResultPresenter(ResultContract.View view) {
        super(view);
    }

    public void getPersonalInformation() {
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userID = fAuth.getCurrentUser().getUid();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("drinks").child(userID);
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        DocumentReference docRef = db.collection("users").document(userID);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    assert document != null;
                    if (document.exists()) {
                        //Log.d("Debug", document.get("weight").toString());
                        Log.d("Debug", "Elso");
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
                }

                Log.d("Debug", "Masodik");
                //Double w = Double.parseDouble(weight) * 2.2; // converting kg to pounds
                Double w = 1.1;
                Double r = null;
                // initialize gender constant
                if (gender.equals("Male")) {
                    r = 0.73;
                } else {
                    r = 0.66;
                }
                res = (A * 5.14) / (w * r);

                if (view != null){
                    view.showResult(res);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }



}
