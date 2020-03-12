package com.example.smsrc;

import android.content.Context;
import com.example.smsrc.db.DBRunner;
import com.example.smsrc.permissions.models.Authenticate;
import com.example.smsrc.permissions.models.Authorize;
import com.example.smsrc.users.dals.UserRepository;
import com.example.smsrc.users.models.User;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.lang.reflect.Field;

import androidx.room.Room;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@RunWith(AndroidJUnit4.class)
public class AuthTest {

    private Context appContext;
    private DBRunner db;
    private User user;

    @Before
    public void beforeTestRun() throws NoSuchFieldException, IllegalAccessException {
        appContext = InstrumentationRegistry.getInstrumentation().getTargetContext().getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(appContext, DBRunner.class).build();
        UserRepository repository = new UserRepository(appContext);

        Field repoDb = repository.getClass().getDeclaredField("dbRunner");
        repoDb.setAccessible(true);
        repoDb.set(repository, db);

        user = new User("my name", "123456", "delete");
        repository.insert( user.getUsername(), user.getPasscode(), user.getAuthLevel());
    }

    @Test
    public void testSuccessfulAuthentication() {
        Authenticate authenticate = new Authenticate(appContext);
        authenticate.authenticate(user.getUsername(), user.getPasscode());
    }

    @Test
    public void testFailedAuthenticationWrongPassword() {
        try {
            Authenticate authenticate = new Authenticate(appContext);
            authenticate.authenticate(user.getUsername(), "12345");
            Assert.fail();
        } catch (RuntimeException e) {
            assertEquals("Invalid Credentials", e.getMessage());
        }
    }

    @Test
    public void testFailedAuthenticationWrongUsername() {
        try {
            Authenticate authenticate = new Authenticate(appContext);
            authenticate.authenticate("my nam", user.getPasscode());
            Assert.fail();
        } catch (RuntimeException e) {
            assertEquals("User doesn't exist", e.getMessage());
        }
    }

    @Test
    public void testAllAuthorizationCases(){
        Authorize authorize = new Authorize();
        User user1 = new User(user.getUsername(), user.getPasscode(), "add");
        User user2 = new User(user.getUsername(), user.getPasscode(), "edit");
        assertTrue(authorize.authorize(user, "delete"));
        assertFalse(authorize.authorize(user2, "delete"));
        assertFalse(authorize.authorize(user1, "delete"));
        assertTrue(authorize.authorize(user, "edit"));
        assertTrue(authorize.authorize(user2, "edit"));
        assertFalse(authorize.authorize(user1, "edit"));
        assertTrue(authorize.authorize(user, "add"));
        assertTrue(authorize.authorize(user2, "add"));
        assertTrue(authorize.authorize(user1, "add"));
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

    @After
    public void afterTestRun() {
        db.close();
    }
}


