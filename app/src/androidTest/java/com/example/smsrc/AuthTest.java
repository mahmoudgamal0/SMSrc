package com.example.smsrc;

import com.example.smsrc.commands.model.CommandsContract;
import com.example.smsrc.permissions.models.Authenticate;
import com.example.smsrc.permissions.models.Authorize;
import com.example.smsrc.permissions.utils.Crypto;
import com.example.smsrc.users.dals.UserRepository;
import com.example.smsrc.users.models.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import java.util.ArrayList;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AuthTest {

    @Mock
    private UserRepository repository;
    private ArrayList<User> correctUserList;
    private User user;

    @Before
    public void beforeTestRun() {
        user = new User("my name", Crypto.encrypt("123456"), "owner");
        correctUserList = new ArrayList<>();
        correctUserList.add(user);
    }

    @Test
    public void testSuccessfulAuthentication() {
        when(repository.getUserByUsername(user.getUsername())).thenReturn(correctUserList);
        Authenticate authenticate = new Authenticate(repository);
        authenticate.authenticate(user.getUsername(), "123456");
    }

    @Test
    public void testFailedAuthenticationWrongPassword() {
        try {
            when(repository.getUserByUsername(user.getUsername())).thenReturn(correctUserList);
            Authenticate authenticate = new Authenticate(repository);
            authenticate.authenticate(user.getUsername(), "12345");
            Assert.fail();
        } catch (RuntimeException e) {
            assertEquals("Invalid Credentials", e.getMessage());
        }
    }

    @Test
    public void testFailedAuthenticationWrongUsername() {
        try {
            when(repository.getUserByUsername(user.getUsername())).thenReturn(new ArrayList<>());
            Authenticate authenticate = new Authenticate(repository);
            authenticate.authenticate("my nam", user.getPasscode());
            Assert.fail();
        } catch (RuntimeException e) {
            assertEquals("User doesn't exist", e.getMessage());
        }
    }

    @Test
    public void testAllAuthorizationCases(){
        Authorize authorize = new Authorize();
        User user1 = new User(user.getUsername(), user.getPasscode(), "level 1 guest");
        User user2 = new User(user.getUsername(), user.getPasscode(), "level 2 guest");
        assertTrue(authorize.authorize(user, CommandsContract.CHANGE_PIN_CODE));
        assertFalse(authorize.authorize(user2, CommandsContract.CHANGE_PIN_CODE));
        assertFalse(authorize.authorize(user1, CommandsContract.CHANGE_PIN_CODE));
        assertTrue(authorize.authorize(user, CommandsContract.PLAY_SOUND));
        assertTrue(authorize.authorize(user1, CommandsContract.PLAY_SOUND));
        assertFalse(authorize.authorize(user2, CommandsContract.PLAY_SOUND));
    }

    @Test
    public void testInvalidAuthorization(){
        Authorize authorize = new Authorize();
        User user1 = new User(user.getUsername(), user.getPasscode(), "rubbish");
        try {
            authorize.authorize(user1, "add");
            Assert.fail();
        } catch (RuntimeException e) {
            assertEquals("Invalid Auth Role for user", e.getMessage());
        }
    }
}


