package com.mlbean.clustering;

import com.mlbean.dataObjects.DataElement;
import com.mlbean.dataObjects.DataHeader;
import com.mlbean.dataObjects.DataRow;
import com.mlbean.dataObjects.DataSet;
import org.junit.Test;
import static org.junit.Assert.*;

public class KNNTest {

    /**
     * Test of predict method, of class KNN.
     *      -   -  
     *        o
     *      
     *     
     *      + + +
     * 
     * k = 2 -> o = -
     * k = 5 -> o = +
     */
    @Test
    public void testPredict() {
        DataHeader header = new DataHeader("x", "numeric", "y", "numeric", "fruit", "nominal");
        DataSet set = new DataSet(header);
        set.addRow(new DataRow(new DataElement[]{new DataElement(-1), new DataElement(4)}, new DataElement("apple")));
        set.addRow(new DataRow(new DataElement[]{new DataElement(1), new DataElement(4)}, new DataElement("apple")));
        set.addRow(new DataRow(new DataElement[]{new DataElement(-1), new DataElement(-5)}, new DataElement("orange")));
        set.addRow(new DataRow(new DataElement[]{new DataElement(-1), new DataElement(-6)}, new DataElement("orange")));
        set.addRow(new DataRow(new DataElement[]{new DataElement(-1), new DataElement(-7)}, new DataElement("orange")));
        
        DataRow toClassify = new DataRow(new DataElement[]{new DataElement(0), new DataElement(4)}, new DataElement("apple"));
        KNN clusterer = new KNN();
        clusterer.train(set);
        assertEquals("apple", clusterer.predict(toClassify).getNominalValue());
        clusterer.setK(5);
        assertEquals("orange", clusterer.predict(toClassify).getNominalValue());
    }
    
}
