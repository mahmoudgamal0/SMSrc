package com.example.smsrc.splash.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import com.example.smsrc.R;


public class SplashFragment extends Fragment {
    private NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_splash, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        instantiateListeners(view);
    }

    private void instantiateListeners(View view) {
        Button splashToSigninBtn = view.findViewById(R.id.btn_navigate_users);
        splashToSigninBtn.setOnClickListener(v -> navController.navigate(R.id.action_splashFragment_to_signinFragment));
    }
}
