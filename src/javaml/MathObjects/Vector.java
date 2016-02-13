/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaml.MathObjects;

/**
 *
 * @author John
 */
public class Vector {
    private Matrix backingMatrix;
    private int length;
    private boolean isRow;
    private boolean isColumn;
    
    public Vector(int length, double[] data) {
        this.length = length;
        backingMatrix = new Matrix(1, length, data);
    }
    
    public double get(int i) {
        return backingMatrix.get(0, i);
    }
    
    public double dotProduct(Vector vector) {
        int dotprod = 0;
        for(int i = 0; i < length; i++) {
            dotprod += get(i) * vector.get(i);
        }
        return dotprod;
    }
}
