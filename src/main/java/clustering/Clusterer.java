/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clustering;

import com.mlbean.dataObjects.DataElement;
import com.mlbean.dataObjects.DataSet;

/**
 *
 * @author john
 */
public interface Clusterer {
    DataElement predict(DataElement[] attributes);

    void train(DataSet data);
}
