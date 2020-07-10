package com.example.myapplication.ui.home.settings;

import android.content.Intent;
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
import com.example.myapplication.ui.authentication.AuthenticationActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingsFragment extends Fragment {
    @BindView(R.id.email_input)
    TextInputEditText email;
    @BindView(R.id.passwordInput)
    EditText password;
    @BindView(R.id.signOutButton)
    Button signOutButton;
    @BindView(R.id.changeEmailButton)
    Button changeEmailButton;
    @BindView(R.id.changePaswordButton)
    Button changePasswordButton;

    FirebaseAuth mAuth;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SettingsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SettingsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SettingsFragment newInstance(String param1, String param2) {
        SettingsFragment fragment = new SettingsFragment();
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
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings,container,false);
        ButterKnife.bind(this, view);
        initView();
        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String emailStr = email.getText().toString().trim();
        String passwordStr = password.getText().toString().trim();
        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getContext(), AuthenticationActivity.class);
                startActivity(intent);
            }
        });
        changeEmailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailStr = email.getText().toString().trim();
                if (TextUtils.isEmpty(emailStr)) {
                    email.setError("Email required");
                    return;
                }
                FirebaseUser user = mAuth.getCurrentUser();
                user.updateEmail(emailStr)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getActivity(), "Email changed", Toast.LENGTH_SHORT).show();
                            Log.d("Change", "User email address updated.");
                        }else {
                            Toast.makeText(getActivity(), "Error !" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                email.getText().clear();
            }
        });
        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String passwordStr = password.getText().toString().trim();
                if (passwordStr.length() < 6) {
                    password.setError("Password must be 6 character long");
                    return;
                }
                if (TextUtils.isEmpty(passwordStr)) {
                    email.setError("Email required");
                    return;
                }
                FirebaseUser user = mAuth.getCurrentUser();
                user.updatePassword(passwordStr)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getActivity(), "Password changed", Toast.LENGTH_SHORT).show();
                                    Log.d("Change", "User password updated.");
                                } else {
                                    Toast.makeText(getActivity(), "Error !" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                password.getText().clear();
            }
        });
    }

    private void initView() {
    }
}