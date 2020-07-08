package com.example.myapplication.ui.home.listScreen;

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
import com.google.android.material.textfield.TextInputLayout;

import butterknife.BindView;

public class EditDataDialog extends DialogFragment {

//    @BindView(R.id.quantityInput)
//    TextInputLayout quantity;
//
//    @BindView(R.id.degreeInput)
//    TextInputLayout degree;
//
//    @BindView(R.id.dateInput)
//    TextInputLayout date;
//
//    @BindView(R.id.hourInput)
//    TextInputLayout hour;
//
//    @BindView(R.id.cancelButton)
//    Button cancel;
//
//    @BindView(R.id.saveButton)
//    Button save;
    Button cancel,save;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_data,container,false);

//        cancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                getDialog().dismiss();
//            }
//        });
//
//        save.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

        return view;
    }
}
