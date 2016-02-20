/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mlbean.regression;

import com.mlbean.dataObjects.DataSet;
import com.mlbean.mathObjects.Matrix;
import com.mlbean.mathObjects.Vector;

/**
 *
 * @author John
 */
public class ClosedForm extends Regression {
    
    public ClosedForm(DataSet dataSet, int iterations, double stepSize) {
        super(dataSet, iterations, stepSize);
    }
    
    public void execute() {
        //(x transpose times x) inverse times x transpose.
        //then mul y
        Matrix x = this.dataSet.asMatrix();
        Matrix x_transpose = x.transpose();
        Vector y = this.dataSet.responseVector();
        Matrix result = x_transpose.multiply(x).multiply(x_transpose).multiply(dataSet.responseVector());
        double[] coeffData = new double[dataSet.getVarSpan()];
        for(int i = 0; i < coeffData.length; i++) {
            coeffData[i] = result.get(i, 0);
        }
        this.coeff = new Vector(dataSet.getVarSpan(), coeffData);
    }
}
