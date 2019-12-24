package com.example.debuapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hbb20.CountryCodePicker;

public class Loginactivity extends AppCompatActivity {
    private EditText number;
    private Button send;
    private CountryCodePicker cpp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        number=findViewById(R.id.number);
        send=findViewById(R.id.send);
        cpp=findViewById(R.id.cpp);

        cpp.registerCarrierNumberEditText(number);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(number.getText().toString())){
                    Toast.makeText(Loginactivity.this, "Enter your no", Toast.LENGTH_SHORT).show();
                }
                else if (number.getText().toString().replace(" ","").length()!=10){
                    Toast.makeText(Loginactivity.this, "Enter correct no", Toast.LENGTH_SHORT).show();
                }
                else {
                   Intent intent= new Intent(Loginactivity.this,PhoneverificationActivity.class);
                   intent.putExtra("number",cpp.getFullNumberWithPlus().replace("",""));
                   startActivity(intent);
                   finish();

                }
            }
        });
    }
}
