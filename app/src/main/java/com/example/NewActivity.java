package com.example;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.example.debuapp.R;

public class NewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);

       String s=getIntent().getStringExtra("id");
        Toast.makeText(this, ""+s, Toast.LENGTH_SHORT).show();
    }
}
