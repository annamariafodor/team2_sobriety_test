package com.example.myapplication.ui.home.taxi;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.ui.home.MainActivity;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class TaxiFragment extends Fragment implements IFireBaseLoadDone {

    @BindView(R.id.taxiList)
    ListView taxiListItem;

    @BindView(R.id.citySpinner)
    SearchableSpinner citySpinner;

    private DatabaseReference taxiRef;
    IFireBaseLoadDone iFireBaseLoadDone;
    List<Taxi> taxis;

    boolean isFirstTimeClicked = true;

    @BindView(R.id.setLocation)
    TextView locationtxt;
    TextView taxiName, taxiNumber;
    private static final int REQUEST_CALL = 1;

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

                List<City> cities = new ArrayList<>();

                for (DataSnapshot citySnapShot : snapshot.getChildren()) {
                    City city = new City(citySnapShot.getKey());
                    Log.d("Debug", "Cityname; " + city.getName());
                    for (DataSnapshot snapShot : citySnapShot.getChildren()) {
                        Taxi taxi = new Taxi("", "");
                        taxi.setName(snapShot.child("name").getValue(String.class));
                        taxi.setNumber(snapShot.child("number").getValue(String.class));
                        city.addTaxi(taxi);
                    }
                    cities.add(city);
                }
                iFireBaseLoadDone.onFirebaseLoadSuccess(cities);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("Debug", "Error");
                iFireBaseLoadDone.onFirebaseLoadFailed(error.getMessage());
            }
        });

    }


    @Override
    public void onFirebaseLoadSuccess(List<City> cities) {
        Log.d("Debug", "Success");
        List<String> cityList = new ArrayList<>();
        for (City city : cities) {
            cityList.add(city.getName());
        }

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(requireActivity().getBaseContext(), android.R.layout.simple_spinner_item, cityList);
        citySpinner.setAdapter(spinnerAdapter);

        citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ArrayAdapter<Taxi> taxiAdapter;

                List<Taxi> taxiList = new ArrayList<>();
                // it shows the taxis only if it's not the first click
                if (!isFirstTimeClicked) {
                    String city = citySpinner.getSelectedItem().toString();
                    for (City c : cities) {
                        if (city.equals(c.getName())) {
                            taxiList = c.getTaxiList();
                            taxiAdapter = new ArrayAdapter<Taxi>(requireActivity().getBaseContext(), android.R.layout.simple_list_item_1, taxiList);
                            taxiListItem.setAdapter(taxiAdapter);

                            //Handling Click Events in ListView
                            List<Taxi> finalTaxiList = taxiList;
                            taxiListItem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                                    Taxi taxi = finalTaxiList.get(i);
                                    String number = taxi.getNumber();

                                    //TODO: IT'S ONLY WORKING IN ROMANIA
                                    String countryCode = "tel:+40";
                                    //I need to take the second 0 from the number
                                    number = number.substring(1);
                                    number = countryCode.concat(number);

                                    //CHECKING FOR PERMISSIONS
                                    if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
                                    } else {
                                        Intent intent = new Intent(Intent.ACTION_CALL);
                                        intent.setData(Uri.parse(number));
                                        startActivity(intent);
                                    }
                                }
                            });
                        }
                    }
                } else {
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