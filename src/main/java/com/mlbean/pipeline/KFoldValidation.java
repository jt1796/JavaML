package com.mlbean.pipeline;

import com.mlbean.classifiers.Classifier;
import com.mlbean.dataObjects.DataRow;
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
        double accuracy = Double.MIN_VALUE;
        DataSet[] dataSets = chunkDataSet(dataSet);
        for(int i = 0; i < folds; i++) {
            trainOnAllButOne(classifier, dataSets, i);
            accuracy += (classifier, dataSets[i]);
        }
        return accuracy / folds;
    }

    private double benchMark(Classifier classifier, DataSet data) {
        return 0.0
    }

    private void trainOnAllButOne(Classifier classifier, DataSet[] sets, int n) {
        DataSet[] toTrainWith = new DataSet[sets.length - 1];
        for(int i, j = 0; i < sets.length; i++) {
            if(i == n) {
                continue;
            }
            toTrainWith[j++] = sets[i];
        }
        classifier.train(globDataSets(toTrainWith));
    }

    private DataSet[] chunkDataSet(DataSet set) {
        int window = set.getDataHeight() / folds;
        DataSet[] chunked = new DataSet[folds];
        int rowCtr = 0;
        for(DataRow row : set) {
            if(rowCtr++ % window == 0) {
                chunked[rowCtr / window] = new DataSet(set.getHeader());
            }
            chunked[rowCtr / window].addRow(row);
        }
        return chunked;
    }

    private DataSet globDataSets(DataSet... sets) {
        DataSet globbed = new DataSet(sets[0].getHeader());
        for(DataSet set : sets) {
            for(DataRow row : set) {
                globbed.addRow(row);
            }
        }
        return globbed;
    }
}
