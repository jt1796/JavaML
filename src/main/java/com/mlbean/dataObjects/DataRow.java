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

import com.mlbean.mathObjects.Vector;

/**
 *
 * @author John
 */
public class DataRow {
    private final DataElement[] nonLabels;
    private final boolean isLabelled;
    private final DataElement label;
    
    public DataRow(DataElement[] attributes) {
        this(attributes, true);
    }
    
    public DataRow(DataElement[] attributes, boolean isLabelled) {
        this.isLabelled = isLabelled;
        this.label = attributes[attributes.length - 1];
        nonLabels = new DataElement[attributes.length - 1];
        System.arraycopy(attributes, 0, nonLabels, 0, nonLabels.length);
    }

    public DataRow(DataElement[] nonLabels, DataElement label) {
        this.nonLabels = new DataElement[nonLabels.length];
        this.isLabelled = true;
        System.arraycopy(nonLabels, 0, this.nonLabels, 0, nonLabels.length);
        this.label = label;
    }
    
    public boolean isLabelled() {
        return isLabelled;
    }
    
    public DataElement getLabel() {
        if (!isLabelled) {
            throw new RuntimeException("this DataRow is not labelled");
        }
        return label;
    }
    
    public DataElement getNonLabel(int i) {
        return nonLabels[i];
    }

    public DataElement[] getNonLabels() {
        DataElement[] copy = new DataElement[nonLabels.length];
        System.arraycopy(nonLabels, 0, copy, 0, copy.length);
        return copy;
    }
    
    public DataElement getAttribute(int i) {
        if(isLabelled && i == nonLabels.length + 1) {
            return label;
        }
        return nonLabels[i];
    }
    
    public int numAttributes() {
        if (isLabelled) {
            return nonLabels.length + 1;
        } else {
            return nonLabels.length;
        }
    }
    
    public Vector nonLabelsAsVector() {
        double[] numericArray = new double[nonLabels.length];
        for(int i = 0; i < numericArray.length; i++) {
            if(!"numeric".equals(nonLabels[i].dataType())) {
                throw new RuntimeException("This DataRow contains nominal data");
            }
            numericArray[i] = nonLabels[i].getNumericValue();
        }
        return new Vector(nonLabels.length, numericArray);
    }
}
