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
public class BatchGradientDescent extends Regression {
    
    public BatchGradientDescent(DataSet dataSet, int iterations, double stepSize) {
        super(dataSet, iterations, stepSize);
    }
    
    public void execute() {
        coeff = new Vector(dataSet.getVarSpan());
        for(int i = 0; i < iterations; i++) {
            double[] newCoeff = new double[coeff.getLength()];
            for(int omega = 0; omega < coeff.getLength(); omega++) {
                double sum = 0;
                for(DataElement dataRow : dataSet) {
                    sum += (coeff.dotProduct(dataRow.independenceAsVector()) - dataRow.getResponse()) * dataRow.getIth(omega) * stepSize;
                }
                newCoeff[omega] = coeff.get(omega) - sum;
            }
            coeff = new Vector(dataSet.getVarSpan(), newCoeff);
        }
    }
}
