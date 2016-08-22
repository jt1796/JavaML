/*
 * Copyright (c) 2016, tompk
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package com.mlbean.dataObjects;

import com.mlbean.mathObjects.Matrix;
import com.mlbean.mathObjects.Vector;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author tompk
 */
public class DataSetTest {
    
    DataRow row = null;
    DataRow rowTwo = null;
    DataRow rowThree = null;
    DataHeader header = null;
    
    public DataSetTest() {
        DataElement[] elements = {new DataElement(12344), new DataElement(5.23)};
        row = new DataRow(elements, new DataElement(1.12));
        header = new DataHeader("vehicle weight", "numeric", "price", "numeric", "vehicle height", "numeric");
        DataElement[] secondElements = {new DataElement(22112), new DataElement(7.00)};
        rowTwo = new DataRow(secondElements, new DataElement(2.33));
        DataElement[] thirdElements = {new DataElement(8700), new DataElement(2.34)};
        rowThree = new DataRow(thirdElements, new DataElement(2.01));
    }
   
    @Test
    public void testLabelsAsVector() {
        DataSet data = new DataSet(header);
        data.addRow(row);
        data.addRow(rowTwo);
        Vector vec = data.labelsAsVector();
        assertEquals(vec.get(0), 1.12, 0.0001);
        assertEquals(vec.get(1), 2.33, 0.0001);
        assertEquals(vec.getLength(), 2);
    }
    
    @Test
    public void testNonLabelsAsMatrix() {
        DataSet data = new DataSet(header);
        data.addRow(row);
        data.addRow(rowTwo);
        Matrix mat = data.nonLabelsAsMatrix();
        double[] expectedData = {12344, 5.23, 22112, 7.00};
        Matrix expectedMat = new Matrix(2, 2, expectedData);
        assertEquals(expectedMat, mat);
    }
    
    @Test
    public void addWithDimensionMismatch() {
    }
    
    @Test
    public void testTypeMismatchInNonLabel() {
        try {
            DataElement[] dArr = {new DataElement(5.2), new DataElement("expensize")};
            DataRow dRow = new DataRow(dArr, new DataElement(2.3));
            DataSet data = new DataSet(header);
            data.addRow(dRow);
        }catch(Exception e) {
            assertEquals(e.getMessage(), "this row has a type mismatch in price");
            return;
        }
        fail("no exception was thrown.");
    }
    
    @Test
    public void testTypeMismatchInLabel() {
        try {
            DataElement[] dArr = {new DataElement(5.2), new DataElement(5.4)};
            DataRow dRow = new DataRow(dArr, new DataElement("expensive"));
            DataSet data = new DataSet(header);
            data.addRow(dRow);
        }catch(Exception e) {
            assertEquals(e.getMessage(), "this row has a type mismatch in the label");
            return;
        }
        fail("no exception was thrown.");
    }
    
}
