package com.meiriq.xposehook;

import android.util.Log;

import junit.framework.TestCase;

/**
 * Created by tian on 16-1-27.
 */
public class CalculatorTest extends TestCase {

    Calculator calculator;
    @Override
    protected void setUp() throws Exception {
        calculator = new Calculator();
    }

    public void testSum() throws Exception {
//        calculator = new Calculator();
        assertEquals(4,calculator.sum(1,1),2);

//        Log.d("unlock","dd");
    }

    public void testSubstract() throws Exception {

    }
}