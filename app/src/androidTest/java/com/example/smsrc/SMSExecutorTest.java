package com.example.smsrc;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;

import com.example.smsrc.commands.interfaces.CommandsContract;
import com.example.smsrc.permissions.utils.Crypto;
import com.example.smsrc.sms.handlers.SMSExecutor;
import com.example.smsrc.sms.model.SMS;
import com.example.smsrc.users.dals.UserRepository;
import com.example.smsrc.users.models.User;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)

public class SMSExecutorTest {


    private SMSExecutor executor;
    private User user;
    private User user1;
    private User user2;

    @Mock
    private UserRepository repository;
    private Context context;
    private ArrayList<User> correctUserList;

    @Before
    public void beforeTestRun() {

        user1 = new User("helloworld", "123456", "owner");
        user2 = new User("hellothere", "123456", "level 1 guest");

        context = InstrumentationRegistry.getInstrumentation().getContext();

        correctUserList = new ArrayList<>();
        correctUserList.add(user1);
        correctUserList.add(user2);

        user = new User("my name", "123456", "owner");
        executor = new SMSExecutor();
        executor.setRepository(repository);
    }
    @Test
    public void testNonExistingUser() {
        when(repository.getAllUsers()).thenReturn(correctUserList);
        try {
            SMS s = new SMS(
                    Crypto.encrypt(user.getUsername() + "test" + user.getPasscode()),
                    Crypto.encrypt(CommandsContract.PLAY_SOUND + "test"),
                    "test"
            );
            executor.execute(s);
            Assert.fail("executed successfully");
        } catch (Exception e) {
            assertEquals("Credentials not found", e.getMessage());
        }

    }

    @Test
    public void testSuccessfulAuthorizedUser() {
        when(repository.getAllUsers()).thenReturn(correctUserList);
        try {
            SMS s = new SMS(
                    Crypto.encrypt(user1.getUsername() + "test" + user1.getPasscode()),
                    Crypto.encrypt(CommandsContract.PLAY_SOUND + "test"),
                    "test"
            );
            executor.setRepository(repository);
            executor.setContext(context);
            executor.execute(s);
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void testNonExistingCommand() {
        when(repository.getAllUsers()).thenReturn(correctUserList);
        try {
            SMS s = new SMS(
                    Crypto.encrypt(user1.getUsername() + "test" + user1.getPasscode()),
                    Crypto.encrypt("Non existing command"),
                    "test"
            );
            executor.execute(s);
            Assert.fail("ran a non existing command");
        } catch (Exception e) {
            assertEquals("No such command", e.getMessage());
        }
    }

    @Test
    public void testNotAuthorizedCommand() {
        when(repository.getAllUsers()).thenReturn(correctUserList);
        try {
            SMS s = new SMS(
                    Crypto.encrypt(user2.getUsername() + "test" + user2.getPasscode()),
                    Crypto.encrypt(CommandsContract.LOCK_PHONE + "test"),
                    "test"
            );
            executor.execute(s);
            Assert.fail("ran a non authorized command");
        } catch (Exception e) {
            assertEquals("UnAuthorized command", e.getMessage());
        }
    }




}
