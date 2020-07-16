package com.example.myapplication.ui.home.taxi;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
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
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class TaxiFragment extends Fragment implements IFireBaseLoadDone {

    @BindView(R.id.taxiList)
    ListView taxiListItem;

    @BindView(R.id.citySpinner)
    SearchableSpinner citySpinner;

    @BindView(R.id.setLocation)
    TextView locationtxt;

    private IFireBaseLoadDone iFireBaseLoadDone;
    private static final int REQUEST_CALL = 1;

    public TaxiFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        DatabaseReference taxiRef = FirebaseDatabase.getInstance().getReference("taxi");
        //init interface
        iFireBaseLoadDone = this;
        //get Data
        taxiRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<City> cities = new ArrayList<>();
                for (DataSnapshot citySnapShot : snapshot.getChildren()) {
                    City city = new City(citySnapShot.getKey());
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
                List<Taxi> taxiList;

                String city = citySpinner.getSelectedItem().toString();
                for (City c : cities) {
                    if (city.equals(c.getName())) {
                        taxiList = c.getTaxiList();
                        taxiAdapter = new ArrayAdapter<>(requireActivity().getBaseContext(), android.R.layout.simple_list_item_1, taxiList);
                        taxiListItem.setAdapter(taxiAdapter);

                        //Handling Click Events in ListView
                        List<Taxi> finalTaxiList = taxiList;
                        taxiListItem.setOnItemClickListener((adapterView, view1, i, l) -> {
                            Taxi taxi = finalTaxiList.get(i);
                            String number = taxi.getNumber();
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
                        });
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    @Override
    public void onFirebaseLoadFailed(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

}