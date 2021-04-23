package com.example.myapplication.ui.home.listScreen;

import android.content.Context;
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
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.ui.home.mainScreen.MainScreenFragment;
import com.example.myapplication.ui.home.mainScreen.SelectDateFragment;
import com.example.myapplication.ui.home.mainScreen.SelectTimeFragment;
import com.example.myapplication.ui.home.mainScreen.onDateSelected;
import com.google.android.gms.common.util.JsonUtils;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditDataDialog extends DialogFragment implements onDateSelected {

    @BindView(R.id.quantityInput)
    TextInputLayout quantity;
    @BindView(R.id.degreeInput)
    TextInputLayout degree;
    @BindView(R.id.dateInput)
    TextInputLayout dateInput;
    @BindView(R.id.hourInput)
    TextInputLayout hourInput;
    @BindView(R.id.dateTextInput)
    TextInputEditText date;
    @BindView(R.id.hourTextInput)
    TextInputEditText hour;
    @BindView(R.id.cancelButton)
    Button cancel;
    @BindView(R.id.saveButton)
    Button save;

    @Override
    public void sendInputDate(String year, String month, String day) {
        date.setText(year+"/"+month+"/"+day);
    }

    @Override
    public void sendInputHour(String hour, String minute) {
        this.hour.setText(hour+":"+minute);
    }


    public interface  onDataSelected{
        void sendInput(String quantity,String degree,String date, String hour);
    }
    public onDataSelected onDataSelected;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_data,container,false);
        ButterKnife.bind(this, view);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });



        hour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new SelectTimeFragment();
                newFragment.setTargetFragment(EditDataDialog.this,1);
                newFragment.show(getFragmentManager(), "TimePicker");
            }
        });

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new SelectDateFragment();
                newFragment.setTargetFragment(EditDataDialog.this,1);
                newFragment.show(getFragmentManager(), "DatePicker");
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validateInputs(degree,quantity,dateInput,hourInput)){
                    return;
                }
                String quantityString=quantity.getEditText().getText().toString();
                String degreeString=degree.getEditText().getText().toString();
                String dateString=date.getText().toString();
                String hourString=hour.getText().toString();
                onDataSelected.sendInput(quantityString,degreeString,dateString,hourString);
                getDialog().dismiss();
            }
        });

        return view;
    }

    public void onAttach(Context context){
        super.onAttach(context);
        try {
            onDataSelected = (onDataSelected) getTargetFragment();
        }catch (ClassCastException e){}
    }

    private Boolean validateInputs(TextInputLayout degree, TextInputLayout quantity, TextInputLayout date, TextInputLayout hour) {
        String textDegree = degree.getEditText().getText().toString();
        String textQuantity = quantity.getEditText().getText().toString();
        String textDate = date.getEditText().getText().toString();
        String textHour = hour.getEditText().getText().toString();
        Boolean valid = true;


        if (TextUtils.isEmpty(textDegree) || (Double.parseDouble(textDegree)<=0)) {
            degree.getEditText().setError("Enter a valid value for degree");
            valid = false;
        } else {
            degree.getEditText().setError(null);
        }

        if (TextUtils.isEmpty(textQuantity) || (Double.parseDouble(textQuantity)<=0)) {
            quantity.getEditText().setError("Enter a valid value for quantity!");
            valid = false;
        } else {
            quantity.getEditText().setError(null);
        }

        Date date1 = null;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        try {
            date1=formatter.parse(textDate.concat(" ").concat(textHour));
        }catch (Exception e){
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

        if(!TextUtils.isEmpty(textDate) && !TextUtils.isEmpty(textHour)){
            Date currentDate = new Date(System.currentTimeMillis());
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(currentDate);
            calendar.add(calendar.HOUR_OF_DAY, -8);
            if(calendar.getTime().before(date1) && currentDate.after(date1)){ // check if input date is earlier than 24 hours or later than the current date
                hour.getEditText().setError(null);
                date.getEditText().setError(null);
            }else {
                valid = false;
                hour.getEditText().setError("Enter valid value for hour!");
                date.getEditText().setError("Enter valid value for date!");
                Toast.makeText(getActivity(), "The date and time can't be earlier than 8 hours or later than the current time!", Toast.LENGTH_LONG).show();
            }
        }

        return valid;
    }

}
