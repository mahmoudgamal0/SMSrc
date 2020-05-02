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

import com.example.smsrc.HomeActivity;
import com.example.smsrc.MainActivity;
import com.example.smsrc.R;
import com.example.smsrc.auth.models.Authorize;
import com.example.smsrc.auth.utils.AuthRoles;
import com.example.smsrc.users.dals.UserRepository;
import com.example.smsrc.users.models.User;
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

        Authorize authorize = new Authorize();
        User currentUser = ((HomeActivity)this.getActivity()).getCurrentUser();

        // Check Edit
        boolean canEdit = authorize.authorizeManagement(currentUser, AuthRoles.EDIT);
        // Check Delete
        boolean canDelete = authorize.authorizeManagement(currentUser, AuthRoles.DELETE);

        usersPresenter = new UsersPresenter(UserRepository.getUserRepository(this.getContext()));
        navController = Navigation.findNavController(view);

        recyclerView = view.findViewById(R.id.users_list_recycler_view);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(layoutManager);

        UsersAdapter.AdapterPayload payload = new UsersAdapter.AdapterPayload();
        payload.setAdmin(currentUser);
        payload.setCanEdit(canEdit);
        payload.setCanDelete(canDelete);
        payload.setNavController(navController);
        payload.setPresenter(usersPresenter);

        mAdapter = new UsersAdapter(usersPresenter.getAllUsers(), payload);
        recyclerView.setAdapter(mAdapter);
    }
}
