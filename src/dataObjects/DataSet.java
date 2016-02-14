/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataObjects;

import java.util.LinkedList;
import java.util.Iterator;

/**
 *
 * @author John
 */
public class DataSet implements Iterable<DataElement> {
    LinkedList<DataElement> data = null;
    int dataSize;
    int numberOfIndependents;
    
    public DataSet(int n) {
        this.data = new LinkedList<>();
        dataSize = 0;
        numberOfIndependents = n + 1;
    }
    
    public void addData(double response, double[] data) {
        if(data.length + 1 != numberOfIndependents) {
            throw new RuntimeException("Wrong number of inputs");
        }
        double[] extendedData = new double[data.length + 1];
        extendedData[0] = 1;
        for(int i = 0; i < data.length; i++) {
            extendedData[i+1] = data[i];
        }
        DataElement element = new DataElement(response, extendedData);
        this.data.add(element);
        dataSize++;
    }
    
    public Iterator<DataElement> iterator() {
        return data.iterator();
    }
    
    public int getVarSpan() {
        return numberOfIndependents;
    }
    
    public int getDataSize() {
        return dataSize;
    }
}
