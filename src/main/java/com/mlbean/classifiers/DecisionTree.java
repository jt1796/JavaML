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
package com.mlbean.classifiers;

import com.mlbean.dataObjects.DataRow;
import com.mlbean.dataObjects.DataSet;
import java.util.HashMap;

/**
 *
 * @author John
 */
public class DecisionTree {
    
    private double entropy(DataSet data) {
        double entropy = 0.0;
        int totalSize = data.getDataHeight();
        HashMap <String, Integer> classCount = marshalNominalLabels(data);
        for(String s : classCount.keySet()) {
            double prob = classCount.get(s) / totalSize;
            entropy += prob * Math.log(prob);
        }
        return (-1) * entropy;
    }
    
    private double infoGain(DataSet data, String attribute) {
        double entropy = entropy(data);
        return 0.0;
    }
    
    private HashMap<String, Integer> marshalNominalLabels(DataSet data) {
        HashMap<String, Integer> marshalled = new HashMap<>();
        for(DataRow row : data) {
            String classLabel = row.getLabel().getNominalValue();
            if(!marshalled.containsKey(classLabel)) {
                marshalled.put(classLabel, 0);
            }
            marshalled.put(classLabel, 1 + marshalled.get(classLabel));
        }
        return marshalled;
    }
}
