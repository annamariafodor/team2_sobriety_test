package com.example.myapplication.ui.authentication.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.ui.home.MainActivity;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

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

    private String gender;
    private Float height, weight;
    private Integer age;
    private FirebaseAuth fAuth;
    private FirebaseFirestore fStore;
    private String userID;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        toMainScreen.setOnClickListener(view1 -> {
            try {
                gender = genderInp.getSelectedItem().toString();
                height = Float.parseFloat(heightInp.getEditText().getText().toString());
                weight = Float.parseFloat(weightInp.getEditText().getText().toString());
                age = Integer.parseInt(ageInp.getEditText().getText().toString());

                if (!isValidForm(age, weight, height, weightInp, heightInp)) {
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

                documentReference.set(user).addOnSuccessListener(aVoid -> {
                    Intent intent = new Intent(getContext(), MainActivity.class);
                    startActivity(intent);
                });
            } catch (Exception exp) {
                Toast.makeText(getActivity(), "You must sign in first", Toast.LENGTH_SHORT).show();
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


        if (weight < 10 || weight > 600) {
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