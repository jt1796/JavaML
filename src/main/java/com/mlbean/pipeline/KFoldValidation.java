package com.mlbean.pipeline;

import com.mlbean.classifiers.Classifier;
import com.mlbean.dataObjects.DataSet;
import java.util.LinkedList;

/**
 * Created by john on 10/21/16.
 */
public class KFoldValidation {
    private int folds;

    public KFoldValidation(int folds) {
        this.folds = folds;
    }

    public double evalClassifier(Classifier classifier, DataSet dataSet) {
        if(dataSet.getDataHeight() < folds) {
            throw new RuntimeException("More folds than data rows");
        }
        DataSet[] dataSets = new DataSet[folds];
        int windowSize = dataSet.getDataHeight() / folds;
        // IN PROGRESS
        for(int i = 0; i < folds; i++) {
            int minRange = i * dataSet.getDataHeight() / folds;
            int maxRange = minRange + dataSet.getDataHeight() / folds;
            dataSets[i] = new DataSet(dataSet.getHeader());
            dataSets[i].switchBacking(new LinkedList<>());
        }
        return 0.0;
    }
}
