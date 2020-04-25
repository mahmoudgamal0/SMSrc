package com.example.smsrc.users.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import com.example.smsrc.R;
import com.example.smsrc.permissions.utils.AuthRoles;
import com.example.smsrc.users.dals.UserRepository;
import com.example.smsrc.users.models.User;
import com.example.smsrc.users.presenters.UsersPresenter;
import com.google.android.material.textfield.TextInputEditText;


public class UserEditFragment extends Fragment {
    private UsersPresenter usersPresenter;
    private NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_edit, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        usersPresenter = new UsersPresenter(UserRepository.getUserRepository(this.getContext()));
        navController = Navigation.findNavController(view);

        Bundle bundle = getArguments();
        User user = (User) bundle.getSerializable("user");

        TextInputEditText userName = view.findViewById(R.id.user_name);
        Spinner userRole = view.findViewById(R.id.user_role_spinner);
        Button saveBtn = view.findViewById(R.id.save_btn);

        String[] items = AuthRoles.getRoles();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_dropdown_item, items);
        userRole.setAdapter(adapter);

        userName.setText(user.getUsername());
        userRole.setSelection(java.util.Arrays.asList(items).indexOf(user.getAuthLevel()));

        saveBtn.setOnClickListener(v -> {
            usersPresenter.updateUser(
                    user.getId(),
                    userName.getText().toString(),
                    userRole.getSelectedItem().toString()
            );
            Toast.makeText(this.getContext(), "Saved successfully", Toast.LENGTH_SHORT).show();
            navController.navigateUp();
        });
    }
}
