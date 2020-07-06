package com.example.myapplication.ui.home.mainScreen;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.DialogFragment;

import com.example.myapplication.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.zip.Inflater;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SelectDateFragment extends AppCompatDialogFragment implements DatePickerDialog.OnDateSetListener {

    TextInputEditText editDate;

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dialog = new DatePickerDialog(getActivity(), R.style.datepicker, SelectDateFragment.this, year, month, day);

        return dialog;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        month = month + 1;
        String y = String.valueOf(year);
        String m = String.valueOf(month);
        String d = String.valueOf(day);

        editDate = (TextInputEditText) getActivity().findViewById(R.id.dateTextLayout);
        editDate.setText(y+"/"+m+"/"+d);

    }


}
