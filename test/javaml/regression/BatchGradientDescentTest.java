/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaml.regression;

import javaml.regression.BatchGradientDescent;
import dataObjects.DataSet;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author John
 */
public class BatchGradientDescentTest {
    
    public BatchGradientDescentTest() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    @Test
    public void TwoDimLinear() {
        DataSet set = new DataSet(1);
        set.addData(6, new double[]{3});
        set.addData(8, new double[]{4});
        set.addData(20, new double[]{10});
        BatchGradientDescent batch = new BatchGradientDescent(set, 1000, .01);
        batch.execute();
        assertEquals(2, batch.getCoeffs().get(1), 0.01);
    }
}
