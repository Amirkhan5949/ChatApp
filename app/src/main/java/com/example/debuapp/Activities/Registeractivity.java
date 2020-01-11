package com.example.debuapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.debuapp.R;
import com.example.debuapp.utils.FirebaseConstants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Registeractivity extends AppCompatActivity {

    private EditText user,email,address;
    private DatabaseReference User;
    private Button submit;
    private String number;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dataactivity);

        user=findViewById(R.id.user);
        email=findViewById(R.id.email);
        address=findViewById(R.id.address);
        submit=findViewById(R.id.submit);



        number=getIntent().getStringExtra("number");

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User= FirebaseDatabase.getInstance().getReference().child(FirebaseConstants.User.key);


                HashMap<String,Object> map = new HashMap<>();
                map.put(FirebaseConstants.User.user,user.getText().toString());
                map.put(FirebaseConstants.User.email,email.getText().toString());
                map.put(FirebaseConstants.User.address,address.getText().toString());
                map.put(FirebaseConstants.User.number,number);




                User.child(FirebaseAuth.getInstance().getUid())
                        .setValue(map)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Log.i("jfbvkj", "onComplete: ");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.i("jfbvkj", "onFailure: "+e.toString());
                            }
                        }).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(Registeractivity.this, "Successfully submit", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Registeractivity.this,Dashboardactivity.class));
                        finish();
                    }
                });

            }
        });

    }
}
