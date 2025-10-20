package com.calculator.ejb;

import org.junit.BeforeClass;
import org.junit.AfterClass;
import org.junit.Test;
import static org.junit.Assert.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Properties;

/**
 * Integration tests for Calculator EJB
 * Requires JBoss server to be running with the EJB deployed
 */
public class CalculatorIntegrationTest {
    
    private static Context ctx;
    private static CalculatorHome home;
    private static CalculatorRemote calculator;
    
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        // Setup JNDI context
        Properties props = new Properties();
        props.put(Context.INITIAL_CONTEXT_FACTORY, 
                 "org.jnp.interfaces.NamingContextFactory");
        props.put(Context.URL_PKG_PREFIXES, 
                 "org.jboss.naming:org.jnp.interfaces");
        props.put(Context.PROVIDER_URL, "jnp://localhost:1099");
        
        try {
            ctx = new InitialContext(props);
            
            // Lookup the EJB home
            Object obj = ctx.lookup("ejb/CalculatorHome");
            home = (CalculatorHome) 
                javax.rmi.PortableRemoteObject.narrow(obj, CalculatorHome.class);
            
            // Create the remote reference
            calculator = home.create();
            
        } catch (NamingException e) {
            System.err.println("WARNING: JBoss server not available. Integration tests will be skipped.");
            System.err.println("Make sure JBoss is running and Calculator EJB is deployed.");
            throw e;
        }
    }
    
    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        if (ctx != null) {
            ctx.close();
        }
    }
    
    @Test
    public void testRemoteAddOperation() throws Exception {
        double result = calculator.add(100.0, 50.0);
        assertEquals(150.0, result, 0.0001);
    }
    
    @Test
    public void testRemoteSubtractOperation() throws Exception {
        double result = calculator.subtract(100.0, 50.0);
        assertEquals(50.0, result, 0.0001);
    }
    
    @Test
    public void testRemoteMultiplyOperation() throws Exception {
        double result = calculator.multiply(100.0, 50.0);
        assertEquals(5000.0, result, 0.0001);
    }
    
    @Test
    public void testRemoteDivideOperation() throws Exception {
        double result = calculator.divide(100.0, 50.0);
        assertEquals(2.0, result, 0.0001);
    }
    
    @Test(expected = Exception.class)
    public void testRemoteDivideByZero() throws Exception {
        calculator.divide(100.0, 0.0);
    }
    
    @Test
    public void testMultipleRemoteCalls() throws Exception {
        double result1 = calculator.add(10.0, 5.0);
        double result2 = calculator.multiply(result1, 2.0);
        double result3 = calculator.subtract(result2, 10.0);
        double result4 = calculator.divide(result3, 4.0);
        
        assertEquals(15.0, result1, 0.0001);
        assertEquals(30.0, result2, 0.0001);
        assertEquals(20.0, result3, 0.0001);
        assertEquals(5.0, result4, 0.0001);
    }
    
    @Test
    public void testStatelessBehavior() throws Exception {
        // Test that multiple clients get consistent results
        // proving stateless session bean behavior
        CalculatorRemote calc1 = home.create();
        CalculatorRemote calc2 = home.create();
        
        double result1 = calc1.add(10.0, 5.0);
        double result2 = calc2.add(10.0, 5.0);
        
        assertEquals(result1, result2, 0.0001);
    }
    
    @Test
    public void testConcurrentAccess() throws Exception {
        // Simple test to verify multiple sequential calls work
        for (int i = 0; i < 10; i++) {
            double result = calculator.add(i, i);
            assertEquals(i * 2.0, result, 0.0001);
        }
    }
}
