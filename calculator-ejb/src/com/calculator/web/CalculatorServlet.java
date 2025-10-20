package com.calculator.web;

import com.calculator.ejb.CalculatorHome;
import com.calculator.ejb.CalculatorRemote;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet that handles calculator operations
 */
public class CalculatorServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String number1Str = request.getParameter("number1");
        String number2Str = request.getParameter("number2");
        String operation = request.getParameter("operation");
        
        String result = "";
        String error = "";
        
        try {
            // Validate input
            if (number1Str == null || number1Str.trim().isEmpty() ||
                number2Str == null || number2Str.trim().isEmpty() ||
                operation == null || operation.trim().isEmpty()) {
                error = "Please fill in all fields";
            } else {
                double num1 = Double.parseDouble(number1Str);
                double num2 = Double.parseDouble(number2Str);
                
                // Lookup EJB
                Context ctx = new InitialContext();
                Object obj = ctx.lookup("ejb/CalculatorHome");
                CalculatorHome home = (CalculatorHome) 
                    javax.rmi.PortableRemoteObject.narrow(obj, CalculatorHome.class);
                CalculatorRemote calculator = home.create();
                
                // Perform operation
                double resultValue = 0;
                if ("add".equals(operation)) {
                    resultValue = calculator.add(num1, num2);
                    result = num1 + " + " + num2 + " = " + resultValue;
                } else if ("subtract".equals(operation)) {
                    resultValue = calculator.subtract(num1, num2);
                    result = num1 + " - " + num2 + " = " + resultValue;
                } else if ("multiply".equals(operation)) {
                    resultValue = calculator.multiply(num1, num2);
                    result = num1 + " × " + num2 + " = " + resultValue;
                } else if ("divide".equals(operation)) {
                    resultValue = calculator.divide(num1, num2);
                    result = num1 + " ÷ " + num2 + " = " + resultValue;
                } else {
                    error = "Invalid operation";
                }
                
                ctx.close();
            }
            
        } catch (NumberFormatException e) {
            error = "Please enter valid numbers";
        } catch (Exception e) {
            error = "Error: " + e.getMessage();
        }
        
        // Set attributes for JSP
        request.setAttribute("result", result);
        request.setAttribute("error", error);
        request.setAttribute("number1", number1Str);
        request.setAttribute("number2", number2Str);
        request.setAttribute("operation", operation);
        
        // Forward to JSP
        request.getRequestDispatcher("/calculator.jsp").forward(request, response);
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Forward GET requests to JSP
        request.getRequestDispatcher("/calculator.jsp").forward(request, response);
    }
}
