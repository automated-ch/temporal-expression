/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.automated.temporal;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 *Testsuite to execute all tests
 * @author wit
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({ch.automated.temporal.TemporalExpressionOperationsTest.class, ch.automated.temporal.TemporalExpressionRangesTest.class})
public class TestAllTemporalExpression {
    
}
