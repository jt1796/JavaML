package com.mlbean.classifiers;

import com.mlbean.clustering.KNN;
import com.mlbean.dataObjects.DataElement;
import com.mlbean.dataObjects.DataHeader;
import com.mlbean.dataObjects.DataRow;
import com.mlbean.dataObjects.DataSet;
import org.junit.Test;
import static org.junit.Assert.*;

public class KNNTest {

    @Test
    public void testNominal() {
        DataHeader header = new DataHeader("x", "numeric", "y", "numeric", "fruit", "nominal");
        DataSet set = new DataSet(header);
        set.addRow(new DataRow(new DataElement[]{new DataElement(-1), new DataElement(4)}, new DataElement("apple")));
        set.addRow(new DataRow(new DataElement[]{new DataElement(1), new DataElement(4)}, new DataElement("apple")));
        set.addRow(new DataRow(new DataElement[]{new DataElement(-1), new DataElement(-5)}, new DataElement("orange")));
        set.addRow(new DataRow(new DataElement[]{new DataElement(-1), new DataElement(-6)}, new DataElement("orange")));
        set.addRow(new DataRow(new DataElement[]{new DataElement(-1), new DataElement(-7)}, new DataElement("orange")));
        
        DataElement[] toClassify = new DataElement[]{new DataElement(0), new DataElement(4)};
        KNN clusterer = new KNN();
        clusterer.train(set);
        assertEquals("apple", clusterer.predict(toClassify).getNominalValue());
        clusterer.setK(5);
        assertEquals("orange", clusterer.predict(toClassify).getNominalValue());
    }
    
    @Test
    public void testNumeric() {
        DataHeader header = new DataHeader("x", "numeric", "y", "numeric", "fruit", "numeric");
        DataSet set = new DataSet(header);
        set.addRow(new DataRow(new DataElement[]{new DataElement(-1), new DataElement(4)}, new DataElement(1)));
        set.addRow(new DataRow(new DataElement[]{new DataElement(1), new DataElement(4)}, new DataElement(2)));
        set.addRow(new DataRow(new DataElement[]{new DataElement(-1), new DataElement(-5)}, new DataElement(5)));
        set.addRow(new DataRow(new DataElement[]{new DataElement(-1), new DataElement(-6)}, new DataElement(7)));
        set.addRow(new DataRow(new DataElement[]{new DataElement(-1), new DataElement(-7)}, new DataElement(9)));
        
        DataElement[] toClassify = new DataElement[]{new DataElement(0), new DataElement(4)};
        KNN clusterer = new KNN();
        clusterer.train(set);
        assertEquals(2.666666667, clusterer.predict(toClassify).getNumericValue(), 0.00001);
        clusterer.setK(5);
        assertEquals(4.8, clusterer.predict(toClassify).getNumericValue(), 0.00001);
    }
    
}
