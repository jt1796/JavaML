package com.mlbean.classifiers;

import com.mlbean.dataObjects.DataElement;
import com.mlbean.dataObjects.DataHeader;
import com.mlbean.dataObjects.DataRow;
import com.mlbean.dataObjects.DataSet;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by tompk on 10/10/2016.
 */
public class DecisionTreeTest {

    DecisionTree tree;

    @Test
    public void testSimpleDecision() {
        DataSet one = new DataSet(new DataHeader("color", "nominal", "type", "nominal"));
        one.addRow(new DataRow(new DataElement[] {new DataElement("red"), new DataElement("heavy")}));
        one.addRow(new DataRow(new DataElement[] {new DataElement("green"), new DataElement("light")}));
        tree = new DecisionTree();
        tree.train(one);
        assertEquals("heavy", tree.predict(new DataElement[] {new DataElement("red")}));
        assertEquals("light", tree.predict(new DataElement[] {new DataElement("green")}));
    }

    @Test
    public void testTwoToOneSplit() {
        DataSet one = new DataSet(new DataHeader("color", "nominal", "type", "nominal"));
        one.addRow(new DataRow(new DataElement[] {new DataElement("red"), new DataElement("heavy")}));
        one.addRow(new DataRow(new DataElement[] {new DataElement("red"), new DataElement("heavy")}));
        one.addRow(new DataRow(new DataElement[] {new DataElement("red"), new DataElement("light")}));
        tree = new DecisionTree();
        tree.train(one);
        assertEquals("heavy", tree.predict(new DataElement[] {new DataElement("red")}));
    }
}
