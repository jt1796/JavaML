/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mlbean.clustering;

import com.mlbean.dataObjects.DataElement;
import com.mlbean.dataObjects.DataRow;
import com.mlbean.dataObjects.DataSet;
import java.util.HashMap;

import java.util.PriorityQueue;

/**
 *
 * @author john.tompkins
 */
public class KNN implements Classifier {
    
    private DataSet data;
    private int k;
    
    @Override
    public DataElement predict(DataElement[] attributes) {
        PriorityQueue<DataRow> pq = new PriorityQueue<>(data.getDataHeight(), (a, b) -> cmp(a, b, attributes) );
        for (DataRow dr : data) {
            pq.add(dr);
        }
        DataRow[] elems = new DataRow[k];
        for (int i = 0; i < k; i++) {
            elems[i] = pq.poll();
        }
        if (data.getHeader().getAttributeTypeByIndex(data.getDataWidth() - 1).equals("nominal")) {
            return predictNominal(elems);
        } else {
            return predictNumeric(elems);
        }
    }
    
    private DataElement predictNominal(DataRow ...kelems) {
        HashMap<DataElement, Integer> counts = new HashMap<>();
        DataElement prediction = null;
        int occurences = 0;
        for (DataRow dr : kelems) {
            DataElement elem = dr.getLabel();
            if (!counts.containsKey(elem)) {
                counts.put(elem, 0);
            }
            int cur = counts.get(elem) + 1;
            counts.put(elem, cur);
            if (cur > occurences) {
                occurences = cur;
                prediction = elem;
            }
        }
        return prediction;
    }
    
    private DataElement predictNumeric(DataRow ...kelems) {
        double sum = 0.0;
        for (DataRow dr : kelems) {
            System.out.println(dr.getLabel().getNumericValue());
            sum += dr.getLabel().getNumericValue();
        }
        return new DataElement(sum / kelems.length);
    }
    
    public void setK(int k) {
        this.k = k;
    }

    @Override
    public void train(DataSet data) {
        this.data = data;
        this.k = 3;
    }
    
    private int cmp(DataRow a, DataRow b, DataElement[] attrs) {
        double db = distanceBetween(a, attrs) - distanceBetween(b, attrs);
        return (db < 0) ? -1 : 1;
    }
    
    private double distanceBetween(DataRow a, DataElement[] b) {
        double mulled = 0;
        for (int i = 0; i < a.numAttributes() - 1; i++) {
            double diff = a.getAttribute(i).getNumericValue() - b[i].getNumericValue();
            mulled += diff * diff;
        }
        return Math.sqrt(mulled);
    }
    
}
