package com.example.myapplication.ui.home.taxi;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
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
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;


public class TaxiFragment extends Fragment implements IFireBaseLoadDone {

    @BindView(R.id.citySpinner)
    SearchableSpinner citySpinner;

    private DatabaseReference taxiRef;
    IFireBaseLoadDone iFireBaseLoadDone;
    List <Taxi> taxis;

    @BindView(R.id.setLocation)
    TextView locationtxt;

//    Double latitude = 0.0;
//    Double longitude = 0.0;
//    Location gps_loc = null, network_loc = null, final_loc = null;
//    private static final int REQUEST_PERMISSION = 10;
//    //it's like hashMap
//    private SparseIntArray mErrorString;


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


//        mErrorString = new SparseIntArray();
//        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
//        try {
//            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
//                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(),
//                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//
//                //  Consider calling ActivityCompat#requestPermissions
//                Toast.makeText(getContext(), "Not Granted", Toast.LENGTH_SHORT).show();
//
//
//                return;
//            }
//            gps_loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//            network_loc = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        if (gps_loc != null) {
//            final_loc = gps_loc;
//            latitude = final_loc.getLatitude();
//            longitude = final_loc.getLongitude();
//        } else if (network_loc != null) {
//            final_loc = network_loc;
//            latitude = final_loc.getLatitude();
//            longitude = final_loc.getLongitude();
//        } else {
//            latitude = 0.0;
//            longitude = 0.0;
//        }
//
//        try {
//            Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
//            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
//            if (addresses != null && addresses.size() > 0) {
//                String city = addresses.get(0).getLocality();
//                String country = addresses.get(0).getCountryCode();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }


        //initialize database
        taxiRef = FirebaseDatabase.getInstance().getReference("taxi");
        //init interface
        iFireBaseLoadDone = this;

        //get Data
        taxiRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List <Taxi> taxis = new ArrayList<>();

                for(DataSnapshot taxiSnapShot: snapshot.getChildren()){
                    taxis.add(taxiSnapShot.getValue(Taxi.class));
                }

                iFireBaseLoadDone.onFirebaseLoadSuccess(taxis);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                iFireBaseLoadDone.onFirebaseLoadFailed("valami");
            }
        });

    }

    @Override
    public void onFirebaseLoadSuccess(List<Taxi> taxiList) {

        taxis = taxiList;
        List <String>  name_list = new ArrayList<>();
        for(Taxi taxi :taxiList){
            name_list.add(taxi.getName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireActivity().getBaseContext(),android.R.layout.simple_spinner_item,name_list);
        citySpinner.setAdapter(adapter);
    }

    @Override
    public void onFirebaseLoadFailed(String message) {

    }


//    public void requestAppPermission(final String[] requestedPermissions, final int StringId, final int requestCode) {
//        mErrorString.put(requestCode, StringId);
//        int permissionCheck = PackageManager.PERMISSION_GRANTED;
//        boolean showRequestPermission = false;
//        for (String permission : requestedPermissions) {
//            permissionCheck = permissionCheck + ContextCompat.checkSelfPermission(getContext(), permission);
//            showRequestPermission = showRequestPermission || ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), permission);
//        }
//
//        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
//            if (showRequestPermission) {
//                Log.d("msg", "Grant");
//            } else {
//                ActivityCompat.requestPermissions(getActivity(), requestedPermissions, requestCode);
//            }
//        } else {
//            onPermissionGranted(requestCode);
//        }
//
//    }
//
//    private void onPermissionGranted(int requestCode) {
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        int permissionCheck = PackageManager.PERMISSION_GRANTED;
//        for (int permission : grantResults) {
//            permissionCheck = permissionCheck + permission;
//        }
//        if (grantResults.length > 0 && PackageManager.PERMISSION_GRANTED == permissionCheck) {
//            onPermissionGranted(requestCode);
//        } else {
//            Log.d("msg", "enable");
//        }
//    }


}