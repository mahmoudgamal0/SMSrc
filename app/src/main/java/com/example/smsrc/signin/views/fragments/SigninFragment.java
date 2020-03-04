package com.example.smsrc.signin.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.smsrc.R;


public class SigninFragment extends Fragment {
    private NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_signin, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        instantiateListeners(view);
    }

    private void instantiateListeners(View view) {
        Button signinToLoginBtn = view.findViewById(R.id.btn_navigate_signin);
        Button signinToUsersBtn = view.findViewById(R.id.btn_navigate_users);

        signinToLoginBtn.setOnClickListener(v -> navController.navigate(R.id.action_signinFragment_to_loginFragment));
        signinToUsersBtn.setOnClickListener(v -> navController.navigate(R.id.action_signinFragment_to_usersListFragment));
    }
}
