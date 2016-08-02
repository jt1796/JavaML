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
public class StochasticGradientDescent extends Regression {

    public StochasticGradientDescent(DataSet dataSet, int iterations, double stepSize) {
        super(dataSet, iterations, stepSize);
    }
    
    public void execute() {
        double[] t_coeff = new double[dataSet.getVarSpan()];
        for(int i = 0; i < iterations; i++) {
            for(DataRow dataRow : dataSet) {
                for(int omega = 0; omega < t_coeff.length; omega++) {
                    t_coeff[omega] = t_coeff[omega] - stepSize * dataRow.getIth(omega) * 
                            (dataRow.independenceAsVector().dotProduct(new Vector(t_coeff.length, t_coeff)) - dataRow.getResponse());
                }
            }
        }
        coeff = new Vector(t_coeff.length, t_coeff);
    }
}
