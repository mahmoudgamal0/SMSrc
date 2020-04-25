package com.example.smsrc;

import com.example.smsrc.pin.utils.PinUtils;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import androidx.test.platform.app.InstrumentationRegistry;


@RunWith(JUnit4.class)
public class PinUtilsTest {

    private PinUtils pinUtils;

    @Before
    public void before(){
        pinUtils = new PinUtils(InstrumentationRegistry.getInstrumentation().getContext(), false);
    }

    @Test
    public void testEmptyOld() {
        Assert.assertFalse(pinUtils.checkPins("", "1234", "1234", "1234"));
    }

    @Test
    public void testWrongOld() {
        Assert.assertFalse(pinUtils.checkPins("1235", "1234", "1234", "1234"));
    }

    @Test
    public void testNoCacheWithOld() {
        Assert.assertTrue(pinUtils.checkPins("1235", null, "1234", "1234"));
    }

    @Test
    public void testNoCacheNewPinSuccess() {
        Assert.assertTrue(pinUtils.checkPins("", null, "1234", "1234"));
    }

    @Test
    public void testNoCacheNewPinFailConfirmation() {
        Assert.assertFalse(pinUtils.checkPins("", null, "1234", "1235"));
    }

    @Test
    public void testNoCacheNewPinFailEmpty() {
        Assert.assertFalse(pinUtils.checkPins("", null, "", "1235"));
    }

    @Test
    public void testCacheNewPinSuccess() {
        Assert.assertTrue(pinUtils.checkPins("1235", "1235", "1234", "1234"));
    }

    @Test
    public void testCacheNewPinFailConfirmation() {
        Assert.assertFalse(pinUtils.checkPins("1235", "1235", "1234", "1233"));
    }

    @Test
    public void testCacheNewPinFailEmpty() {
        Assert.assertFalse(pinUtils.checkPins("1235", "1235", "", "1233"));
    }

    @Test
    public void testCacheNewPinFailLength() {
        Assert.assertFalse(pinUtils.checkPins("1235", "1235", "123", "123"));
    }
}


