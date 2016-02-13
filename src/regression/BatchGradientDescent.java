/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package regression;

import javaml.MathObjects.Vector;

/**
 *
 * @author John
 */
public class BatchGradientDescent {
    private double stepSize = 0;
    private int varSpan = 0;
    private int iterations = 0;
    private Vector coeff = null;
    
    public BatchGradientDescent(double stepSize, int varSpan, int iterations) {
        this.stepSize = stepSize;
        this.varSpan = varSpan;
        this.iterations = iterations;
        this.coeff = new Vector(varSpan);
    }
    
    public void execute() {
        
    }
    
}
