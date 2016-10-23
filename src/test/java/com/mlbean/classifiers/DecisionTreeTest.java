package com.mlbean.classifiers;

import com.mlbean.dataObjects.*;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by tompk on 10/10/2016.
 */
public class DecisionTreeTest {

    DecisionTree tree;
    DataObjectFactory factory = new DataObjectFactory();

    @Test
    public void testSimpleDecision() {
        DataSet one = new DataSet(new DataHeader("color", "nominal", "type", "nominal"));
        one.addRow(new DataRow(new DataElement[] {new DataElement("red"), new DataElement("heavy")}));
        one.addRow(new DataRow(new DataElement[] {new DataElement("green"), new DataElement("light")}));
        tree = new DecisionTree();
        tree.train(one);
        assertEquals(new DataElement("heavy"), tree.predict(new DataElement[] {new DataElement("red")}));
        assertEquals(new DataElement("light"), tree.predict(new DataElement[] {new DataElement("green")}));
    }

    @Test
    public void testContradictingNominals() {
        DataSet one = new DataSet(new DataHeader("color", "nominal", "type", "nominal"));
        one.addRow(new DataRow(new DataElement[] {new DataElement("red"), new DataElement("heavy")}));
        one.addRow(new DataRow(new DataElement[] {new DataElement("red"), new DataElement("heavy")}));
        one.addRow(new DataRow(new DataElement[] {new DataElement("red"), new DataElement("light")}));
        tree = new DecisionTree();
        tree.train(one);
        assertEquals(new DataElement("heavy"), tree.predict(new DataElement[] {new DataElement("red")}));
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
        assertEquals(new DataElement("light"), tree.predict(new DataElement[] {new DataElement("blue"), new DataElement("short")}));
        assertEquals(new DataElement("heavy"), tree.predict(new DataElement[] {new DataElement("blue"), new DataElement("tall")}));
        assertEquals(new DataElement("light"), tree.predict(new DataElement[] {new DataElement("red"), new DataElement("short")}));
        assertEquals(new DataElement("heavy"), tree.predict(new DataElement[] {new DataElement("red"), new DataElement("tall")}));
    }

    @Test
    public void testUselessAttributes() {
        DataSet two = new DataSet(new DataHeader("color", "nominal", "height", "nominal", "weight", "nominal"));
        two.addRow(factory.genRow("blue", "short", "light"));
        two.addRow(factory.genRow("blue", "tall", "light"));
        two.addRow(factory.genRow("red", "short", "heavy"));
        two.addRow(factory.genRow("red", "tall", "heavy"));
        tree = new DecisionTree();
        tree.train(two);
        assertEquals(new DataElement("light"), tree.predict(factory.genElementArray("blue", "short")));
        assertEquals(new DataElement("light"), tree.predict(factory.genElementArray("blue", "tall")));
        assertEquals(new DataElement("heavy"), tree.predict(factory.genElementArray("red", "short")));
        assertEquals(new DataElement("heavy"), tree.predict(factory.genElementArray("red", "tall")));
    }

    @Test
    public void testNumericAttributes() {
        DataSet one = new DataSet(new DataHeader("price", "numeric", "weight", "nominal"));
        one.addRow(factory.genRow(1.20, "light"));
        one.addRow(factory.genRow(3.50, "light"));
        one.addRow(factory.genRow(6.00, "heavy"));
        tree = new DecisionTree();
        tree.train(one);
        assertEquals(new DataElement("heavy"), tree.predict(factory.genElementArray(7.00)));
        assertEquals(new DataElement("heavy"), tree.predict(factory.genElementArray((3.5 + 6.0)/2 + 0.001)));
        assertEquals(new DataElement("light"), tree.predict(factory.genElementArray(-9999.0)));
        assertEquals(new DataElement("heavy"), tree.predict(factory.genElementArray(9999)));
        assertEquals(new DataElement("light"), tree.predict(factory.genElementArray((3.5 + 6.0)/2 - 0.001)));
    }

    @Test
    public void testMixedAttributeTypes() {
        DataSet two = new DataSet(new DataHeader("price", "numeric", "size", "nominal", "value", "nominal"));
        two.addRow(factory.genRow(1.00, "large", "low"));
        two.addRow(factory.genRow(18.95, "small", "low"));
        two.addRow(factory.genRow(2.00, "small", "low"));
        two.addRow(factory.genRow(20.00, "large", "high"));
        tree = new DecisionTree();
        tree.train(two);
        assertEquals(new DataElement("low"), tree.predict(factory.genElementArray(2.00, "large")));
        assertEquals(new DataElement("low"), tree.predict(factory.genElementArray(1.50, "small")));
        assertEquals(new DataElement("low"), tree.predict(factory.genElementArray(18.00, "small")));
        assertEquals(new DataElement("high"), tree.predict(factory.genElementArray(25.00, "large")));
    }

}
