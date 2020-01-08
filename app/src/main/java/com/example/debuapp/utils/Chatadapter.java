package com.example.debuapp.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.debuapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class Chatadapter extends RecyclerView.Adapter<Chatadapter.ChatViewHolder> {
    ArrayList arrayList;
    Context context;

    List<String> list;

    public Chatadapter(List<String> list, Context context){
        this.context=context;

        this.list = list;
    }
    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.chat,parent,false);
        return new ChatViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull final ChatViewHolder holder, int position) {

        String id = list.get(position);

        FirebaseDatabase.getInstance().getReference()
                .child("User")
                .child(id)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String s="User Name :"+dataSnapshot.child("user").getValue().toString();
                        holder.username.setText(s);


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ChatViewHolder extends RecyclerView.ViewHolder {
        CircleImageView imageView;
        TextView username,status;

        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView=itemView.findViewById(R.id.png);
            username=itemView.findViewById(R.id.username);
            status=itemView.findViewById(R.id.status);
        }
    }
}