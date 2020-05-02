package com.example.smsrc.users.views.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.smsrc.R;
import com.example.smsrc.users.models.User;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder> {

    private List<User> users;
    private NavController navController;
    private User admin;
    private boolean canEdit;
    private boolean canDelete;

    public UsersAdapter(List<User> users, NavController navController, User admin, boolean canEdit, boolean canDelete){
        this.users = users;
        this.navController = navController;
        this.admin = admin;
        this.canEdit = canEdit;
        this.canDelete = canDelete;
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

        if(canEdit && !user.getUsername().equals(admin.getUsername())){
            editButton.setVisibility(View.VISIBLE);
            editButton.setOnClickListener(e -> navController.navigate(R.id.action_usersListFragment_to_userEditFragment, bundle));
        }

        if(canDelete && !user.getUsername().equals(admin.getUsername())) {
            deleteButton.setVisibility(View.VISIBLE);
            // DO action
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



}
