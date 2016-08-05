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

import com.mlbean.mathObjects.Vector;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author tompk
 */
public class DataRowTest {
    
    DataRow mixedDataRow = null;
    DataRow numericDataRow = null;
    
    public DataRowTest() {
        DataElement[] mixed = {new DataElement(2.9), new DataElement(5.4), new DataElement("truck")};
        DataElement[] pure = {new DataElement(111), new DataElement(1.2), new DataElement(3.21)};
        mixedDataRow = new DataRow(mixed, new DataElement(2.2));
        numericDataRow = new DataRow(pure, new DataElement(1.1));
    }
    
    @Test
    public void testGetLabel() {
        assertEquals(mixedDataRow.getLabel().getNumericValue(), 2.2, 0.0001);
    }
    
    @Test
    public void testNumAttributes() {
        assertEquals(mixedDataRow.numAttributes(), 4);
    }
    
    @Test
    public void testGetNonLabels() {
        assertEquals(mixedDataRow.getNonLabel(0).getNumericValue(), 2.9, 0.0001);
        assertEquals(mixedDataRow.getNonLabel(1).getNumericValue(), 5.4, 0.0001);
        assertEquals(mixedDataRow.getNonLabel(2).getNominalValue(), "truck");
    }
    
    @Test
    public void testAsVector() {
        Vector vec = numericDataRow.nonLabelsAsVector();
        assertEquals(vec.get(0), 111, 0.0001);
        assertEquals(vec.get(1), 1.2, 0.0001);
        assertEquals(vec.get(2), 3.21, 0.0001);
        assertEquals(vec.getLength(), 3);
    }
    
    @Test
    public void testErrorMixedToVector() {
        try {
            mixedDataRow.nonLabelsAsVector();
        } catch(Exception e) {
            assertEquals(e.getMessage(), "This DataRow contains nominal data");
            return;
        }
        fail("no error was thrown.");
    }
}
