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
package com.mlbean.mathObjects;

/**
 *
 * @author John
 */
public class Matrix {
    private final int width;
    private final int height;
    private final double[][] data;
    
    public Matrix(Matrix matrix) {
        this.width = matrix.getWidth();
        this.height = matrix.getHeight();
        data = new double[height][width];
        for(int r = 0; r < height; r++) {
            for(int c = 0; c < width; c++) {
                data[r][c] = matrix.get(r, c);
            }
        }
    }
    
    public Matrix(double[][] data) {
        height = data.length;
        width = data[0].length;
        this.data = data;
    }
    
    public Matrix(int height, int width, double[] data) {
        this.height = height;
        this.width = width;
        this.data = new double[height][width];
        for(int r = 0; r < height; r++) {
            for(int c = 0; c < width; c++) {
                this.data[r][c] = data[r * width + c];
            }
        }
    }
    
    public Matrix transpose() {
       double[][] newdata = new double[width][height];
       for(int r = 0; r < height; r++) {
           for(int c = 0; c < width; c++) {
               newdata[c][r] = data[r][c];
           }
       }
       return new Matrix(newdata);
    }
    
    public Matrix multiply(int number) {
        double[][] newdata = new double[height][width];
        for(int r = 0; r < height; r++) {
            for(int c = 0; c < width; c++) {
                newdata[r][c] = number * data[r][c];
            }
        }
        return new Matrix(newdata);
    }
    
    public Matrix multiply(Matrix matrix) {
        int newwidth = matrix.width;
        int newheight = this.height;
        int dotprodlength = this.width;
        double[][] newdata = new double[newheight][newwidth];
        int c = 0, r = 0, i = 0;
        for(r = 0; r < newheight; r++) {
            for(c = 0; c < newwidth; c++) {
                int dotprod = 0;
                for(i = 0; i < dotprodlength; i++) {
                    dotprod += this.get(r, i) * matrix.get(i, c);
                }
                newdata[r][c] = dotprod;
            }
        }   
        return new Matrix(newdata);
    }
    
    public Matrix ReducedRowEchelonForm() {
        double[][] newdata = data;
        for(int r = height - 1; r >= 0; r--) {
            if(newdata[r][r] != 0.0){
                for(int upperrow = r - 1; upperrow >= 0; upperrow--) {
                    double addfactor = (-1) * newdata[upperrow][r] / newdata[r][r];
                    for(int c = r; c < width; c++) {
                        newdata[upperrow][c] += newdata[r][c] * addfactor;
                    }
                }
            }
        }
        return new Matrix(newdata);
    }
    
    public Matrix RowEchelonForm() {
        double[][] newdata = data;
        for(int r = 0; r < height; r++) {
            if(get(r, r) == 0) {
                int i = r + 1;
                while(i < height && newdata[i][r] != 0) {
                    i++;
                }
                double[] tmp = newdata[r];
                newdata[r] = newdata[i];
                newdata[i] = tmp;
            }
            double mag = newdata[r][r];
            for(int c = 0; c < width; c++) {
                newdata[r][c] = newdata[r][c] / mag;
            }
            for(int lowerrow = r + 1; lowerrow < height; lowerrow++) {
                double addfactor = (-1) * newdata[lowerrow][r] / newdata[r][r];
                for(int c = r; c < width; c++) {
                    newdata[lowerrow][c] += newdata[r][c] * addfactor;
                }
            }
        }
        return new Matrix(newdata);
    }
    
    public Matrix inverse() {
        double[][] newdata = new double[height][width * 2];
        for(int r = 0; r < height; r++) {
            for(int c = 0; c < width; c++) {
                newdata[r][c] = get(r, c);
            }
            newdata[r][width + r] = 1;
        }
        newdata = new Matrix(newdata).RowEchelonForm().ReducedRowEchelonForm().data;
        double[][] finaldata = new double[height][width];
        for(int r = 0; r < height; r++) {
            for(int c = 0; c < width; c++) {
                finaldata[r][c] = newdata[r][c + width];
            }
        }
        return new Matrix(finaldata);
    }
    
    public double trace() {
        int trace = 0;
        for (int i = 0; i < height; i++) {
            trace += get(i, i);
        }
        return trace;
    }
    
    public Matrix multiply(Vector vector) {
        //TODO
        return null;
    }
    
    public double get(int row, int col) {
        return data[row][col];
    }
    
    public int getHeight() {
        return height;
    }
    
    public int getWidth() {
        return width;
    }
    
    @Override
    public boolean equals(Object other) {
        if(this == other) {
            return true;
        }
        if(!(other instanceof Matrix)) {
            return false;
        }
        Matrix mat = (Matrix) other;
        if(height == mat.getHeight() && width == mat.getWidth()) {
        } else {
            return false;
        }
        for(int r = 0; r < height; r++) {
            for(int c = 0; c < width; c++) {
                if(!(Math.abs(get(r, c) - mat.get(r, c)) < .0001)) {
                    return false;
                }
            }
        }
        return true;
    }
    
    public String toString() {
        String buffer = "";
        for(int r = 0; r < height; r++) {
            buffer += "[ ";
            for(int c = 0; c < width; c++) {
                buffer += " " + get(r, c);
            }
            buffer += "]\n";
        }
        return buffer;
    }
}
