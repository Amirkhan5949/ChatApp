package com.example.debuapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.Model.Message;
import com.example.debuapp.R;
import com.example.debuapp.utils.FirebaseConstants;
import com.example.debuapp.Adapters.MessageAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ServerValue;

import java.util.HashMap;

public class ChatActivity extends AppCompatActivity {
    private EditText edit;
    private ImageView send;
    private RecyclerView recycler;
    private MessageAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        edit=findViewById(R.id.edit);
        send=findViewById(R.id.image);
        recycler=findViewById(R.id.recycler);
        final DatabaseReference base =FirebaseDatabase.getInstance().getReference().child(FirebaseConstants.Message.key);
        final String s=getIntent().getStringExtra("id");
        final DatabaseReference ourMessage = base.child(FirebaseAuth.getInstance().getUid()).child(s).push();
        final String pushId = ourMessage.getKey();


        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        recycler.setLayoutManager(layoutManager);

        Query query = base.child(FirebaseAuth.getInstance().getUid()).child(s);

        FirebaseRecyclerOptions<Message> options =
                new FirebaseRecyclerOptions.Builder<Message>()
                        .setQuery(query, Message.class)
                        .build();

        adapter=new MessageAdapter(this,options,s,pushId);
        recycler.setAdapter(adapter);









        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DatabaseReference ourMessage = base.child(FirebaseAuth.getInstance().getUid()).child(s).push();
                final String pushId = ourMessage.getKey();

                HashMap<String,Object>map=new HashMap<>();
                map.put(FirebaseConstants.Message.message,edit.getText().toString());
                map.put(FirebaseConstants.Message.status,false);
                map.put(FirebaseConstants.Message.time_stemp, ServerValue.TIMESTAMP);
                map.put(FirebaseConstants.Message.type,FirebaseConstants.Message.Type.SEND);
                map.put(FirebaseConstants.Message.tap,pushId);



                ourMessage.setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        final DatabaseReference mymessage= base.child(s).child(FirebaseAuth.getInstance().getUid()).child(pushId);
                        HashMap<String,Object>map=new HashMap<>();
                        map.put(FirebaseConstants.Message.message,edit.getText().toString());
                        map.put(FirebaseConstants.Message.status,false);
                        map.put(FirebaseConstants.Message.time_stemp, ServerValue.TIMESTAMP);
                        map.put(FirebaseConstants.Message.type,FirebaseConstants.Message.Type.RECEIVE);
                        map.put(FirebaseConstants.Message.tap,pushId);

                        mymessage.setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                edit.setText("");

                                recycler.smoothScrollToPosition(adapter.getItemCount() - 1);
                            }
                        });

                    }
                });





            }
        });








    }

    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
