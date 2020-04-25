package com.example.smsrc.users.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.smsrc.MainActivity;
import com.example.smsrc.R;
import com.example.smsrc.users.dals.UserRepository;
import com.example.smsrc.users.presenters.UsersPresenter;
import com.example.smsrc.users.views.ui.UsersAdapter;


public class UsersListFragment extends Fragment {
    private NavController navController;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private UsersPresenter usersPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_users_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        usersPresenter = new UsersPresenter(UserRepository.getUserRepository(this.getContext()));
        navController = Navigation.findNavController(view);

        recyclerView = view.findViewById(R.id.users_list_recycler_view);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new UsersAdapter(usersPresenter.getAllUsers(), navController);
        recyclerView.setAdapter(mAdapter);
    }
}
