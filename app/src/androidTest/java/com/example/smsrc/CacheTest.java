package com.example.smsrc;


import androidx.test.platform.app.InstrumentationRegistry;

import com.example.smsrc.cache.CacheManager;
import com.example.smsrc.permissions.models.Authenticate;
import com.example.smsrc.users.models.User;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CacheTest {


    private User user;
    private CacheManager cacheManager;

    @Before
    public void beforeTestRun() {
        user = new User("my name", "123456", "delete");
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



}
