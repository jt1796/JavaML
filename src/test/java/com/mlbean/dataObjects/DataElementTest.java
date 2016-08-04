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

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author tompk
 */
public class DataElementTest {
    
    @Test
    public void testNumericIdentifiesAsNumeric() {
        DataElement element = new DataElement(2.33);
        assertEquals(element.getNumericValue(), 2.33, 0.001);
    }
    
    @Test
    public void testNominalIdentifiesAsNominal() {
        DataElement element = new DataElement("blue");
        assertEquals(element.getNominalValue(), "blue");
    }
    
    @Test
    public void testErrorOnNominalValue() {
        DataElement element = new DataElement(1.22);
        try {
            element.getNominalValue();
        } catch(Exception e) {
            assertEquals(e.getMessage(), "This is not a nominal DataElement");
            return;
        }
        fail("no exception was thrown");
    }
    
    @Test
    public void testErrorOnNumericValue() {
        DataElement element = new DataElement("green");
        try {
            element.getNumericValue();
        } catch(Exception e) {
            assertEquals(e.getMessage(), "This is not a numeric DataElement");
            return;
        }
        fail("no exception was thrown");
    }
    
}
