package com.example.smsrc.commands.views.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.smsrc.MainActivity;
import com.example.smsrc.R;
import com.example.smsrc.commands.model.CommandsContract;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class CommandFragment extends Fragment implements BottomNavigationView.OnNavigationItemSelectedListener{

    private NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_command, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        MainActivity mainActivity = (MainActivity)this.getActivity();
        mainActivity.showNavigation();
        BottomNavigationView navigationView = mainActivity.findViewById(R.id.home_bottom_nav);
        navigationView.setOnNavigationItemSelectedListener(this);
        initListeners(view);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.nav_command:
                if(!navController.getCurrentDestination().getLabel().equals("fragment_command"))
                    navController.navigate(R.id.action_usersListFragment_to_commandFragment);
                break;
            case R.id.nav_users:
                if(navController.getCurrentDestination().getLabel().equals("fragment_command"))
                    navController.navigate(R.id.action_commandFragment_to_usersListFragment);
                break;
        }
        return true;
    }

    private void initListeners(View view) {
        Button playSoundBtn = view.findViewById(R.id.play_sound_button);
        playSoundBtn.setOnClickListener(e->{
            Bundle bundle = new Bundle();
            bundle.putString("command", CommandsContract.PLAY_SOUND);
            navController.navigate(R.id.action_commandFragment_to_outboundInformationFragment, bundle);
        });
    }
}
