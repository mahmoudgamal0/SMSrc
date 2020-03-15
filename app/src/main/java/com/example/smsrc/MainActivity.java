package com.example.smsrc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void hideNavigation(){
        findViewById(R.id.home_bottom_nav).setVisibility(View.GONE);
    }

    public void showNavigation(){
        findViewById(R.id.home_bottom_nav).setVisibility(View.VISIBLE);
    }
}
