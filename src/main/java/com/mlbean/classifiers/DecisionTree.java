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

import com.mlbean.dataObjects.DataElement;
import com.mlbean.dataObjects.DataHeader;
import com.mlbean.dataObjects.DataRow;
import com.mlbean.dataObjects.DataSet;

import javax.print.attribute.standard.DateTimeAtCompleted;
import javax.xml.crypto.Data;
import java.util.*;

/**
 *
 * @author John
 */
public class DecisionTree implements Classifier {

    private DTreeNode root;

    public DataElement predict(DataElement[] attributes) {
        return new DataElement(root.predict(attributes));
    }
    
    public void train(DataSet data) {
        root = new DTreeNode();
        root.buildChildren(data);
    }

    public String toString() {
        return root.toString();
    }
    
    private double entropy(DataSet data) {
        double entropy = 0.0;
        int totalSize = data.getDataHeight();
        HashMap <String, Integer> classCount = marshalNominalLabels(data);
        for(Map.Entry<String, Integer> s : classCount.entrySet()) {
            double prob = ((double)s.getValue()) / totalSize;
            entropy += prob * Math.log(prob);
        }
        return (-1) * entropy;
    }
    
    private HashMap<String, Integer> marshalNominalLabels(DataSet data) {
        HashMap<String, Integer> marshaled = new HashMap<>();
        for(DataRow row : data) {
            String classLabel = row.getLabel().getNominalValue();
            if(!marshaled.containsKey(classLabel)) {
                marshaled.put(classLabel, 0);
            }
            marshaled.put(classLabel, 1 + marshaled.get(classLabel));
        }
        return marshaled;
    }
    
    private DataSplit findBestSplitOnCol(DataSet data) {
        double leastEntropy = Double.MAX_VALUE;
        DataSplit bestSplit = null;
        for(int i = 0; i < data.getDataWidth() - 1; i++) {
            DataSplit split = findBestSplitOnColAtt(data, i);
            DataSet[] d = split.split(data);
            Double entropy = entropy(d[0]) + entropy(d[1]);
            if(entropy < leastEntropy) {
                leastEntropy = entropy;
                bestSplit = split;
            }
        }
        return bestSplit;
    }

    private DataSplit findBestSplitOnColAtt(DataSet data, int col) {
        double leastEntropy = Double.MAX_VALUE;
        DataSplit bestSplit = null;
        if (data.getHeader().getAttributeTypeByIndex(col).equals("nominal")) {
            HashSet<DataElement> attSet = new HashSet<>();
            for(DataRow d : data) {
                attSet.add(d.getAttribute(col));
            }
            for(DataElement val : attSet) {
                NominalDataSplit split = new NominalDataSplit();
                split.attribute = col;
                split.splitVal = val;
                DataSet[] datasets = split.split(data);
                double entropy = entropy(datasets[0]) + entropy(datasets[1]);
                if (entropy < leastEntropy) {
                    leastEntropy = entropy;
                    bestSplit = split;
                }
            }
        } else {
            HashSet<Double> hValues = new HashSet<>();
            for(DataRow d : data) {
                hValues.add(d.getNonLabel(col).getNumericValue());
            }
            hValues.add((-1) * Double.MAX_VALUE);
            hValues.add(Double.MAX_VALUE);
            ArrayList<Double> lValues = new ArrayList<>(hValues);
            lValues.sort((t0, t1) -> Double.compare(t0, t1));
            System.out.println(lValues);
            for(int i = 0; i < lValues.size() - 1; i++) {
                double splitval = (lValues.get(i) + lValues.get(i + 1)) / 2;
                NumericDataSplit split = new NumericDataSplit();
                split.attribute = col;
                split.splitVal = new DataElement(splitval);
                DataSet[] datasets = split.split(data);
                double entropy = entropy(datasets[0]) + entropy(datasets[1]);
                if (entropy < leastEntropy) {
                    leastEntropy = entropy;
                    bestSplit = split;
                }
            }
        }
        return bestSplit;
    }

    private boolean canSplit(DataSet data) {
        for(int i = 0; i < data.getDataWidth() - 1; i++) {
            DataElement lastAtt = null;
            for(DataRow d : data) {
                if(null == lastAtt) {
                    lastAtt = d.getAttribute(i);
                }
                if(!lastAtt.equals(d.getAttribute(i))) {
                    return true;
                }
            }
        }
        return false;
    }

    private String getMostCommonLabel(DataSet data) {
        HashMap<String, Integer> cMap = marshalNominalLabels(data);
        String attr = null;
        int maxCount = -1;
        for(Map.Entry<String, Integer> entry : cMap.entrySet()) {
            if(entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                attr = entry.getKey();
            }
        }
        return attr;
    }
    
    private class DTreeNode {
        DataSplit split = null;
        DTreeNode lChild = null;
        DTreeNode rChild = null;
        String prediction = null;

        String predict(DataElement[] attributes) {
            if(null != prediction) {
                return prediction;
            }
            int splitIndex = split.attribute;
            DataElement splitVal = split.splitVal;
            if(splitVal.dataType().equals("nominal")) {
                if(attributes[splitIndex].equals(splitVal)) {
                    return lChild.predict(attributes);
                } else {
                    return rChild.predict(attributes);
                }
            } else {
                if(attributes[splitIndex].getNumericValue() < splitVal.getNumericValue()) {
                    return lChild.predict(attributes);
                } else {
                    return rChild.predict(attributes);
                }
            }
        }
        
        void buildChildren(DataSet data) {
            if(data.getDataHeight() == 0) {
                return;
            }
            double entropy = entropy(data);
            if(entropy == 0.0) {
                prediction = data.iterator().next().getLabel().getNominalValue();
                return;
            }
            if (!canSplit(data)) {
                prediction = getMostCommonLabel(data);
                return;
            }
            split = findBestSplitOnCol(data);
            DataSet[] dChildren = split.split(data);
            lChild = new DTreeNode();
            rChild = new DTreeNode();
            lChild.buildChildren(dChildren[0]);
            rChild.buildChildren(dChildren[1]);
        }

        public String toString() {
            return "" + split;
        }
    }
    
    private abstract class DataSplit {
        DataElement splitVal = null;
        int attribute = -1;
        abstract DataSet[] split(DataSet data);

        public String toString() {
            return "Split on index : " + attribute;
        }
    }
    
    private class NumericDataSplit extends DataSplit {
        public DataSet[] split(DataSet data) {
            DataHeader header = data.getHeader();
            DataSet left = new DataSet(header);
            DataSet right = new DataSet(header);
            for(DataRow row : data) {
                double rowVal = row.getAttribute(attribute).getNumericValue();
                if(rowVal < splitVal.getNumericValue()) {
                    left.addRow(row);
                } else {
                    right.addRow(row);
                }
            }
            return new DataSet[] {left, right};
        }
    }
    
    private class NominalDataSplit extends DataSplit {
        public DataSet[] split(DataSet data) {
            DataHeader header = data.getHeader();
            DataSet left = new DataSet(header);
            DataSet right = new DataSet(header);
            for(DataRow row : data) {
                String rowVal = row.getAttribute(attribute).getNominalValue();
                if (rowVal.equals(splitVal.getNominalValue())) {
                    left.addRow(row);
                } else {
                    right.addRow(row);
                }
            }
            return new DataSet[] {left, right};
        }
    }
}
