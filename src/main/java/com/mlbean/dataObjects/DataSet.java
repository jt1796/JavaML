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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 *
 * @author John
 */
public class DataSet implements Iterable<DataRow> {
    private DataHeader header = null;
    private Collection<DataRow> data = null;
    private String[] orderedNonLabels = null;
    private String[] orderedAttributes = null;
    private boolean isLabelled = false;
    private String labelName = null;
    
    public DataSet(DataHeader hdr) {
        this(hdr, true);
    }
    
    public DataSet(DataHeader hdr, boolean isLabelled) {
        this.header = hdr;
        this.isLabelled = isLabelled;
        orderedNonLabels = new String[header.numAttributes() - 1];
        orderedAttributes = new String[header.numAttributes()];
        data = new ArrayList<>();
        for(int i = 0; i < header.numAttributes(); i++) {
            String name = header.getAttributeNameByIndex(i);
            if(i != header.numAttributes() - 1 || !isLabelled) {
                orderedNonLabels[i] = name;
            } else {
                labelName = name;
            }
            orderedAttributes[i] = name;
        }
    }
    
    public void addRow(DataRow row) {
        if (header.numAttributes() != row.numAttributes()) {
            throw new RuntimeException("this row does not have the right number of attributes");
        }
        if (isLabelled != row.isLabelled()) {
            throw new RuntimeException("mismatch in isLabelled for DataSet and DataRow");
        }
        if (isLabelled) {
            String labelType = header.getAttributeTypeByName(labelName);
            String rowLabelType = row.getLabel().dataType();
            if (!labelType.equals(rowLabelType)) {
                throw new RuntimeException("this row has a type mismatch in the label");
            }
        }
        int nonLabelIndex = 0;
        for(int i = 0; i < header.numAttributes(); i++) {
            String currentAttributeName = header.getAttributeNameByIndex(i);
            if(!isLabelled || !currentAttributeName.equals(labelName)) {
                String currentNonLabelType = row.getNonLabel(nonLabelIndex).dataType();
                if(!currentNonLabelType.equals(header.getAttributeTypeByIndex(i))) {
                    throw new RuntimeException("this row has a type mismatch in " + header.getAttributeNameByIndex(i));
                }
                nonLabelIndex++;
            }
        }
        data.add(row);
    }
    
    public Iterator<DataRow> iterator() {
        return data.iterator();
    }
    
    public int getDataWidth() {
        return header.numAttributes();
    }
    
    public int getDataHeight() {
        return data.size();
    }
    
    public Matrix nonLabelsAsMatrix() {
        DataRow[] dataArr = data.toArray(new DataRow[getDataHeight()]);
        double[][] matData = new double[getDataHeight()][getDataWidth() - 1];
        for(int r = 0; r < getDataHeight(); r++) {
            Vector row = dataArr[r].nonLabelsAsVector();
            for(int c = 0; c < getDataWidth() - 1; c++) {
                matData[r][c] = row.get(c);
            }
        }
        return new Matrix(matData);
    }
    
    public Vector labelsAsVector() {
        if (!isLabelled) {
            throw new RuntimeException("this DataSet has no labels");
        }
        DataRow[] dataArr = data.toArray(new DataRow[getDataHeight()]);
        double[] vecData = new double[getDataHeight()];
        for(int i = 0; i < getDataHeight(); i++) {
            vecData[i] = dataArr[i].getLabel().getNumericValue();
        }
        return new Vector(getDataHeight(), vecData);
    }

    public void switchBacking(Collection<DataRow> e) {
        if (!e.isEmpty()) {
            throw new RuntimeException("collection must be empty");
        }
        e.addAll(data);
        data = e;
    }
    
    public String toString() {
        return new DataPrinter(this).print();
    }
    
    public DataHeader getHeader() {
        return this.header;
    }
}
