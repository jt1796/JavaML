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
public class DataSet {
    LinkedList<DataElement> data = null;
    int dataSize;
    int numberOfIndependents;
    
    public DataSet(int n) {
        this.data = new LinkedList<>();
        dataSize = 0;
        numberOfIndependents = n;
    }
    
    public void addData(double response, double[] data) {
        if(data.length != numberOfIndependents) {
            throw new RuntimeException("Wrong number of inputs");
        }
        DataElement element = new DataElement(response, data);
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
