/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaml.regression;

import javaml.dataObjects.DataSet;

/**
 *
 * @author John
 */
public class ClosedForm extends Regression {
    
    public ClosedForm(DataSet dataSet, int iterations, double stepSize) {
        super(dataSet, iterations, stepSize);
    }
    
    //TODO. Will need a matrix inverse operation.
    public void execute() {
        
    }
}
