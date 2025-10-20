package com.calculator.ejb;

import javax.ejb.EJBHome;
import javax.ejb.CreateException;
import java.rmi.RemoteException;

/**
 * Home interface for Calculator EJB
 */
public interface CalculatorHome extends EJBHome {
    
    CalculatorRemote create() throws RemoteException, CreateException;
}
