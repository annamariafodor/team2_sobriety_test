package com.example.myapplication.ui.home.taxi;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        Log.d("Debug", "OnCreateView");
        initView();
        return view;
    }


    @SuppressLint("SetTextI18n")
    private void initView() {

        bottomSheetDialog = new BottomSheetDialog(requireActivity().getBaseContext());
        //View bottom_sheet_dialog = getLayoutInflater().inflate(R.layout.fragment_taxi_list,null);

        //initialize database
        taxiRef = FirebaseDatabase.getInstance().getReference("taxi");

        Log.d("Debug", "Taxiref" + taxiRef.toString());

        //init interface
        iFireBaseLoadDone = this;

        //get Data
        taxiRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Taxi> taxis = new ArrayList<>();
                List<String> cities = new ArrayList<>();

                Log.d("Debug", "OnDataChange");

                for (DataSnapshot taxiSnapShot : snapshot.getChildren()) {
                    Taxi taxi = new Taxi("", "");
                    //Log.d("Debug", "here");
                    Log.d("Debug", taxiSnapShot.toString());
                    taxi.setName(taxiSnapShot.getChildren().toString());
                    taxi.setNumber(taxiSnapShot.getChildren().toString());
                    Log.d("Debug", taxi.toString1());

                    taxis.add(taxi);
                    cities.add(taxiSnapShot.getKey());

                }
                iFireBaseLoadDone.onFirebaseLoadSuccess(taxis, cities);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("Debug", "Error");
                iFireBaseLoadDone.onFirebaseLoadFailed("valami");
            }
        });
    }

    @Override
    public void onFirebaseLoadSuccess(List<Taxi> taxiList, List<String> cityList) {
        Log.d("Debug", "Success");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireActivity().getBaseContext(), android.R.layout.simple_spinner_item, cityList);
        citySpinner.setAdapter(adapter);
    }

    @Override
    public void onFirebaseLoadFailed(String message) {
        Toast.makeText(getActivity(), "Naa", Toast.LENGTH_SHORT).show();
    }
}