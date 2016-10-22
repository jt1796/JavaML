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

import javax.xml.crypto.Data;
import java.util.HashMap;
import java.util.HashSet;

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
        for(String s : classCount.keySet()) {
            double prob = ((double)classCount.get(s)) / totalSize;
            entropy += prob * Math.log(prob);
        }
        return (-1) * entropy;
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

    private boolean canSplit(DataSet data) {
        for(int i = 0; i < data.getDataWidth() - 1; i++) {
            String lastAtt = null;
            for(DataRow d : data) {
                if(null == lastAtt) {
                    lastAtt = d.getAttribute(i).getNominalValue();
                }
                if(!lastAtt.equals(d.getAttribute(i).getNominalValue())) {
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
        for(String key : cMap.keySet()) {
            if(cMap.get(key) > maxCount) {
                maxCount = cMap.get(key);
                attr = key;
            }
        }
        return attr;
    }

    private DataSplit findBestSplitOnColAtt(DataSet data, int col) {
        double leastEntropy = Double.MAX_VALUE;
        DataSplit bestSplit = null;
        HashSet<String> attSet = new HashSet<>();
        for(DataRow d : data) {
            attSet.add(d.getAttribute(col).getNominalValue());
        }
        for(String val : attSet) {
            NominalDataSplit split = new NominalDataSplit();
            split.attribute = col;
            split.val = val;
            DataSet[] dsets = split.split(data);
            double entropy = entropy(dsets[0]) + entropy(dsets[1]);
            if (entropy < leastEntropy) {
                leastEntropy = entropy;
                bestSplit = split;
            }
        }
        return bestSplit;
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
            String splitVal = ((NominalDataSplit) split).val;
            if(attributes[splitIndex].getNominalValue().equals(splitVal)) {
                return lChild.predict(attributes);
            } else {
                return rChild.predict(attributes);
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
            DataSet[] datum = split.split(data);
            lChild = new DTreeNode();
            rChild = new DTreeNode();
            lChild.buildChildren(datum[0]);
            rChild.buildChildren(datum[1]);
        }

        public String toString() {
            return "" + split;
        }
    }
    
    private abstract class DataSplit {
        int attribute = -1;
        abstract DataSet[] split(DataSet data);
    }
    
    private class NumericDataSplit extends DataSplit {
        double threshold;
        
        public DataSet[] split(DataSet data) {
            DataHeader header = data.getHeader();
            DataSet left = new DataSet(header);
            DataSet right = new DataSet(header);
            for(DataRow row : data) {
                double rowVal = row.getAttribute(attribute).getNumericValue();
                if(rowVal < threshold) {
                    left.addRow(row);
                } else {
                    right.addRow(row);
                }
            }
            return new DataSet[] {left, right};
        }
    }
    
    private class NominalDataSplit extends DataSplit {
        String val;
        
        public DataSet[] split(DataSet data) {
            DataHeader header = data.getHeader();
            DataSet left = new DataSet(header);
            DataSet right = new DataSet(header);
            for(DataRow row : data) {
                String rowVal = row.getAttribute(attribute).getNominalValue();
                if (rowVal.equals(val)) {
                    left.addRow(row);
                } else {
                    right.addRow(row);
                }
            }
            return new DataSet[] {left, right};
        }

        public String toString() {
            return "Split on index : " + attribute;
        }
    }
}
