package com.example.smsrc.login.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.smsrc.R;
import com.example.smsrc.cache.CacheManager;
import com.example.smsrc.login.presenter.LoginPresenter;
import com.example.smsrc.users.dals.UserRepository;
import com.google.android.material.textfield.TextInputEditText;


public class LoginFragment extends Fragment {
    private NavController navController;
    private LoginPresenter presenter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        presenter = new LoginPresenter();
        instantiateListeners(view);
    }

    private void instantiateListeners(View view) {
        Button loginToSigninBtn = view.findViewById(R.id.btn_navigate_signin);
        Button loginToUsersBtn = view.findViewById(R.id.btn_navigate_users);

        TextInputEditText usernameBox = view.findViewById(R.id.username_login);
        TextInputEditText passwordBox = view.findViewById(R.id.password_login);

        loginToSigninBtn.setOnClickListener(
                v -> {
                    navController.navigate(R.id.action_loginFragment_to_signinFragment);
                }
        );
        loginToUsersBtn.setOnClickListener(v -> {
            try {
                if(presenter.loginUser(
                        usernameBox.getText().toString(),
                        passwordBox.getText().toString(),
                        UserRepository.getUserRepository(getContext()),
                        new CacheManager(getContext())
                )) {
                    navController.navigate(R.id.action_loginFragment_to_commandFragment);
                }
            } catch (Exception e) {
                //TODO ALERT BOX
            }
        });
    }
}
