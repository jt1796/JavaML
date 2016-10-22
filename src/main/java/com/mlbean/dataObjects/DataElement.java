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

/**
 *
 * @author John
 */
public class DataElement {
    private String nominalValue = null;
    private double numericValue = 0;
    private boolean numeric = false;
    
    public DataElement(String val) {
        this.nominalValue = val;
        this.numeric = false;
    }
    
    public DataElement(double val) {
        this.numericValue = val;
        this.numeric = true;
    }
    
    public String dataType() {
        if(numeric) {
            return "numeric";
        }
        return "nominal";
    }
    
    public String getNominalValue() {
        if (numeric) {
            throw new RuntimeException("This is not a nominal DataElement");
        }
        return nominalValue;
    }
    
    public double getNumericValue() {
        if(!numeric) {
            throw new RuntimeException("This is not a numeric DataElement");
        }
        return numericValue;
    }
    
    public String toString() {
        if(numeric) {
            return "" + numericValue;
        }
        return nominalValue;
    }
    
    public boolean equals(Object other) {
        if (other instanceof DataElement) {
            DataElement e = (DataElement) other;
            if(e.hashCode() == this.hashCode()) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        if(!numeric) {
            return nominalValue.hashCode();
        } else {
            return new Double(numericValue).hashCode();
        }
    }
    
    private boolean doubleEquals(Double d1, Double d2) {
        double diff = d1.doubleValue() - d2.doubleValue();
        return Math.abs(diff) < 0.000001;
    }
}
