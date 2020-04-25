package com.example.smsrc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(this::startMainActivity, 3000);
    }

    public void startMainActivity(){
        startActivity(new Intent(this.getApplicationContext(), MainActivity.class));
        finish();
    }

}
