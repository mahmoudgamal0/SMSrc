package com.example.smsrc.users.presenters;

import com.example.smsrc.users.dals.UserRepository;
import com.example.smsrc.users.models.User;

import java.util.List;

public class UsersPresenter {

    private UserRepository repository;

    public UsersPresenter(UserRepository repository){
        this.repository = repository;
    }

    public void updateUser(int id, String username, String authLevel) {
        repository.updateUserInfo(id, username, authLevel);
    }

    public List<User> getAllUsers() {
        return repository.getAllUsers();
    }

    public User getUserByUsername(String username) {
        List<User> users = repository.getUserByUsername(username);
        if(users.size() == 0)
            return null;
        return users.get(0);
    }

    public void deleteUser(String username) {
        repository.deleteUser(username);
    }
}
