package com.example.myapplication.ui.home.listScreen;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.myapplication.ListScreen;
import com.example.myapplication.Model;
import com.example.myapplication.MyAdapter;
import com.example.myapplication.R;
import com.example.myapplication.ui.home.mainScreen.SelectDateFragment;
import com.example.myapplication.ui.home.mainScreen.SelectTimeFragment;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.xml.sax.helpers.AttributesImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListScreenFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListScreenFragment extends Fragment implements EditDataDialog.onDataSelected{


    @BindView(R.id.recyclerView)
    RecyclerView myRecyclerView;
    MyAdapter myAdapter;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    DatabaseReference reference;
    String userID;
    ArrayList<Model> list;
    Model m;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ListScreenFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListScreenFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListScreenFragment newInstance(String param1, String param2) {
        ListScreenFragment fragment = new ListScreenFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
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
                    System.out.println("meret: " + list.size());

                }
                myAdapter=new MyAdapter(getContext(),list);
                myRecyclerView.setAdapter(myAdapter);

                myAdapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
                    @Override
                    public void deleteItem(Model model, int position) {
                         reference.child(model.getKey()).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                snapshot.getRef().removeValue();
                                System.out.println("Elottmeret: " + position);
                                myAdapter.remove(position);
                                System.out.println("Utanmeret: " + position);
                                myAdapter.notifyItemRemoved(position);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }

                    @Override
                    public void editItem(Model model, int position) {
                        EditDataDialog dialog = new EditDataDialog();
                        dialog.setTargetFragment(ListScreenFragment.this,1);
                        dialog.show(getFragmentManager(),"Edit data dialog");
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
    }
}