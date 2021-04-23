package com.example.myapplication.ui.home.listScreen;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.myapplication.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ListScreenFragment extends Fragment implements EditDataDialog.onDataSelected {


    @BindView(R.id.recyclerView)
    RecyclerView myRecyclerView;

    private MyAdapter myAdapter;
    private FirebaseAuth fAuth;
    private FirebaseFirestore fStore;
    private DatabaseReference reference;
    private String userID;
    private ArrayList<Model> list;
    private Model m;

    public ListScreenFragment() {
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
        View view = inflater.inflate(R.layout.fragment_list_screen, container, false);
        ButterKnife.bind(this, view);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        list = new ArrayList<Model>();
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userID = fAuth.getCurrentUser().getUid();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("drinks").child(userID);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot s : snapshot.getChildren()) {
                    Model m = new Model();
                    m.setTime(s.child("hour").getValue().toString());
                    m.setDate(s.child("date").getValue().toString());
                    m.setDegree(s.child("degree").getValue().toString());
                    m.setQuantity(s.child("quantity").getValue().toString());
                    m.setKey(s.getKey());
                    list.add(m);

                }
                myAdapter = new MyAdapter(getContext(), list);
                myRecyclerView.setAdapter(myAdapter);

                myAdapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
                    @Override
                    public void deleteItem(Model model, int position) {
                        reference.child(model.getKey()).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                snapshot.getRef().removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(getContext(), "Drink deleted", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                myAdapter.remove(position);
                                myAdapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });
                    }

                    @Override
                    public void editItem(Model model, int position) {
                        EditDataDialog dialog = new EditDataDialog();
                        dialog.setTargetFragment(ListScreenFragment.this, 1);
                        dialog.show(getFragmentManager(), "Edit data dialog");
                        m = model;
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        return view;
    }

    @Override
    public void sendInput(String quantity, String degree, String date, String hour) {
        m.setQuantity(quantity);
        m.setDegree(degree);
        m.setDate(date);
        m.setTime(hour);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userID = fAuth.getCurrentUser().getUid();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("drinks").child(userID);
        reference.child(m.getKey()).child("date").setValue(date);
        reference.child(m.getKey()).child("hour").setValue(hour);
        reference.child(m.getKey()).child("degree").setValue(degree);
        reference.child(m.getKey()).child("quantity").setValue(quantity);
        myAdapter.notifyDataSetChanged();
        Toast.makeText(getContext(), "Drink modified", Toast.LENGTH_SHORT).show();
    }
}