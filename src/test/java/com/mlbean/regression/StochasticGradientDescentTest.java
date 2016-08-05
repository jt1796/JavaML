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
package com.mlbean.regression;

import com.mlbean.dataObjects.DataElement;
import com.mlbean.dataObjects.DataHeader;
import com.mlbean.dataObjects.DataRow;
import com.mlbean.dataObjects.DataSet;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author John
 */
public class StochasticGradientDescentTest {
    
    public StochasticGradientDescentTest() {
    }

    @Test
    public void TwoDimLinear() {
        DataHeader header = new DataHeader("input", "numeric", "output", "numeric");
        DataSet set = new DataSet(header, "output");
        set.addRow(new DataRow(new DataElement[]{new DataElement(3)}, new DataElement(6)));
        set.addRow(new DataRow(new DataElement[]{new DataElement(5)}, new DataElement(10)));
        set.addRow(new DataRow(new DataElement[]{new DataElement(10)}, new DataElement(20)));
        Regression stoch = new StochasticGradientDescent(set, 10000, .01);
        stoch.execute();
        assertEquals(2, stoch.getCoeffs().get(1), 0.01);
    }
    
    @Test
    public void FourDimNoisy() {
        DataHeader header = new DataHeader("input1", "numeric", "input2", "numeric", "input3", "numeric", "input4", "numeric", "output", "numeric");
        DataSet set = new DataSet(header, "output");
        set.addRow(new DataRow(new DataElement[]{new DataElement(1), new DataElement(1), new DataElement(1), new DataElement(1)}, new DataElement(24 + 3)));
        set.addRow(new DataRow(new DataElement[]{new DataElement(2), new DataElement(2), new DataElement(2), new DataElement(2)}, new DataElement(48 + 3)));
        set.addRow(new DataRow(new DataElement[]{new DataElement(3), new DataElement(1), new DataElement(6), new DataElement(1)}, new DataElement(53 + 3)));
        set.addRow(new DataRow(new DataElement[]{new DataElement(12), new DataElement(-3), new DataElement(3), new DataElement(8)}, new DataElement(150 + 3)));
        set.addRow(new DataRow(new DataElement[]{new DataElement(7), new DataElement(19), new DataElement(23), new DataElement(51)}, new DataElement(672 + 3)));
        Regression stoch = new StochasticGradientDescent(set, 80000, .001);
        stoch.execute();
        assertEquals(3, stoch.getCoeffs().get(0), 0.01);
        assertEquals(7, stoch.getCoeffs().get(1), 0.01);
        assertEquals(5, stoch.getCoeffs().get(2), 0.01);
        assertEquals(3, stoch.getCoeffs().get(3), 0.01);
        assertEquals(9, stoch.getCoeffs().get(4), 0.01);
    }
}
