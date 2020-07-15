package com.example.myapplication.ui.home.help;

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
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;


public class HelpFragment extends Fragment {
    @BindView(R.id.sendButton)
    Button send;
    @BindView(R.id.textInputEditText)
    TextInputEditText message;

    private DatabaseReference myRef;
    private HelpInformations info;

    public HelpFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference().child("Messages");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_help, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        info = new HelpInformations();
        send.setOnClickListener(view1 -> {
            FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
            assert currentFirebaseUser != null;
            String email = Objects.requireNonNull(currentFirebaseUser.getEmail());
            String messageStr = Objects.requireNonNull(message.getText()).toString();
            if (messageStr.isEmpty()) {
                message.setError("Write something");
                return;
            }
            info.setEmail(email);
            info.setMessage(messageStr);
            myRef.push().setValue(info).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    message.getText().clear();
                    Toast.makeText(getActivity(), "Successfully sent", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Send is unsuccessful", Toast.LENGTH_SHORT).show();
                }
            });
        });

    }
}