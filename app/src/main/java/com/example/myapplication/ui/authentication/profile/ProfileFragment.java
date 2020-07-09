package com.example.myapplication.ui.authentication.profile;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.myapplication.R;
import com.example.myapplication.ui.home.MainActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class ProfileFragment extends Fragment {

    @BindView(R.id.heightInput)
    TextInputLayout heightInp;

    @BindView(R.id.spinner)
    Spinner genderInp;

    @BindView(R.id.ageInput)
    TextInputLayout ageInp;

    @BindView(R.id.weightInput)
    TextInputLayout weightInp;

    @BindView(R.id.imageButton)
    ImageButton toMainScreen;

    String gender;
    Float height, weight;
    Integer age;

    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, view);
        return view;
    }




    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final NavController navController = Navigation.findNavController(view);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        toMainScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                gender = genderInp.getSelectedItem().toString();
                height = Float.parseFloat(heightInp.getEditText().getText().toString());
                weight = Float.parseFloat(weightInp.getEditText().getText().toString());
                age = Integer.parseInt(ageInp.getEditText().getText().toString());

                if(!isValidForm(age, weight, height, weightInp, heightInp)) {
                    return;
                }

                // get The current user;
                userID = fAuth.getCurrentUser().getUid();

                //create new document
                DocumentReference documentReference = fStore.collection("users").document(userID);
                Map<String, Object> user = new HashMap<>();
                user.put("gender", gender);
                user.put("age", age);
                user.put("weight", weight);
                user.put("height", height);

                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("Profile", "onSuccess: User profile is created" + userID);
                        Intent intent = new Intent(getContext(), MainActivity.class);
                        startActivity(intent);
                    }
                });
            }
        });
    }

    public boolean isValidForm(Integer age, Float weight, Float height, TextInputLayout weightInp, TextInputLayout heightInp) {

        if (ageInp.getEditText().getText().toString().trim().length() == 0) {
            ageInp.setError("Age is Required");
            return false;
        }

        if (heightInp.getEditText().getText().toString().trim().length() == 0) {
            heightInp.setError("Height is Required");
            return false;
        }

        if (weightInp.getEditText().getText().toString().trim().length() == 0) {
            weightInp.setError("Weight is Required");
            return false;
        }


        if (weight < 10 || weight > 600 ){
            weightInp.setError("Weight is not valid");
            return false;
        }

        if (height < 10 || height > 280) {
            heightInp.setError("Height is not valid");
            return false;
        }

        if (age < 1 || age > 130) {
            ageInp.setError("Age is not valid");
            return false;
        }
        return true;
    }
}