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
public class KNN implements Clusterer {
    
    private DataSet data;
    private int k;

    @Override
    public DataElement predict(DataRow attrs) {
        PriorityQueue<DataRow> pq = new PriorityQueue<>(data.getDataHeight(), (a, b) -> ((int) (distanceBetween(a, attrs) - distanceBetween(b, attrs))) );
        for (DataRow dr : data) {
            pq.add(dr);
        }
        HashMap<DataElement, Integer> counts = new HashMap<>();
        DataElement prediction = null;
        int occurences = 0;
        for (int i = 0; i < k; i ++) {
            DataElement elem = pq.poll().getLabel();
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
    
    public void setK(int k) {
        this.k = k;
    }

    @Override
    public void train(DataSet data) {
        this.data = data;
        this.k = 3;
    }
    
    private double distanceBetween(DataRow a, DataRow b) {
        double mulled = 0;
        for (int i = 0; i < a.numAttributes() - 1; i++) {
            double diff = a.getAttribute(i).getNumericValue() - b.getAttribute(i).getNumericValue();
            mulled += diff * diff;
        }
        return Math.sqrt(mulled);
    }
    
}
