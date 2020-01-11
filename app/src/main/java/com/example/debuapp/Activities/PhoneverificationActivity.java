package com.example.debuapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.debuapp.R;
import com.example.debuapp.utils.Loader;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

public class PhoneverificationActivity extends AppCompatActivity {
    private EditText otp;
    private Button submit;
    private TextView resend,text;
    private String number,id;
    private FirebaseAuth mAuth;
    private Loader loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_verification);


        otp=findViewById(R.id.otp);
        submit=findViewById(R.id.submit);
        resend=findViewById(R.id.resend);
        text=findViewById(R.id.text);

        loader=new Loader(this);


        mAuth=FirebaseAuth.getInstance();
        number=getIntent().getStringExtra("number");
        Log.i("hghbihiuj", "onCreate: "+number);
        sendVerificationCode();



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(otp.getText().toString())){
                    Toast.makeText(PhoneverificationActivity.this, "enter otp", Toast.LENGTH_SHORT).show();
                }
                else if (otp.getText().toString().replace(" ","").length()!=6){

                    Toast.makeText(PhoneverificationActivity.this, "enter right", Toast.LENGTH_SHORT).show();
                }
                else {
                    loader.show();
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(id, otp.getText().toString().replace("",""));
                    signInWithPhoneAuthCredential(credential);

                }
            }
        });


        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendVerificationCode();
            }
        });


    }

    private void sendVerificationCode() {
        new CountDownTimer(60000,1000) {
            @Override
            public void onTick(long l) {
                resend.setText(""+l/1000);
                resend.setEnabled(false);

            }

            @Override
            public void onFinish() {
                resend.setText("resend");
                  resend.setEnabled(true);

            }
        }.start();

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                number,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onCodeSent(@NonNull String id, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        PhoneverificationActivity.this.id=id;
                    }

                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                        signInWithPhoneAuthCredential(phoneAuthCredential);
                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {
                        Log.i("Aamir", "onVerificationFailed: "+e.getMessage());
                         Toast.makeText(PhoneverificationActivity.this, "Verification failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            loader.dismiss();
                            FirebaseUser user = task.getResult().getUser();

                            FirebaseDatabase.getInstance().getReference().child("User").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.hasChild(FirebaseAuth.getInstance().getUid())){
                                        startActivity((new Intent(PhoneverificationActivity.this,Dashboardactivity.class)));
                                        finish();
                                    }
                                    else{

                                        Intent intent=new Intent(PhoneverificationActivity.this,Registeractivity.class);
                                        intent.putExtra("number",number);
                                        startActivity(intent);
                                        finish();

                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                            // ...
                        } else {
                            loader.dismiss();
                            Log.i("mayank", "onComplete: "+task.getException().getMessage());
                            Toast.makeText(PhoneverificationActivity.this, "verification failrd", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }


}

