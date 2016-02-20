/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mlbean.dataObjects;

import com.mlbean.mathObjects.Matrix;
import com.mlbean.mathObjects.Vector;
import java.util.LinkedList;
import java.util.Iterator;

/**
 *
 * @author John
 */
public class DataSet implements Iterable<DataElement> {
    LinkedList<DataElement> data = null;
    int dataSize;
    int numberOfIndependentsPlusBase;
    
    public DataSet(int n) {
        this.data = new LinkedList<>();
        dataSize = 0;
        numberOfIndependentsPlusBase = n + 1;
    }
    
    public void addData(double response, double[] data) {
        if(data.length + 1 != numberOfIndependentsPlusBase) {
            throw new RuntimeException("Wrong number of inputs");
        }
        double[] extendedData = new double[data.length + 1];
        extendedData[0] = 1;
        System.arraycopy(data, 0, extendedData, 1, data.length);
        DataElement element = new DataElement(response, extendedData);
        this.data.add(element);
        dataSize++;
    }
    
    public Iterator<DataElement> iterator() {
        return data.iterator();
    }
    
    public int getVarSpan() {
        return numberOfIndependentsPlusBase;
    }
    
    public int getDataSize() {
        return dataSize;
    }
    
    public Matrix asMatrix() {
        double[][] matData = new double[dataSize][data.size() + 1];
        int ctr = 0;
        for(DataElement d : this) {
            for(int c = 0; c < numberOfIndependentsPlusBase; c++) {
                matData[ctr][c] = d.getIth(c);
            }
            ctr++;
        }
        return new Matrix(matData);
    }
    
    public Vector responseVector() {
        double[] vecData = new double[dataSize];
        int i = 0;
        for(DataElement row : this) {
            vecData[i++] = row.getResponse();
        }
        return new Vector(dataSize, vecData);
    }
}
