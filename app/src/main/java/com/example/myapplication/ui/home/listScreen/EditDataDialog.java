package com.example.myapplication.ui.home.listScreen;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.ui.home.mainScreen.SelectDateFragment;
import com.example.myapplication.ui.home.mainScreen.SelectTimeFragment;
import com.google.android.gms.common.util.JsonUtils;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditDataDialog extends DialogFragment {

    @BindView(R.id.quantityInput)
    TextInputLayout quantity;
    @BindView(R.id.degreeInput)
    TextInputLayout degree;
    @BindView(R.id.dateTextInput)
    TextInputEditText date;
    @BindView(R.id.hourTextInput)
    TextInputEditText hour;
    @BindView(R.id.cancelButton)
    Button cancel;
    @BindView(R.id.saveButton)
    Button save;


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
                newFragment.show(getChildFragmentManager(), "TimePicker");
            }
        });

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new SelectDateFragment();
                newFragment.show(getChildFragmentManager(), "DatePicker");
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

}
