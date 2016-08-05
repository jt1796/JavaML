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

import com.mlbean.dataObjects.DataRow;
import com.mlbean.dataObjects.DataSet;
import com.mlbean.mathObjects.Vector;

/**
 *
 * @author John
 */
public abstract class Regression {
    protected double stepSize = 0;
    protected int iterations = 0;
    protected Vector coeff = null;
    protected DataSet dataSet = null;
    
    public Regression(DataSet dataSet, int iterations, double stepSize) {
        this.stepSize = stepSize;
        this.iterations = iterations;
        this.dataSet = dataSet;
    }
    
    public abstract void execute();
    
    public Vector getCoeffs() {
        if(null == coeff) {
            throw new RuntimeException("Must call execute() before this call");
        }
        return coeff;
    }
    
    protected double modDotProduct(double coeff_zero, double[] t_coeff, DataRow datarow) {
        return coeff_zero + new Vector(t_coeff.length, t_coeff).dotProduct(datarow.nonLabelsAsVector());
    }
}
