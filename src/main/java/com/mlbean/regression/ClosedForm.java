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
import com.mlbean.mathObjects.Matrix;
import com.mlbean.mathObjects.Vector;

/**
 *
 * @author John
 */
public class ClosedForm extends Regression {
    
    public ClosedForm(DataSet dataSet, int iterations, double stepSize) {
        super(dataSet, iterations, stepSize);
    }
    
    public void execute() {
        //need to augment the matrix with ones.
        Matrix x = this.dataSet.nonLabelsAsMatrix();
        x = this.augmentMatrixWithOnes(x);
        Matrix x_transpose = x.transpose();
        Vector y = this.dataSet.labelsAsVector();
        Matrix result = x_transpose.multiply(x).inverse().multiply(x_transpose).multiply(dataSet.labelsAsVector());
        double[] coeffData = new double[dataSet.getDataWidth()];
        for(int i = 0; i < coeffData.length; i++) {
            coeffData[i] = result.get(i, 0);
        }
        this.coeff = new Vector(dataSet.getDataWidth(), coeffData);
    }
    
    private Matrix augmentMatrixWithOnes(Matrix mat) {
        int width = mat.getWidth() + 1;
        int height = mat.getHeight();
        double[][] newMatData = new double[height][width];
        for(int i = 0; i < height; i++) {
            newMatData[i][0] = 1;
            for(int j = 1; j < width; j++) {
                newMatData[i][j] = mat.get(i, j - 1);
            }
        }
        return new Matrix(newMatData);
    }
}
