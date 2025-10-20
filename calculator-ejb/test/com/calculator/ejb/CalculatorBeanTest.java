package com.calculator.ejb;

import org.junit.Before;
import org.junit.Test;
import org.junit.After;
import static org.junit.Assert.*;

import java.rmi.RemoteException;

/**
 * Unit tests for CalculatorBean
 * Tests business logic without requiring EJB container
 */
public class CalculatorBeanTest {
    
    private CalculatorBean calculator;
    
    @Before
    public void setUp() {
        calculator = new CalculatorBean();
        calculator.ejbCreate();
    }
    
    @After
    public void tearDown() {
        calculator.ejbRemove();
        calculator = null;
    }
    
    @Test
    public void testAddPositiveNumbers() {
        double result = calculator.add(10.0, 5.0);
        assertEquals(15.0, result, 0.0001);
    }
    
    @Test
    public void testAddNegativeNumbers() {
        double result = calculator.add(-10.0, -5.0);
        assertEquals(-15.0, result, 0.0001);
    }
    
    @Test
    public void testAddMixedNumbers() {
        double result = calculator.add(10.0, -5.0);
        assertEquals(5.0, result, 0.0001);
    }
    
    @Test
    public void testAddZero() {
        double result = calculator.add(10.0, 0.0);
        assertEquals(10.0, result, 0.0001);
    }
    
    @Test
    public void testAddDecimals() {
        double result = calculator.add(10.5, 5.3);
        assertEquals(15.8, result, 0.0001);
    }
    
    @Test
    public void testSubtractPositiveNumbers() {
        double result = calculator.subtract(10.0, 5.0);
        assertEquals(5.0, result, 0.0001);
    }
    
    @Test
    public void testSubtractNegativeNumbers() {
        double result = calculator.subtract(-10.0, -5.0);
        assertEquals(-5.0, result, 0.0001);
    }
    
    @Test
    public void testSubtractResultingNegative() {
        double result = calculator.subtract(5.0, 10.0);
        assertEquals(-5.0, result, 0.0001);
    }
    
    @Test
    public void testSubtractZero() {
        double result = calculator.subtract(10.0, 0.0);
        assertEquals(10.0, result, 0.0001);
    }
    
    @Test
    public void testSubtractDecimals() {
        double result = calculator.subtract(10.5, 5.3);
        assertEquals(5.2, result, 0.0001);
    }
    
    @Test
    public void testMultiplyPositiveNumbers() {
        double result = calculator.multiply(10.0, 5.0);
        assertEquals(50.0, result, 0.0001);
    }
    
    @Test
    public void testMultiplyNegativeNumbers() {
        double result = calculator.multiply(-10.0, -5.0);
        assertEquals(50.0, result, 0.0001);
    }
    
    @Test
    public void testMultiplyMixedNumbers() {
        double result = calculator.multiply(-10.0, 5.0);
        assertEquals(-50.0, result, 0.0001);
    }
    
    @Test
    public void testMultiplyByZero() {
        double result = calculator.multiply(10.0, 0.0);
        assertEquals(0.0, result, 0.0001);
    }
    
    @Test
    public void testMultiplyByOne() {
        double result = calculator.multiply(10.0, 1.0);
        assertEquals(10.0, result, 0.0001);
    }
    
    @Test
    public void testMultiplyDecimals() {
        double result = calculator.multiply(10.5, 2.0);
        assertEquals(21.0, result, 0.0001);
    }
    
    @Test
    public void testDividePositiveNumbers() throws RemoteException {
        double result = calculator.divide(10.0, 5.0);
        assertEquals(2.0, result, 0.0001);
    }
    
    @Test
    public void testDivideNegativeNumbers() throws RemoteException {
        double result = calculator.divide(-10.0, -5.0);
        assertEquals(2.0, result, 0.0001);
    }
    
    @Test
    public void testDivideMixedNumbers() throws RemoteException {
        double result = calculator.divide(-10.0, 5.0);
        assertEquals(-2.0, result, 0.0001);
    }
    
    @Test
    public void testDivideByOne() throws RemoteException {
        double result = calculator.divide(10.0, 1.0);
        assertEquals(10.0, result, 0.0001);
    }
    
    @Test
    public void testDivideDecimals() throws RemoteException {
        double result = calculator.divide(10.5, 2.0);
        assertEquals(5.25, result, 0.0001);
    }
    
    @Test
    public void testDivideResultingInFraction() throws RemoteException {
        double result = calculator.divide(10.0, 3.0);
        assertEquals(3.3333, result, 0.0001);
    }
    
    @Test(expected = RemoteException.class)
    public void testDivideByZero() throws RemoteException {
        calculator.divide(10.0, 0.0);
    }
    
    @Test
    public void testDivideByZeroMessage() {
        try {
            calculator.divide(10.0, 0.0);
            fail("Expected RemoteException to be thrown");
        } catch (RemoteException e) {
            assertEquals("Division by zero is not allowed", e.getMessage());
        }
    }
    
    @Test
    public void testLargeNumbers() {
        double result = calculator.add(1000000.0, 2000000.0);
        assertEquals(3000000.0, result, 0.0001);
    }
    
    @Test
    public void testVerySmallDecimals() {
        double result = calculator.add(0.0001, 0.0002);
        assertEquals(0.0003, result, 0.00001);
    }
    
    @Test
    public void testChainedOperations() throws RemoteException {
        double result = calculator.add(10.0, 5.0);
        result = calculator.multiply(result, 2.0);
        result = calculator.subtract(result, 10.0);
        result = calculator.divide(result, 4.0);
        assertEquals(5.0, result, 0.0001);
    }
}
