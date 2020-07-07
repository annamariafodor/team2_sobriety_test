package com.example.myapplication.ui.home.taxi;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.PermissionGroupInfo;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;

import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;


public class TaxiFragment extends Fragment {

//    @BindView(R.id.searchBar)
//    SearchView search;

    @BindView(R.id.setLocation)
    TextView locationtxt;

    Double latitude = 0.0;
    Double longitude = 0.0;

    Location gps_loc = null, network_loc = null, final_loc = null;

    private static final int REQUEST_PERMISSION = 10;

    //it's like hashMap
    private SparseIntArray mErrorString;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TaxiFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TaxiFragment.
     */
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

    public void requestAppPermission(final String[] requestedPermissions, final int StringId, final int requestCode) {
        mErrorString.put(requestCode, StringId);
        int permissionCheck = PackageManager.PERMISSION_GRANTED;
        boolean showRequestPermission = false;
        for (String permission : requestedPermissions) {
            permissionCheck = permissionCheck + ContextCompat.checkSelfPermission(getContext(), permission);
            showRequestPermission = showRequestPermission || ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), permission);
        }

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            if (showRequestPermission) {
                Log.d("msg", "Grant");
            } else {
                ActivityCompat.requestPermissions(getActivity(),requestedPermissions,requestCode);
            }
        }else{
            onPermissionGranted(requestCode);
        }

    }

    private void onPermissionGranted(int requestCode) {
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        int permissionCheck = PackageManager.PERMISSION_GRANTED;
        for(int permission:grantResults){
            permissionCheck = permissionCheck + permission;
        }
        if(grantResults.length > 0  && PackageManager.PERMISSION_GRANTED == permissionCheck){
            onPermissionGranted(requestCode);
        }else{
            Log.d("msg", "enable");
        }
    }

    @SuppressLint("SetTextI18n")
    private void initView() {
        mErrorString = new SparseIntArray();

        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        try {
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                //  Consider calling ActivityCompat#requestPermissions
                Toast.makeText(getContext(), "Not Granted", Toast.LENGTH_SHORT).show();


                return;
            }
            gps_loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            network_loc = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (gps_loc != null) {
            final_loc = gps_loc;
            latitude = final_loc.getLatitude();
            longitude = final_loc.getLongitude();
        } else if (network_loc != null) {
            final_loc = network_loc;
            latitude = final_loc.getLatitude();
            longitude = final_loc.getLongitude();
        } else {
            latitude = 0.0;
            longitude = 0.0;
        }

        try {
            Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses != null && addresses.size() > 0) {
                String city = addresses.get(0).getLocality();
                String country = addresses.get(0).getCountryCode();


                locationtxt.setText("city" + city + "\n\n" +
                        "country:" + country);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}