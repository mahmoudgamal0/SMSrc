package com.example.smsrc.signin.views.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.smsrc.HomeActivity;
import com.example.smsrc.R;
import com.example.smsrc.cache.CacheManager;
import com.example.smsrc.permissions.models.Authenticate;
import com.example.smsrc.signin.presenter.SigninPresenter;
import com.example.smsrc.users.dals.UserRepository;
import com.google.android.material.textfield.TextInputEditText;


public class SigninFragment extends Fragment {
    private NavController navController;
    private SigninPresenter presenter;
    private CacheManager cacheManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_signin, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        presenter = new SigninPresenter();
        cacheManager = new CacheManager(this.getContext());
        if(!cacheManager.isUserCached()){
            instantiateListeners(view);
        } else {
            startHomeActivity();
        }
    }

    private void instantiateListeners(View view) {
        Button signinToLoginBtn = view.findViewById(R.id.btn_navigate_signin);
        Button signinToUsersBtn = view.findViewById(R.id.btn_navigate_users);

        TextInputEditText usernameBox = view.findViewById(R.id.username_signup);
        TextInputEditText passwordBox = view.findViewById(R.id.password_signup);
        TextInputEditText confirmPasswordBox = view.findViewById(R.id.confirm_password_signup);

        signinToLoginBtn.setOnClickListener(v -> navController.navigate(R.id.action_signinFragment_to_loginFragment));
        signinToUsersBtn.setOnClickListener(v -> {
            try {

                Log.i("SigninFragment", "Sign up attempt"); //TODO permission name

                if(presenter.signUpUser(
                        usernameBox.getText().toString(),
                        passwordBox.getText().toString(),
                        confirmPasswordBox.getText().toString(),
                        UserRepository.getUserRepository(getContext()),
                        new Authenticate(UserRepository.getUserRepository(getContext())),
                        new CacheManager(getContext()))
                ) {
                    startHomeActivity();
                }
            } catch (Exception e) {
                Toast.makeText(getContext() ,e.getMessage(),Toast.LENGTH_LONG).show();
                Log.e("SigninFragment", e.getMessage());
            }
        });
    }

    private void startHomeActivity(){
        startActivity(new Intent(this.getContext(), HomeActivity.class));
    }
}
