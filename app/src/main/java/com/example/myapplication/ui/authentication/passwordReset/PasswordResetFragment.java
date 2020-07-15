package com.example.myapplication.ui.authentication.passwordReset;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PasswordResetFragment extends Fragment {

    @BindView(R.id.emailInputLayout)
    TextInputLayout emailInput;
    @BindView(R.id.resetButton)
    Button buttonReset;

    private String email;
    private FirebaseAuth fAuth;

    public PasswordResetFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_password_reset, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fAuth = FirebaseAuth.getInstance();
        buttonReset.setOnClickListener(v -> {
            email = Objects.requireNonNull(emailInput.getEditText()).getText().toString();
            fAuth.sendPasswordResetEmail(email).addOnSuccessListener(aVoid -> Toast.makeText(getActivity(), "Reset link set to your email", Toast.LENGTH_SHORT).show()).addOnFailureListener(e -> Toast.makeText(getActivity(), "Error! Mail not sent" + e.getMessage(), Toast.LENGTH_SHORT).show());
        });
    }
}