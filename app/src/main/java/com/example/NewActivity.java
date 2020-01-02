package com.example;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.Model.Message;
import com.example.debuapp.R;
import com.example.Model.User;
import com.example.debuapp.Useradapter;
import com.example.debuapp.utils.FirebaseConstants;
import com.example.debuapp.utils.MessageAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.HashMap;

public class NewActivity extends AppCompatActivity {
    private EditText edit;
    private ImageView send;
    private RecyclerView recycler;
    private MessageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);

        edit=findViewById(R.id.edit);
        send=findViewById(R.id.image);
        recycler=findViewById(R.id.recycler);
        final DatabaseReference base =FirebaseDatabase.getInstance().getReference().child(FirebaseConstants.Message.key);
        final String s=getIntent().getStringExtra("id");

        recycler.setLayoutManager(new LinearLayoutManager(this));

        Query query = base.child(FirebaseAuth.getInstance().getUid()).child(s);

        FirebaseRecyclerOptions<Message> options =
                new FirebaseRecyclerOptions.Builder<Message>()
                        .setQuery(query, Message.class)
                        .build();

        adapter=new MessageAdapter(this,options);
        recycler.setAdapter(adapter);









        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String,Object>map=new HashMap<>();
                map.put(FirebaseConstants.Message.message,edit.getText().toString());
                map.put(FirebaseConstants.Message.status,false);
                map.put(FirebaseConstants.Message.type,FirebaseConstants.Message.Type.SEND);




                base.child(FirebaseAuth.getInstance().getUid()).child(s)
                        .push().setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        HashMap<String,Object>map=new HashMap<>();
                        map.put(FirebaseConstants.Message.message,edit.getText().toString());
                        map.put(FirebaseConstants.Message.status,false);
                        map.put(FirebaseConstants.Message.type,FirebaseConstants.Message.Type.RECEIVE);
                        base.child(s).child(FirebaseAuth.getInstance().getUid()).push().setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(NewActivity.this, "success", Toast.LENGTH_SHORT).show();
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
