package com.mlbean.clustering;

import com.mlbean.dataObjects.DataElement;
import com.mlbean.dataObjects.DataSet;

/**
 * Created by jtompkins on 10/21/16.
 */
public interface Classifier {
    DataElement predict(DataElement[] attributes);

    void train(DataSet data);
}
