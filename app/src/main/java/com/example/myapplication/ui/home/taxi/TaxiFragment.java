package com.example.myapplication.ui.home.taxi;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class TaxiFragment extends Fragment implements IFireBaseLoadDone {

    @BindView(R.id.citySpinner)
    SearchableSpinner citySpinner;

    private DatabaseReference taxiRef;
    IFireBaseLoadDone iFireBaseLoadDone;
    List<Taxi> taxis;

    boolean isFirstTimeClicked = true;

    @BindView(R.id.setLocation)
    TextView locationtxt;
    TextView taxiName, taxiNumber;
    BottomSheetDialog bottomSheetDialog;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    public TaxiFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static TaxiFragment newInstance(String param1, String param2) {
        TaxiFragment fragment = new TaxiFragment();
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
        View view = inflater.inflate(R.layout.fragment_taxi, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }


    @SuppressLint("SetTextI18n")
    private void initView() {

        //initialize database
        taxiRef = FirebaseDatabase.getInstance().getReference("taxi");

        //init interface
        iFireBaseLoadDone = this;

        //get Data
        taxiRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("Debug", "OnDataChange");

                List<Taxi> taxis = new ArrayList<>();
                List<String> cities = new ArrayList<>();

                for (DataSnapshot taxiSnapShot : snapshot.getChildren()) {
                    Taxi taxi = new Taxi("", "");
                    taxi.setName(taxiSnapShot.getChildren().toString());
                    taxi.setNumber(taxiSnapShot.getChildren().toString());
                    taxis.add(taxi);
                    cities.add(taxiSnapShot.getKey());
                }
                iFireBaseLoadDone.onFirebaseLoadSuccess(taxis, cities);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("Debug", "Error");
                iFireBaseLoadDone.onFirebaseLoadFailed(error.getMessage());
            }
        });

    }

    @Override
    public void onFirebaseLoadSuccess(List<Taxi> taxiList, List<String> cityList) {
        Log.d("Debug", "Success");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireActivity().getBaseContext(), android.R.layout.simple_spinner_item, cityList);
        citySpinner.setAdapter(adapter);

        citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // it shows the taxis only if it's not the first click
                if (!isFirstTimeClicked) {
                    Log.d("Debug", "Not first");
                } else {
                    Log.d("Debug", "First");
                    isFirstTimeClicked = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onFirebaseLoadFailed(String message) {
        Log.d("Debug", "Database failed");
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }
}