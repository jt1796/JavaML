/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mlbean.mathObjects;

/**
 *
 * @author John
 */
public class Vector {
    private final Matrix backingMatrix;
    private final int length;
    private final boolean isColumn;
    
    public Vector(int length) {
        this.length = length;
        double[] data = new double[length];
        for(int i = 0; i < length; i++) {
            data[i] = 0;
        }
        backingMatrix = new Matrix(1, length, data);
        isColumn = true;
    }
    
    public Vector(int length, double[] data) {
        this.length = length;
        backingMatrix = new Matrix(1, length, data);
        isColumn = true;
    }
    
    public Vector scalarMultiplication(double c) {
        double[] newData = new double[this.length];
        for(int i = 0; i < length; i++) {
            newData[i] = c * this.get(i);
        }
        return new Vector(length, newData);
    }
    
    public Vector add(Vector vector) {
        double[] newData = new double[this.length];
        for(int i = 0; i < length; i++) {
            newData[i] = vector.get(i) + this.get(i);
        }
        return new Vector(length, newData);
    }
    
    public Vector subtract(Vector vector) {
        Vector inv = this.add(vector);
        return inv.scalarMultiplication(-1);
    }
    
    public double get(int i) {
        return backingMatrix.get(0, i);
    }
    
    public double dotProduct(Vector vector) {
        if(vector.length != this.length) {
            throw new RuntimeException("vector length mismatch: Me " + length + " other: " + vector.length);
        }
        double dotprod = 0;
        for(int i = 0; i < length; i++) {
            dotprod += get(i) * vector.get(i);
        }
        return dotprod;
    }
    
    public int getLength() {
        return length;
    }
    
    public String toString() {
        String ret = "";
        for (int i = 0; i < length; i++) {
            ret += "\n[ " + get(i) + " ]";
        }
        return ret;
    }
}
