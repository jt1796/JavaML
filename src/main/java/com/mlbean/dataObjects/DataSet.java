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

import com.mlbean.mathObjects.Matrix;
import com.mlbean.mathObjects.Vector;
import java.util.LinkedList;
import java.util.Iterator;

/**
 *
 * @author John
 */
public class DataSet implements Iterable<DataRow> {
    DataHeader header = null;
    LinkedList<DataRow> data = null;
    
    public DataSet(DataHeader hdr) {
        this.header = hdr;
    }
    
    public void addRow() {
        
    }
    
    public void addData(double response, double[] data) {
        if(data.length + 1 != numberOfIndependentsPlusBase) {
            throw new RuntimeException("Wrong number of inputs");
        }
        double[] extendedData = new double[data.length + 1];
        extendedData[0] = 1;
        System.arraycopy(data, 0, extendedData, 1, data.length);
        DataRow element = new DataRow(response, extendedData);
        this.data.add(element);
        dataSize++;
    }
    
    public Iterator<DataRow> iterator() {
        return data.iterator();
    }
    
    public int getDataWidth() {
        return header.numAttributes();
    }
    
    public Matrix asMatrix() {
        double[][] matData = new double[dataSize][numberOfIndependentsPlusBase];
        int ctr = 0;
        for(DataRow d : this) {
            for(int c = 0; c < numberOfIndependentsPlusBase; c++) {
                matData[ctr][c] = d.getIth(c);
            }
            ctr++;
        }
        return new Matrix(matData);
    }
    
    public Vector responseVector() {
        double[] vecData = new double[dataSize];
        int i = 0;
        for(DataRow row : this) {
            vecData[i++] = row.getResponse();
        }
        return new Vector(dataSize, vecData);
    }
}
