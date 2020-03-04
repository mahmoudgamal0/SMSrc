package com.example.smsrc.users.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.smsrc.R;


public class UsersListFragment extends Fragment {
    private NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_users_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        instantiateListeners(view);
    }

    private void instantiateListeners(View view) {
        Button usersListToEdit = view.findViewById(R.id.btn_navigate_edit);
        usersListToEdit.setOnClickListener(v -> navController.navigate(R.id.action_usersListFragment_to_userEditFragment));
    }
}
