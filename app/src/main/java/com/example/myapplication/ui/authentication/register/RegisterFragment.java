package com.example.myapplication.ui.authentication.register;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.ui.authentication.login.LoginFragment;
import com.example.myapplication.ui.home.mainScreen.MainScreenFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegisterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class RegisterFragment extends Fragment {

    //register information
    String email,password1,password2;

    EditText emailInput;
    EditText password1Input;
    EditText password2Input;

    Button submitButton;
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

        emailInput = (EditText) getView().findViewById(R.id.email_input);
        password1Input = (EditText) getView().findViewById(R.id.password1);
        password2Input = (EditText) getView().findViewById(R.id.password2);

        submitButton = (Button) getView().findViewById(R.id.registButton) ;

        // check if the user is created or not
//        if(mAuth.getCurrentUser() != null){
//            Intent intent = new Intent(getActivity(), LoginFragment.class);
//            startActivity(intent);
//        }

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = emailInput.getText().toString();
                password1 = password1Input.getText().toString();
                password2 =  password2Input.getText().toString();

                showToast(email);
                showToast(password1);
                showToast(password2);

                // Initialize Firebase Auth
                mAuth = FirebaseAuth.getInstance();


                // form validation
                if (TextUtils.isEmpty(email)){
                    //empty field
                    emailInput.setError("Email is Required");
                    return;
                }

                if (TextUtils.isEmpty(password1)){
                    //empty field
                    emailInput.setError("Password is Required");
                    return;
                }

                if (TextUtils.isEmpty(password2)){
                    //empty field
                    emailInput.setError("Password is Required");
                    return;
                }

                if (password1.length()  < 6 ){
                    password1Input.setError("Password must be 6 character long");
                    return;
                }

                if ( password2.length() < 6){
                    password1Input.setError("Password must be 6 character long");
                    return;
                }

                if (password1.equals(password2)) {
                    password1Input.setError("Passwords must be the same");
                    return;
                }

                // the data is valid here
                // register into firebase

                mAuth.createUserWithEmailAndPassword(email,password1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    // let the user know that the registration was successful
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getActivity(),"User created", Toast.LENGTH_SHORT).show();

                            // to go to the other fragment
                            Intent intent = new Intent(getActivity(), MainScreenFragment.class);
                            startActivity(intent);
                        }else{
                            Toast.makeText(getActivity(), "Error !" + task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    private void showToast(String text) {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false);
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}