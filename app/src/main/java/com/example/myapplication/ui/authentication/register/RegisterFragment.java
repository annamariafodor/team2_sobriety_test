package com.example.myapplication.ui.authentication.register;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterFragment extends Fragment {

    @BindView(R.id.registButton)
    Button submitButton;

    @BindView(R.id.password1)
    EditText password1Input;

    @BindView(R.id.password2)
    EditText password2Input;

    @BindView(R.id.emailInput)
    EditText emailInput;

    // these for getting the exact information
    String email, password1, password2;

    // for firebase
    private FirebaseAuth mAuth;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "RegisterFragment";


    private String mParam1;
    private String mParam2;

    public RegisterFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static RegisterFragment newInstance(String param1, String param2) {
        RegisterFragment fragment = new RegisterFragment();
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
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final NavController navController = Navigation.findNavController(view);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // clickEvent to go Profile fragment
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                email = emailInput.getText().toString();
                password1 = password1Input.getText().toString();
                password2 = password2Input.getText().toString();
                if (!isValidForm(email, password1, password2)) {
                    return;
                }


                // create a new user in the database
                mAuth.createUserWithEmailAndPassword(email, password1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                    // let the user know that the registration was successful
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.w("Test", "OnComplete Listener");
                        if (task.isSuccessful()) {
                            Toast.makeText(requireActivity().getBaseContext(), "User created", Toast.LENGTH_SHORT).show();
                            navController.navigate(R.id.fragment_profile);
                        } else {
                            Toast.makeText(requireActivity().getBaseContext(), "Error !" + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    }
                });
            }
        });
    }


    public boolean isValidForm(String email, String password1, String password2) {
        // form validation
        if (TextUtils.isEmpty(email)) {
            emailInput.setError("Email is Required");
            return false;
        }

        if (!isValid(email)) {
            emailInput.setError("Email is not valid");
            return false;
        }

        if (TextUtils.isEmpty(password1)) {
            password1Input.setError("Password is Required");
            return false;
        }

        if (TextUtils.isEmpty(password2)) {
            password2Input.setError("Password is Required");
            return false;
        }

        if (password1.length() < 6) {
            password1Input.setError("Password must be 6 character long");
            return false;
        }

        if (password2.length() < 6) {
            password2Input.setError("Password must be 6 character long");
            return false;
        }

        if (!password1.equals(password2)) {
            password1Input.setError("Passwords must be the same");
            return false;
        }
        return true;
    }

    public static boolean isValid(String email) {
        String regex = "^[\\w-_.+]*[\\w-_.]@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }
}