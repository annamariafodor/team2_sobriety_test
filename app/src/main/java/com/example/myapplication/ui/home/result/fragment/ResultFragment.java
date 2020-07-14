package com.example.myapplication.ui.home.result.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import com.example.myapplication.R;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import butterknife.BindView;
import butterknife.ButterKnife;


public class ResultFragment extends Fragment implements  ResultContract.View{

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
    public void showResult(double res){
        NumberFormat formatter = new DecimalFormat("#0.000");
        resultText.setText(formatter.format(res));
    }

    @Override
    public void showLoading() {

    }
}