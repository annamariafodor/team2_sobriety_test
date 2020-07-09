package com.example.myapplication.ui.home.mainScreen;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.DialogFragment;

import com.example.myapplication.R;
import com.google.android.material.textfield.TextInputEditText;

import java.text.DateFormat;
import java.util.Calendar;

public class SelectTimeFragment extends AppCompatDialogFragment implements TimePickerDialog.OnTimeSetListener {

    TextInputEditText editTime;
    public onDateSelected onDateSelected;

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog dialog = new TimePickerDialog(getActivity(),R.style.timepicker,this,hour,minute,true);

        return dialog;
    }


    @Override
    public void onTimeSet(TimePicker view, int hour, int minute) {
        String h = String.valueOf(hour);
        String m = String.valueOf(minute);

//        editTime = (TextInputEditText) getActivity().findViewById(R.id.timeInputLayout);
//        editTime.setText(h+":"+m);
        onDateSelected.sendInputHour(h,m);
    }

    public void onAttach(Context context){
        super.onAttach(context);
        try {
            onDateSelected = (onDateSelected) getTargetFragment();
        }catch (ClassCastException e){}
    }

}