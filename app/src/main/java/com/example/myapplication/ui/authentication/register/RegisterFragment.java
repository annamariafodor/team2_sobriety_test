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

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "RegisterFragment";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RegisterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RegisterFragment.
     */

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

    private void showToast(String text) {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final NavController navController = Navigation.findNavController(view);

        Log.w("Hej", "OnViewCreated");

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.w("Hej", "OnClickEvent");

                //check if the user is created or not


//                if (mAuth.getCurrentUser() != null) {
//                    Intent intent = new Intent(getActivity(), LoginFragment.class);
//                    startActivity(intent);
//                }

                email = emailInput.getText().toString();
                password1 = password1Input.getText().toString();
                password2 = password2Input.getText().toString();

                showToast(email);
                showToast(password1);
                showToast(password2);

                // Initialize Firebase Auth
                mAuth = FirebaseAuth.getInstance();


                // form validation
                if (TextUtils.isEmpty(email)) {
                    //empty field
                    emailInput.setError("Email is Required");
                    return;
                }

                if (!isValid(email)) {
                    emailInput.setError("Email is not valid");
                    return;
                }

                if (TextUtils.isEmpty(password1)) {
                    //empty field
                    password1Input.setError("Password is Required");
                    return;
                }

                if (TextUtils.isEmpty(password2)) {
                    //empty field
                    password2Input.setError("Password is Required");
                    return;
                }

                if (password1.length() < 6) {
                    password1Input.setError("Password must be 6 character long");
                    return;
                }

                if (password2.length() < 6) {
                    password2Input.setError("Password must be 6 character long");
                    return;
                }

                if (!password1.equals(password2)) {
                    password1Input.setError("Passwords must be the same");
                    return;
                }

                // the data is valid here
                // register into firebase

                mAuth.createUserWithEmailAndPassword(email, password1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    // let the user know that the registration was successful
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.w("Test","OnComplete Listener");
                        if (task.isSuccessful()) {

                            Log.w("Hej","Task Successful");
                            Toast.makeText(getActivity(), "User created", Toast.LENGTH_SHORT).show();
                            Log.w("Hej","Message DOne");

                            // to go to the profile fragment
//                            Intent intent = new Intent(getContext(), AuthenticationActivity.class);
//                            startActivity(intent);

                            navController.navigate(R.id.fragment_profile);


                            Log.w("Hej","Gone to profile");
                        } else {
                            Toast.makeText(getActivity(), "Error !" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            Log.w("Hej","Here you shouldn't be");
                        }
                    }
                });
            }
        });
    }

    public static boolean isValid(String email) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }
}