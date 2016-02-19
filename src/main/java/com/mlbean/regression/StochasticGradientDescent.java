/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mlbean.regression;

import com.mlbean.dataObjects.DataElement;
import com.mlbean.dataObjects.DataSet;
import com.mlbean.mathObjects.Vector;

/**
 *
 * @author John
 */
public class StochasticGradientDescent extends Regression {

    public StochasticGradientDescent(DataSet dataSet, int iterations, double stepSize) {
        super(dataSet, iterations, stepSize);
    }
    
    public void execute() {
        double[] t_coeff = new double[dataSet.getVarSpan()];
        for(int i = 0; i < iterations; i++) {
            for(DataElement dataRow : dataSet) {
                for(int omega = 0; omega < t_coeff.length; omega++) {
                    t_coeff[omega] = t_coeff[omega] - stepSize * dataRow.getIth(omega) * 
                            (dataRow.independenceAsVector().dotProduct(new Vector(t_coeff.length, t_coeff)) - dataRow.getResponse());
                }
            }
        }
        coeff = new Vector(t_coeff.length, t_coeff);
    }
}
