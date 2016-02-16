/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaml.mathObjects;

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
        try{
        for(r = 0; r < newheight; r++) {
            for(c = 0; c < newwidth; c++) {
                int dotprod = 0;
                for(i = 0; i < dotprodlength; i++) {
                    dotprod += this.get(r, i) * matrix.get(i, c);
                }
                newdata[r][c] = dotprod;
            }
        }   
        }catch(Exception e){
            System.out.println(r + "x" + c + "x" + i);
        }
        return new Matrix(newdata);
    }
    
    public Matrix ReducedRowEchelonForm(Matrix re) {
        //start at mid bottow right and work to top left.
        //add on a multiple to each row above to zero out.
        return null;
    }
    
    public Matrix RowEchelonForm(Matrix matrix) {
        //start at top left, work down in diagonal.
        //if element is zero, swap with nonzero row below.
        //for all rows below, add on multiple to create zero in that row.
        return null;
    }
    
    public Matrix inverse() {
        //augment this matrix with identity on the right. 
        //Then convert to RowEchelon
        //Lastly, scrape the right half.
        return null;
    }
    
    public double trace() {
        int trace = 0;
        for (int i = 0; i < height; i++) {
            trace += get(i, i);
        }
        return trace;
    }
    
    public Matrix multiply(Vector vector) {
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
    
    public String toString() {
        //get the longest number in the matrix. 
        return "TODO";
    }
}
