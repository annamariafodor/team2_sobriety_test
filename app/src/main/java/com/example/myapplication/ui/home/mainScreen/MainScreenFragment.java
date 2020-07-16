package com.example.myapplication.ui.home.mainScreen;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.myapplication.ui.home.listScreen.Model;
import com.example.myapplication.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainScreenFragment extends Fragment implements onDateSelected {

    @BindView(R.id.showButton)
    Button showButton;

    @BindView(R.id.quantityInputLayout)
    TextInputLayout inputQuantity;

    @BindView(R.id.degreeInputLayout)
    TextInputLayout inputDegree;

    @BindView(R.id.hourInputLayout)
    TextInputLayout inputHour;

    @BindView(R.id.dateInputLayout)
    TextInputLayout inputDate;

    @BindView(R.id.dateTextLayout)
    TextInputEditText dateText;

    @BindView(R.id.timeInputLayout)
    TextInputEditText timeText;

    @BindView(R.id.addButton)
    Button addButton;

    @BindView(R.id.resultButton)
    Button resultButton;

    private String quantity, degree, hour, date;
    private Model model;
    private FirebaseAuth fAuth;
    private FirebaseFirestore fStore;
    private String userID;

    public MainScreenFragment() {
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
        View view = inflater.inflate(R.layout.fragment_main_screen, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final NavController navController = Navigation.findNavController(view);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        showButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.nav_list);
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateInputs(inputDegree, inputQuantity, inputDate, inputHour)) {
                    return;
                }
                quantity = inputQuantity.getEditText().getText().toString();
                degree = inputDegree.getEditText().getText().toString();
                hour = inputHour.getEditText().getText().toString();
                date = inputDate.getEditText().getText().toString();
                model = new Model();

                model.setQuantity(quantity);
                model.setDegree(degree);
                model.setTime(hour);
                model.setDate(date);

                userID = fAuth.getCurrentUser().getUid();
                //DocumentReference documentReference = fStore.collection("drinks").document(userID);
                DatabaseReference reff = FirebaseDatabase.getInstance().getReference().child("drinks").child(userID);
                Map<String, Object> drink = new HashMap<>();
                drink.put("quantity", quantity);
                drink.put("degree", degree);
                drink.put("hour", hour);
                drink.put("date", date);

                reff.push().setValue(drink).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getActivity(), "Drink added", Toast.LENGTH_SHORT).show();
                    }
                });
                inputQuantity.getEditText().setText(null);
                inputDegree.getEditText().setText(null);
                inputDate.getEditText().setText(null);
                inputHour.getEditText().setText(null);
            }
        });
        resultButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.nav_result);
            }
        });
    }

    private void initView() {
        timeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new SelectTimeFragment();
                newFragment.setTargetFragment(MainScreenFragment.this, 1);
                newFragment.show(getFragmentManager(), "TimePicker");
            }
        });

        dateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new SelectDateFragment();
                newFragment.setTargetFragment(MainScreenFragment.this, 1);
                newFragment.show(getFragmentManager(), "DatePicker");
            }
        });
    }

    @Override
    public void sendInputDate(String year, String month, String day) {
        dateText.setText(year + "/" + month + "/" + day);
    }

    @Override
    public void sendInputHour(String hour, String minute) {
        timeText.setText(hour + ":" + minute);
    }

    private Boolean validateInputs(TextInputLayout degree, TextInputLayout quantity, TextInputLayout date, TextInputLayout hour) {
        String textDegree = degree.getEditText().getText().toString();
        String textQuantity = quantity.getEditText().getText().toString();
        String textDate = date.getEditText().getText().toString();
        String textHour = hour.getEditText().getText().toString();
        Boolean valid = true;


        if (TextUtils.isEmpty(textDegree) || (Double.parseDouble(textDegree) <= 0)) {
            degree.getEditText().setError("Enter a valid value for degree");
            valid = false;
        } else {
            degree.getEditText().setError(null);
        }

        if (TextUtils.isEmpty(textQuantity) || (Double.parseDouble(textQuantity) <= 0)) {
            quantity.getEditText().setError("Enter a valid value for quantity!");
            valid = false;
        } else {
            quantity.getEditText().setError(null);
        }

        Date date1 = null;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        try {
            date1 = formatter.parse(textDate.concat(" ").concat(textHour));
        } catch (Exception e) {
            Toast.makeText(getActivity(), "Invalid inputs", Toast.LENGTH_SHORT).show();

        }

        if (TextUtils.isEmpty(textDate)) {
            date.getEditText().setError("Enter value for date!");
            valid = false;
        } else {
            date.getEditText().setError(null);
        }

        if (TextUtils.isEmpty(textHour)) {
            hour.getEditText().setError("Enter value for hour!");
            valid = false;
        } else {
            hour.getEditText().setError(null);
        }

        if (!TextUtils.isEmpty(textDate) && !TextUtils.isEmpty(textHour)) {
            Date currentDate = new Date(System.currentTimeMillis());
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(currentDate);
            calendar.add(Calendar.HOUR_OF_DAY, -8);
            if (calendar.getTime().before(date1) && currentDate.after(date1)) { // check if input date is earlier than 24 hours or later than the current date
                hour.getEditText().setError(null);
                date.getEditText().setError(null);
            } else {
                valid = false;
                hour.getEditText().setError("Enter valid value for hour!");
                date.getEditText().setError("Enter valid value for date!");
                Toast.makeText(getActivity(), "The date and time can't be earlier than 8 hours or later than the current time!", Toast.LENGTH_LONG).show();
            }
        }
        return valid;
    }
}