package com.example.debuapp.UI.Fragment;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.debuapp.R;
import com.example.debuapp.Adapters.Chatadapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


/**
 * A simple {@link Fragment} subclass.
 */
public class Chatfragment extends Fragment {
    private RecyclerView recyclerView;
    private View view;
    private Chatadapter chatadapter;
    private List<String> list1;




    public Chatfragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


      view=inflater.inflate(R.layout.fragment_chat, container, false);
      recyclerView=view.findViewById(R.id.recycler);
      recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));



        FirebaseDatabase.getInstance().getReference()
                .child("Message")
                .child(FirebaseAuth.getInstance().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Log.i("try", "onDataChange: "+dataSnapshot.toString());


                        list1=new ArrayList<String>();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                            list1.add(snapshot.getKey().toString());
                            Log.i("forchecking", "onDataChange: "+(snapshot.getKey().toString()));
                        }

                        Log.i("showdata", "onDataChange: "+list1.toString());


                        Chatadapter chatadapter=new Chatadapter(list1,getContext());
                        recyclerView.setAdapter(chatadapter);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });





      return view;
    }


}
