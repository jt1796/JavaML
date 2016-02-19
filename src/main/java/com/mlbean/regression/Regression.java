/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mlbean.regression;

import com.mlbean.dataObjects.DataSet;
import com.mlbean.mathObjects.Vector;

/**
 *
 * @author John
 */
public abstract class Regression {
    protected double stepSize = 0;
    protected int iterations = 0;
    protected Vector coeff = null;
    protected DataSet dataSet = null;
    
    public Regression(DataSet dataSet, int iterations, double stepSize) {
        this.stepSize = stepSize;
        this.iterations = iterations;
        this.dataSet = dataSet;
    }
    
    public abstract void execute();
    
    public Vector getCoeffs() {
        if(null == coeff) {
            throw new RuntimeException("Must call execute() before this call");
        }
        return coeff;
    }
}
