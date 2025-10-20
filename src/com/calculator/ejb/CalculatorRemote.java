package com.calculator.ejb;

import javax.ejb.EJBObject;
import java.rmi.RemoteException;

/**
 * Remote interface for Calculator EJB
 */
public interface CalculatorRemote extends EJBObject {
    
    double add(double a, double b) throws RemoteException;
    
    double subtract(double a, double b) throws RemoteException;
    
    double multiply(double a, double b) throws RemoteException;
    
    double divide(double a, double b) throws RemoteException;
}
