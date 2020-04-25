package com.example.smsrc;


import androidx.test.platform.app.InstrumentationRegistry;

import com.example.smsrc.cache.CacheManager;
import com.example.smsrc.permissions.models.Authenticate;
import com.example.smsrc.users.models.User;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CacheTest {


    private User user;
    private User user2;
    private CacheManager cacheManager;

    @Before
    public void beforeTestRun() {
        user = new User("my name", "123456", "delete");
        user2 = new User("my new name", "123456", "delete");
        cacheManager = new CacheManager(InstrumentationRegistry.getInstrumentation().getContext());
        cacheManager.clearCache();

    }
    @Test
    public void testSuccessfulCache() {
        try {
            assertFalse(cacheManager.isUserCached());
            cacheManager.cacheUser(user.getUsername());
            assertTrue(cacheManager.isUserCached());
            cacheManager.clearCache();
            assertFalse(cacheManager.isUserCached());
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }


    @Test
    public void testTrueSameUser() {
        cacheManager.cacheUser(user.getUsername());
        assertEquals(cacheManager.getCachedUser(), user.getUsername());
        assertNotEquals(cacheManager.getCachedUser(), "my nam");
    }

    @Test
    public void testAddingPin() {
        cacheManager.cacheUser(user.getUsername());
        cacheManager.cachePin("1234");
        assertEquals("1234", cacheManager.getCachedPin());
        cacheManager.cacheUser(user2.getUsername());
        assertNull(cacheManager.getCachedPin());
        cacheManager.cacheUser(user.getUsername());
        assertEquals("1234", cacheManager.getCachedPin());
    }

    @Test
    public void testPinPersistWhenUserLogout(){
        cacheManager.cacheUser(user.getUsername());
        cacheManager.cachePin("1234");
        cacheManager.cacheUser(user2.getUsername());
        cacheManager.cacheUser(user.getUsername());
        assertEquals("1234", cacheManager.getCachedPin());
    }

    @After
    public void afterTestRun(){
        cacheManager.clearUser();
    }

}
