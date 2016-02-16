/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaml.tests.mathObjects;

import javaml.mathObjects.Matrix;
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
public class MatrixTest {
    
    public MatrixTest() {
    }

    @Test
    public void createTwoByThreeArray() {
        double[] testData = {1, 2, 3, 4, 5, 6};
        Matrix testMat = new Matrix(2, 3, testData);
        assertEquals(1.0, testMat.get(0,0), 0.01);
        assertEquals(2.0, testMat.get(0,1), 0.01);
        assertEquals(3.0, testMat.get(0,2), 0.01);
        assertEquals(4.0, testMat.get(1,0), 0.01);
        assertEquals(5.0, testMat.get(1,1), 0.01);
        assertEquals(6.0, testMat.get(1,2), 0.01);
        assertEquals(3, testMat.getWidth());
        assertEquals(2, testMat.getHeight());
    }
    
    @Test
    public void createTwoByThreeDoubleArray() {
        double[][] testData = {{1, 2, 3}, {4, 5, 6}};
        Matrix testMat = new Matrix(testData);
        assertEquals(1.0, testMat.get(0,0), 0.01);
        assertEquals(2.0, testMat.get(0,1), 0.01);
        assertEquals(3.0, testMat.get(0,2), 0.01);
        assertEquals(4.0, testMat.get(1,0), 0.01);
        assertEquals(5.0, testMat.get(1,1), 0.01);
        assertEquals(6.0, testMat.get(1,2), 0.01);
        assertEquals(3, testMat.getWidth());
        assertEquals(2, testMat.getHeight());
    }
    
    @Test
    public void transpostTwoByThree() {
        double[][] testData = {{1, 2, 3}, {4, 5, 6}};
        Matrix testMat = new Matrix(testData);
        testMat = testMat.transpose();
        assertEquals(3, testMat.getHeight());
        assertEquals(2, testMat.getWidth());        
        assertEquals(1.0, testMat.get(0,0), 0.01);
        assertEquals(2.0, testMat.get(1,0), 0.01);
        assertEquals(3.0, testMat.get(2,0), 0.01);
        assertEquals(4.0, testMat.get(0,1), 0.01);
        assertEquals(5.0, testMat.get(1,1), 0.01);
        assertEquals(6.0, testMat.get(2,1), 0.01);
    }
    
    @Test
    public void testScalarMultiplication() {
        double[][] testData = {{1, 2, 3}, {4, 5, 6}};
        Matrix testMat = new Matrix(testData);
        testMat = testMat.multiply(7);
        assertEquals(7.0, testMat.get(0,0), 0.01);
        assertEquals(14.0, testMat.get(0,1), 0.01);
        assertEquals(21.0, testMat.get(0,2), 0.01);
        assertEquals(28.0, testMat.get(1,0), 0.01);
        assertEquals(35.0, testMat.get(1,1), 0.01);
        assertEquals(42.0, testMat.get(1,2), 0.01);
    }
    
    @Test
    public void testMatrixMultiplication() {
        double[][] testData = {{1, 2, 3}, {4, 5, 6}};
        Matrix testMat = new Matrix(testData);
        double[][] testDataTwo = {{1, 2}, {3, 4}, {5, 6}};
        Matrix testMatTwo = new Matrix(testDataTwo);
        Matrix product = testMat.multiply(testMatTwo);
        assertEquals(2, product.getHeight());
        assertEquals(2, product.getWidth());
        assertEquals(8 + 20 + 36, product.get(1, 1), 0.01);
    }
    
    @Test
    public void testTrace() {
        double[][] testData = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        Matrix testMat = new Matrix(testData);
        assertEquals(1 + 5 + 9, testMat.trace(), 0.01);
    }
    
    @Test
    public void rowEchelon() {
        double[][] testData = {{1, 1, 2},{0, 1, 3},{0, 0, 5}};
        Matrix testMat = new Matrix(testData);
        testMat = testMat.RowEchelonForm(testMat);
        double[][] trueData = {{1, 1, 1}, {0, 1, 1}, {0, 0, -2}};
        Matrix trueMat = new Matrix(trueData);
        assertEquals(testMat, trueMat);
    }
    
    @Test
    public void testInverse() {
        double[][] testData = {{1, 3, 3},{1, 4, 3},{1, 3, 4}};
        Matrix testMat = new Matrix(testData);
        testMat = testMat.RowEchelonForm(testMat);
        double[][] trueData = {{7, -3, -3}, {-1, 1, 0}, {-1, 0, 1}};
        Matrix trueMat = new Matrix(trueData);
        assertEquals(testMat.inverse(), trueMat);
    }
    
    @Test
    public void testEquals() {
        double[][] testData = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        double[][] trueData = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        Matrix testMat = new Matrix(testData);
        Matrix trueMat = new Matrix(trueData);
        assertEquals(testMat, trueMat);
    }
}
