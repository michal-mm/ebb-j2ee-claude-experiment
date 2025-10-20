package com.calculator.ejb;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Test suite to run all Calculator tests
 */
@RunWith(Suite.class)
@SuiteClasses({
    CalculatorBeanTest.class,
    CalculatorIntegrationTest.class
})
public class CalculatorTestSuite {
    // This class remains empty
    // Used only as a holder for the above annotations
}
