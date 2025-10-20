package com.calculator.ejb;

import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import java.rmi.RemoteException;

/**
 * Stateless Session Bean implementation for Calculator
 */
public class CalculatorBean implements SessionBean {
    
    private SessionContext ctx;
    
    // Business methods
    public double add(double a, double b) {
        return a + b;
    }
    
    public double subtract(double a, double b) {
        return a - b;
    }
    
    public double multiply(double a, double b) {
        return a * b;
    }
    
    public double divide(double a, double b) throws RemoteException {
        if (b == 0) {
            throw new RemoteException("Division by zero is not allowed");
        }
        return a / b;
    }
    
    // EJB lifecycle methods
    public void ejbCreate() {
        // Initialization logic
    }
    
    public void ejbRemove() {
        // Cleanup logic
    }
    
    public void ejbActivate() {
        // Called when bean is activated from pool
    }
    
    public void ejbPassivate() {
        // Called when bean is passivated to pool
    }
    
    public void setSessionContext(SessionContext ctx) {
        this.ctx = ctx;
    }
}
