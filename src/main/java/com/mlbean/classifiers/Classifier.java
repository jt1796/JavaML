package com.mlbean.classifiers;

import com.mlbean.dataObjects.DataElement;
import com.mlbean.dataObjects.DataSet;

/**
 * Created by jtompkins on 10/21/16.
 */
public interface Classifier {
    public DataElement predict(DataElement[] attributes);

    public void train(DataSet data);
}
