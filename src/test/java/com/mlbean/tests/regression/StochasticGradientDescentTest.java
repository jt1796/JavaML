/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mlbean.tests.regression;

import com.mlbean.dataObjects.DataSet;
import com.mlbean.regression.Regression;
import com.mlbean.regression.StochasticGradientDescent;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author John
 */
public class StochasticGradientDescentTest {
    
    public StochasticGradientDescentTest() {
    }

    @Test
    public void TwoDimLinear() {
        DataSet set = new DataSet(1);
        set.addData(6, new double[]{3});
        set.addData(8, new double[]{4});
        set.addData(20, new double[]{10});
        Regression stoch = new StochasticGradientDescent(set, 10000, .01);
        stoch.execute();
        assertEquals(2, stoch.getCoeffs().get(1), 0.01);
    }
    
    @Test
    public void FourDimNoisy() {
        DataSet set = new DataSet(4);
        set.addData(24 + 3, new double[]{1, 1, 1, 1});
        set.addData(48 + 3, new double[]{2, 2, 2, 2});
        set.addData(53 + 3, new double[]{3, 1, 6, 1});
        set.addData(150 + 3, new double[]{12, -3, 3, 8});
        set.addData(672 + 3, new double[]{7, 19, 23, 51});
        Regression stoch = new StochasticGradientDescent(set, 80000, .001);
        stoch.execute();
        assertEquals(3, stoch.getCoeffs().get(0), 0.01);
        assertEquals(7, stoch.getCoeffs().get(1), 0.01);
        assertEquals(5, stoch.getCoeffs().get(2), 0.01);
        assertEquals(3, stoch.getCoeffs().get(3), 0.01);
        assertEquals(9, stoch.getCoeffs().get(4), 0.01);
    }
}
