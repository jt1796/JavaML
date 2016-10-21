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

    @Test
    public void testMultipleAttributes() {
        DataSet two = new DataSet(new DataHeader("color", "nominal", "height", "nominal", "weight", "nominal"));
        two.addRow(new DataRow(new DataElement[] {new DataElement("blue"), new DataElement("short"), new DataElement("light")}));
        two.addRow(new DataRow(new DataElement[] {new DataElement("blue"), new DataElement("tall"), new DataElement("heavy")}));
        two.addRow(new DataRow(new DataElement[] {new DataElement("red"), new DataElement("short"), new DataElement("light")}));
        two.addRow(new DataRow(new DataElement[] {new DataElement("red"), new DataElement("tall"), new DataElement("heavy")}));
        tree = new DecisionTree();
        tree.train(two);
        assertEquals("light", tree.predict(new DataElement[] {new DataElement("blue"), new DataElement("short")}));
        assertEquals("heavy", tree.predict(new DataElement[] {new DataElement("blue"), new DataElement("tall")}));
        assertEquals("light", tree.predict(new DataElement[] {new DataElement("red"), new DataElement("short")}));
        assertEquals("heavy", tree.predict(new DataElement[] {new DataElement("red"), new DataElement("tall")}));
    }

    @Test
    public void testUselessAttributes() {
        DataSet two = new DataSet(new DataHeader("color", "nominal", "height", "nominal", "weight", "nominal"));
        two.addRow(new DataRow(new DataElement[] {new DataElement("blue"), new DataElement("short"), new DataElement("light")}));
        two.addRow(new DataRow(new DataElement[] {new DataElement("blue"), new DataElement("tall"), new DataElement("light")}));
        two.addRow(new DataRow(new DataElement[] {new DataElement("red"), new DataElement("short"), new DataElement("heavy")}));
        two.addRow(new DataRow(new DataElement[] {new DataElement("red"), new DataElement("tall"), new DataElement("heavy")}));
        tree = new DecisionTree();
        tree.train(two);
        assertEquals("light", tree.predict(new DataElement[] {new DataElement("blue"), new DataElement("short")}));
        assertEquals("light", tree.predict(new DataElement[] {new DataElement("blue"), new DataElement("tall")}));
        assertEquals("heavy", tree.predict(new DataElement[] {new DataElement("red"), new DataElement("short")}));
        assertEquals("heavy", tree.predict(new DataElement[] {new DataElement("red"), new DataElement("tall")}));
    }
}
