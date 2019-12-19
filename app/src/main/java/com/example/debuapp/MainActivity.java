package com.example.debuapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (FirebaseAuth.getInstance().getCurrentUser()==null){
                startActivity(new Intent(MainActivity.this,loginActivity.class));
                finish();
                }
                else{
                    startActivity(new Intent(MainActivity.this,dashboardActivity.class));
                    finish();

                }

            }
        },3000);


    }
}
