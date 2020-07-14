package com.example.myapplication.ui.home.result;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Model;
import com.example.myapplication.MyAdapter;
import com.example.myapplication.R;
import com.example.myapplication.ui.home.listScreen.EditDataDialog;
import com.example.myapplication.ui.home.listScreen.ListScreenFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ResultFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ResultFragment extends Fragment {


    @BindView(R.id.resultText)
    TextView resultText;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    DatabaseReference reference;
    String userID;
    String gender;
    String weight;
    Double A;



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ResultFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ResultFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ResultFragment newInstance(String param1, String param2) {
        ResultFragment fragment = new ResultFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_result, container, false);
        final NavController navController = Navigation.findNavController(view);
        ButterKnife.bind(this, view);
        if (calculateResult()){
            navController.navigate(R.id.popupFragment);
        }
        return view;
    }

    Double res;

    private boolean calculateResult() {

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
                        Log.d("Debug", document.get("weight").toString());
                        gender = document.get("gender").toString();
                        weight = Objects.requireNonNull(document.get("weight")).toString();
                    } else {
                        Log.d("Debug", "No such document");
                    }
                } else {
                    Log.d("Debug", "get failed with ", task.getException());
                }
            }
        });


        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                A = (double) 0;
                for (DataSnapshot s : snapshot.getChildren()) {
                    double drink = Double.parseDouble(s.child("quantity").getValue().toString());
                    drink *= 0.033814; // converting mL to Unica
                    double degree = Double.parseDouble(s.child("degree").getValue().toString());
                    drink *= (degree/100);
                    A += drink;
                }

                Double w = Double.parseDouble(weight) * 2.2; // converting kg to pounds

                Double r = null;
                // initialize gender constant
                if (gender.equals("Male")) {
                    r = 0.73;
                } else {
                    r = 0.66;
                }
                res = (A * 5.14) / (w * r);


                NumberFormat formatter = new DecimalFormat("#0.000");
                resultText.setText(formatter.format(res)+" %");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        if ( res > 0 ){
            return true;
        }else{
            return false;
        }

    }


}