/*
 * Copyright (c) 2016, John
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package com.mlbean.mathObjects;

/**
 *
 * @author John
 */
public class Vector {
    private final Matrix backingMatrix;
    private final Matrix colMatrix;
    private final int length;
    private final boolean isColumn;
    
    public Vector(int length) {
        this.length = length;
        double[] data = new double[length];
        for(int i = 0; i < length; i++) {
            data[i] = 0;
        }
        backingMatrix = new Matrix(1, length, data);
        colMatrix = new Matrix(length, 1, data);
        isColumn = true;
    }
    
    public Vector(int length, double[] data) {
        this.length = length;
        backingMatrix = new Matrix(1, length, data);
        colMatrix = new Matrix(length, 1, data);
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
    
    public Matrix asColumnMatrix() {
        return colMatrix;
    }
    
    public Matrix asRowMatrix() {
        return backingMatrix;
    }
    
    public String toString() {
        StringBuilder ret = new StringBuilder();
        for (int i = 0; i < length; i++) {
            ret.append("\n[ ").append(get(i)).append(" ]");
        }
        return ret.toString();
    }
}
