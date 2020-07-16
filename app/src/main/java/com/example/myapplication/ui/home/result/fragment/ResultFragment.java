package com.example.myapplication.ui.home.result.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.myapplication.R;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ResultFragment extends Fragment implements ResultContract.View {
    @BindView(R.id.resultText)
    TextView resultText;
    @BindView(R.id.seekBar2)
    SeekBar seekBar;
    @BindView(R.id.callTaxiButton)
    Button callTaxi;
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

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final NavController navController = Navigation.findNavController(view);
        showTaxiButton(navController);
    }

    private void calculateResult() {
       presenter.getPersonalInformation();
    }

    @Override
    public void showResult(double res) {
        NumberFormat formatter = new DecimalFormat("#0.000");
        if (res > 0) {
            resultText.setText(formatter.format(res) + " %");
            callTaxi.setVisibility(View.VISIBLE);
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
                calendar.add(Calendar.HOUR_OF_DAY, +i);

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
    public void showTaxiButton(NavController navController) {
        callTaxi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.nav_taxi);
            }
        });
    }


    @Override
    public void showLoading() {

    }

}