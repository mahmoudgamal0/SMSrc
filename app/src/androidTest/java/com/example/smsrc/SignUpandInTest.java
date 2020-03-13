package com.example.smsrc;


import androidx.test.platform.app.InstrumentationRegistry;

import com.example.smsrc.login.presenter.LoginPresenter;
import com.example.smsrc.permissions.models.Authenticate;
import com.example.smsrc.permissions.models.Authorize;
import com.example.smsrc.permissions.utils.Crypto;
import com.example.smsrc.signin.presenter.SigninPresenter;
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
public class SignUpandInTest {

    @Mock
    private UserRepository repository;

    @Mock
    private Authenticate authenticate;

    private ArrayList<User> correctUserList;
    private User user;
    private LoginPresenter loginPresenter;
    private SigninPresenter signUpPresenter;
    @Before
    public void beforeTestRun() {
        String encryptedPass = Crypto.encrypt("123456");
        user = new User("my name", encryptedPass, "owner");
        correctUserList = new ArrayList<>();
        correctUserList.add(user);
        loginPresenter = new LoginPresenter();
        signUpPresenter = new SigninPresenter();

    }

    @Test
    public void testSuccessfulLogIn() {
        when(repository.getUserByUsername(user.getUsername())).thenReturn(correctUserList);
        boolean test = loginPresenter.loginUser(user.getUsername(), "123456", repository);
        assertTrue(test);
    }

    @Test
    public void testFailedLogInWrongPassword() {
        try {
            when(repository.getUserByUsername(user.getUsername())).thenReturn(correctUserList);
            boolean test = loginPresenter.loginUser(user.getUsername(), user.getPasscode(), repository);
            Assert.fail();
        } catch (RuntimeException e) {
            assertEquals("Invalid Credentials", e.getMessage());
        }
    }

    @Test
    public void testSuccessfulSignUp() {
        when(authenticate.authenticate(user.getUsername(),"123456")).thenReturn(true);
        try {
            assertTrue(
                    signUpPresenter.signUpUser(
                    user.getUsername(),
                    "123456",
                    "123456",
                    repository,
                    authenticate));
        } catch (Exception e) {

            Assert.fail(e.getMessage());
        }
    }


    @Test
    public void testFailedSignUpPasswordMismatch() {
        try {
            when(repository.getUserByUsername(user.getUsername())).thenReturn(correctUserList);

            signUpPresenter.signUpUser("test",
                    "456",
                    "123",
                    repository,
                    new Authenticate(repository));
            Assert.fail();

        } catch (Exception e) {
            assertEquals("Password mismatch", e.getMessage());
        }
    }

    @Test
    public void testFailedSignUpAlreadyExistingUser() {
        try {
            when(repository.getUserByUsername(user.getUsername())).thenReturn(correctUserList);

            signUpPresenter.signUpUser(
                    user.getUsername(),
                    "456",
                    "456",
                    repository,
                    new Authenticate(repository));
            Assert.fail();

        } catch (Exception e) {
            assertEquals("Username already exists", e.getMessage());
        }
    }

}




