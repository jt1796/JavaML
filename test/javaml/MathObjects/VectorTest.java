/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaml.MathObjects;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author john
 */
public class VectorTest {
    
    public VectorTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testConstruction() {
        // TODO review the generated test code and remove the default call to fail.
        double[] testData = {1, 2, 3, 4};
        Vector vector = new Vector(4, testData);
        for(int i = 0; i < 4; i++) {
            assertEquals(i + 1, vector.get(i), 0.01);
        }
    }
    
    @Test 
    public void testDotProduct() {
        double[] testData = {1, 2, 3, 4};
        double[] testDataTwo = {3, 7, 5, 5};
        Vector vecOne = new Vector(4, testData);
        Vector vecTwo = new Vector(4, testDataTwo);
        double product = vecOne.dotProduct(vecTwo);
        assertEquals(3 + 14 + 15 + 20, product, 0.01);
    }
    
}
