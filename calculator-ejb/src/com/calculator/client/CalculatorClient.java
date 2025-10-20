package com.calculator.client;

import com.calculator.ejb.CalculatorHome;
import com.calculator.ejb.CalculatorRemote;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Properties;

/**
 * Simple client to test the Calculator EJB
 */
public class CalculatorClient {
    
    public static void main(String[] args) {
        try {
            // Setup JNDI context
            Properties props = new Properties();
            props.put(Context.INITIAL_CONTEXT_FACTORY, 
                     "org.jnp.interfaces.NamingContextFactory");
            props.put(Context.URL_PKG_PREFIXES, 
                     "org.jboss.naming:org.jnp.interfaces");
            props.put(Context.PROVIDER_URL, "jnp://localhost:1099");
            
            Context ctx = new InitialContext(props);
            
            // Lookup the EJB home
            Object obj = ctx.lookup("ejb/CalculatorHome");
            CalculatorHome home = (CalculatorHome) 
                javax.rmi.PortableRemoteObject.narrow(obj, CalculatorHome.class);
            
            // Create the remote reference
            CalculatorRemote calc = home.create();
            
            // Test the calculator operations
            System.out.println("Testing Calculator EJB...");
            System.out.println("10 + 5 = " + calc.add(10, 5));
            System.out.println("10 - 5 = " + calc.subtract(10, 5));
            System.out.println("10 * 5 = " + calc.multiply(10, 5));
            System.out.println("10 / 5 = " + calc.divide(10, 5));
            
            System.out.println("\nAll operations completed successfully!");
            
        } catch (NamingException e) {
            System.err.println("JNDI lookup failed: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
