package com.example.debuapp;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


/**
 * A simple {@link Fragment} subclass.
 */
public class AlluserFragment extends Fragment {

    private RecyclerView recyclerView;
    private View view;
    private  Useradapter useradapter;



    public AlluserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_user, container, false);
        // Inflate the layout for this fragment

        recyclerView=view.findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        FirebaseRecyclerOptions<User> options =
                new FirebaseRecyclerOptions.Builder<User>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("User"), User.class)
                        .build();

          useradapter=new Useradapter(options);
          recyclerView.setAdapter(useradapter);

        return view;
    }
    @Override
    public void onStart() {
        super.onStart();
        useradapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        useradapter.stopListening();
    }

}
