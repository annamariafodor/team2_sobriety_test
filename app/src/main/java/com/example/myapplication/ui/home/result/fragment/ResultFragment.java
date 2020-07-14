package com.example.myapplication.ui.home.result.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.myapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ResultFragment extends Fragment implements  ResultContract.View{
    private double res;
    @BindView(R.id.xButton)
    ImageButton backButton;
    @BindView(R.id.taxiButton)
    ImageButton taxiButton;
    @BindView(R.id.resultText)
    TextView resultText;
    private ResultContract.Presenter presenter = new ResultPresenter(this);

    public ResultFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //final NavController navController = Navigation.findNavController(view);
        View view = inflater.inflate(R.layout.fragment_result, container, false);
        ButterKnife.bind(this, view);
        calculateResult();
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Debug", "Lecci");
                final NavController navController = Navigation.findNavController(view);
                //navController.navigate(R.id.);
            }
        });

        taxiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final NavController navController = Navigation.findNavController(view);
        Log.d("Dialog",String.valueOf(res));
        if ( res > 0 ) {
            Log.d("Dialog","bement");
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("Call a taxi")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //navController.navigate(R.id.nav_taxi);
                        }
                    })
                    .setNegativeButton("No", null);
            AlertDialog alert = builder.create();
            alert.show();

        }
    }

    private void calculateResult() {
       res =  presenter.getPersonalInformation();
    }

    @Override
    public void showResult(double res){
        NumberFormat formatter = new DecimalFormat("#0.000");
        resultText.setText(formatter.format(res));
    }

    @Override
    public void showLoading() {

    }
}