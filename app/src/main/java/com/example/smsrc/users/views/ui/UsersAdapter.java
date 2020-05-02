package com.example.smsrc.users.views.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.smsrc.R;
import com.example.smsrc.users.models.User;
import com.example.smsrc.users.presenters.UsersPresenter;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder> {

    private List<User> users;
    private AdapterPayload payload;

    public UsersAdapter(List<User> users, AdapterPayload payload){
        this.users = users;
        this.payload = payload;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ConstraintLayout v = (ConstraintLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_user, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TextView userNameTextView = holder.content.findViewById(R.id.user_name);
        TextView userRoleTextView = holder.content.findViewById(R.id.user_role_spinner);
        Button editButton = holder.content.findViewById(R.id.user_edit);
        Button deleteButton = holder.content.findViewById(R.id.user_delete);

        User user = users.get(position);
        userNameTextView.setText("Username: " + user.getUsername());
        userRoleTextView.setText("UserRole: " + user.getAuthLevel());

        Bundle bundle = new Bundle();
        bundle.putSerializable("user", user);

        if(payload.canEdit && !user.getUsername().equals(payload.admin.getUsername())){
            editButton.setVisibility(View.VISIBLE);
            editButton.setOnClickListener(e -> payload.navController.navigate(R.id.action_usersListFragment_to_userEditFragment, bundle));
        }

        if(payload.canDelete && !user.getUsername().equals(payload.admin.getUsername())) {
            deleteButton.setVisibility(View.VISIBLE);
            deleteButton.setOnClickListener(e -> {
                payload.presenter.deleteUser(user.getUsername());
                payload.navController.popBackStack();
                payload.navController.navigate(R.id.action_homeRouterFragment_to_usersListFragment);
            });
        }
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ConstraintLayout content;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            content = (ConstraintLayout) itemView;
        }
    }


    public static class AdapterPayload{
        private NavController navController;
        private User admin;
        private boolean canEdit;
        private boolean canDelete;
        private UsersPresenter presenter;

        public void setNavController(NavController navController) {
            this.navController = navController;
        }

        public void setAdmin(User admin) {
            this.admin = admin;
        }

        public void setCanEdit(boolean canEdit) {
            this.canEdit = canEdit;
        }

        public void setCanDelete(boolean canDelete) {
            this.canDelete = canDelete;
        }

        public void setPresenter(UsersPresenter presenter) {
            this.presenter = presenter;
        }
    }

}
