/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataObjects;

import javaml.MathObjects.Vector;

/**
 *
 * @author John
 */
public class DataElement {
    private double response = 0;
    double[] independents = null;
    
    public DataElement(double response, double[] independents) {
        this.response = response;
        this.independents = independents;
    }
    
    public double getResponse() {
        return response;
    }
    
    public double getIth(int i) {
        return independents[i];
    }
    
    public Vector independenceAsVector() {
        return new Vector(independents.length, independents);
    }
}
