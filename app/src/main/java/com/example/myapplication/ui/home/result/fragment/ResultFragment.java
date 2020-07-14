package com.example.myapplication.ui.home.result.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SeekBar;
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
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ResultFragment extends Fragment implements ResultContract.View {

    @BindView(R.id.xButton)
    ImageButton backButton;
    @BindView(R.id.taxiButton)
    ImageButton taxiButton;
    @BindView(R.id.resultText)
    TextView resultText;
    @BindView(R.id.seekBar2)
    SeekBar seekBar;
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

    private void calculateResult() {
        presenter.getPersonalInformation();
    }

    @Override
    public void showResult(double res) {
        NumberFormat formatter = new DecimalFormat("#0.000");
        if (res > 0) {
            resultText.setText(formatter.format(res) + " %");
        } else {
            resultText.setText("0 %");
        }
    }

    @Override
    public void initializeSeekBar(Date elsoDatum, long hours, double res) {
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                Date currentDate = new Date(System.currentTimeMillis());
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(currentDate);
                calendar.add(calendar.HOUR_OF_DAY, +i);

                long secs = (calendar.getTime().getTime() - elsoDatum.getTime()) / 1000;
                long h = (secs / 3600);

                double result = res - 0.015 * (h - hours);
                showResult(result);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }


    @Override
    public void showLoading() {

    }
}