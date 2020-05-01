package com.example.smsrc.restore.views;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.smsrc.R;
import com.example.smsrc.cache.CacheManager;
import com.example.smsrc.restore.presenter.RestoreStatePresenter;
import com.google.android.material.textfield.TextInputEditText;

public class RestoreStateFragment extends Fragment {

    private RestoreStatePresenter presenter;
    private CacheManager cacheManager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_restore_state, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = new RestoreStatePresenter(this.getContext());
        cacheManager = new CacheManager(this.getContext());

        if(cacheManager.getCachedPin() != null) {
            view.findViewById(R.id.pin).setVisibility(View.VISIBLE);
        }

        initListeners(view);
    }

    private void initListeners(View view) {
        Button restoreStateBtn = view.findViewById(R.id.restore_state_btn);
        restoreStateBtn.setOnClickListener(e-> {
            String pin = ((TextInputEditText)view.findViewById(R.id.pin)).getText().toString();
            String password = ((TextInputEditText)view.findViewById(R.id.password)).getText().toString();
            if(cacheManager.getCachedPin() != null){
                if(presenter.validateUser(password, pin)){
                    presenter.restorePhoneState();
                } else {
                    Toast.makeText(this.getContext(), "Wrong Pin or Password", Toast.LENGTH_LONG).show();
                }
            } else {
                if(presenter.validateUser(password, null)){
                    presenter.restorePhoneState();
                } else {
                    Toast.makeText(this.getContext(), "Wrong Password", Toast.LENGTH_LONG).show();
                }
            }

        });
    }
}
