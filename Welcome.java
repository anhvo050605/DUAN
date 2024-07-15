package com.example.duan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class Welcome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_welcome);

//        new CountDownTimer(5000,1000){
//
//            @Override
//            public void onTick(long millisUntilFinished) {
//
//            }
//
//            @Override
//            public void onFinish() {
//                Intent intent =  new Intent(Welcome.this,Login.class);
//                startActivity(intent);
//            }
//        }.start();
        Button LoginWelcome = findViewById(R.id.LoginWelcome);
        Button KhamPha = findViewById(R.id.KhamPha);

        LoginWelcome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Welcome.this,Login.class);
                startActivity(intent);
            }
        });
        KhamPha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}