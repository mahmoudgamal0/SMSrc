package com.example.smsrc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import com.example.smsrc.requester.Requester;

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

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        Requester requester = new Requester(this);
        requester.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
