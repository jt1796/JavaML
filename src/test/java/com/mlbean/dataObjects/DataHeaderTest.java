/*
 * Copyright (c) 2016, John
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
 * @author John
 */
public class DataHeaderTest {
    
    public DataHeaderTest() {
    }
    
    @Test
    public void testFewerThanFourAttributes() {
        try {
            DataHeader dataHeader = new DataHeader("age", "numeric");
        }catch(RuntimeException e) {
            assertEquals(e.getMessage(), "There must be at least two attributes");
            return;
        }
        fail("exception was not thrown.");
    }
    
    @Test
    public void testInvalidType() {
        try {
            DataHeader dataHeader = new DataHeader("age", "numeric", "weight", "nmeric");
        }catch(RuntimeException e) {
            assertEquals(e.getMessage(), "Invalid type [nmeric] for attribute [weight]");
            return;
        }
        fail("exception was not thrown");
    }
    
    @Test
    public void testDuplicateName() {
        try{
            DataHeader dataHeader = new DataHeader("age", "numeric", "age", "nominal");
        }catch(RuntimeException e){
            assertEquals(e.getMessage(), "Cannot reuse attribute names: age");
            return;
        }
        fail("exception was not thrown");
    }
    
    @Test
    public void testNumAttributes() {
        DataHeader dataHeader = new DataHeader("age", "nominal", "weight", "numeric");
        assertEquals(2, dataHeader.numAttributes());
    }
    
    @Test
    public void testGetIndexByName() {
        DataHeader dataHeader = new DataHeader("weight", "nominal", "height", "nominal", "age", "numeric");
        assertEquals(dataHeader.getAttributeIndexByName("age"), 2);
    }
    
    @Test
    public void testGetTypeByName() {
        DataHeader dataHeader = new DataHeader("weight", "nominal", "height", "nominal", "age", "numeric");
        assertEquals(dataHeader.getAttributeTypeByName("age"), "numeric");
    }
    
    @Test
    public void testGetNameByIndex() {
        DataHeader dataHeader = new DataHeader("weight", "nominal", "height", "nominal", "age", "numeric");
        assertEquals(dataHeader.getAttributeNameByIndex(2), "age");
    }
    
    @Test
    public void testGetTypeByIndex() {
        DataHeader dataHeader = new DataHeader("weight", "nominal", "height", "nominal", "age", "numeric");
        assertEquals(dataHeader.getAttributeTypeByIndex(2), "numeric");
    }

}
