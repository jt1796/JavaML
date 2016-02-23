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

import com.mlbean.dataObjects.DataSet;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author John
 */
public class ClosedFormTest {
    
    public ClosedFormTest() {
    }

    @Test
    public void TwoDimLinear() {
        DataSet set = new DataSet(1);
        set.addData(6, new double[]{3});
        set.addData(8, new double[]{4});
        set.addData(20, new double[]{10});
        Regression batch = new ClosedForm(set, 1000, .01);
        batch.execute();
        assertEquals(2, batch.getCoeffs().get(1), 0.01);
    }
    
    @Test
    public void FourDimNoisy() {
        DataSet set = new DataSet(4);
        set.addData(24 + 3, new double[]{1, 1, 1, 1});
        set.addData(48 + 3, new double[]{2, 2, 2, 2});
        set.addData(53 + 3, new double[]{3, 1, 6, 1});
        set.addData(150 + 3, new double[]{12, -3, 3, 8});
        set.addData(672 + 3, new double[]{7, 19, 23, 51});
        Regression batch = new ClosedForm(set, 50000, .0005);
        batch.execute();
        assertEquals(3, batch.getCoeffs().get(0), 0.01);
        assertEquals(7, batch.getCoeffs().get(1), 0.01);
        assertEquals(5, batch.getCoeffs().get(2), 0.01);
        assertEquals(3, batch.getCoeffs().get(3), 0.01);
        assertEquals(9, batch.getCoeffs().get(4), 0.01);
    }
    
}