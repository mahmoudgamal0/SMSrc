package com.example.smsrc;


import com.example.smsrc.users.dals.UserRepository;
import com.example.smsrc.users.models.User;
import com.example.smsrc.users.presenters.UsersPresenter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import java.util.ArrayList;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserPresenterTest {

    @Mock
    private UserRepository repository;
    private ArrayList<User> allUsers;
    @Before
    public void beforeTestRun(){
        User user1 = new User("helloworld", "123456", "owner");
        User user2 = new User("hellothere", "123456", "level 1 guest");

        allUsers = new ArrayList<>();
        allUsers.add(user1);
        allUsers.add(user2);
    }

    @Test
    public void testGetAllUsers(){
        UsersPresenter presenter = new UsersPresenter(repository);
        when(repository.getAllUsers()).thenReturn(allUsers);
        assertEquals(allUsers, presenter.getAllUsers());
    }

    @Test
    public void testUpdateUserInfo(){
        UsersPresenter presenter = new UsersPresenter(repository);
        presenter.updateUser(1, "helloworld", "owner");
        verify(repository, times(1)).updateUserInfo(1, "helloworld", "owner");
    }
}
