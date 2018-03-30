/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mlbean.dataObjects;

/**
 *
 * @author john
 */
public class DataPrinter {
    
    private DataSet data;
    private DataHeader header;
    private StringBuilder table;
    
    public DataPrinter(DataSet data) {
        this.data = data;
        this.header = data.getHeader();
    }
    
    //TODO refactor and make this work for non-labelled data sets
    public String print() {
        table = new StringBuilder();
        int[] colWidths = getTableWidths(data);
        int tableWidth = 0;
        
        String prefix = "";
        for(int i = 0; i < colWidths.length; i++) {
            tableWidth += colWidths[i] + 3;
            table.append(prefix).append(rightPad(header.getAttributeNameByIndex(i), colWidths[i]));
            prefix = " | ";
        }
        table.append("\n");
        for(int i = 0; i < tableWidth; i++) {
            table.append("=");
        }
        table.append("\n");
        for(DataRow d : data) {
            prefix = "";
            for(int i = 0; i < colWidths.length - 1; i++) {
                table.append(prefix).append(rightPad(d.getNonLabel(i).toString(), colWidths[i]));
                prefix = " | ";
            }
            table.append(prefix).append(rightPad(d.getLabel().toString(), colWidths[colWidths.length - 1]));
            table.append("\n");
        }
        return table.toString();
    }
    
    private int[] getTableWidths(DataSet set) {
        int[] colWidths = new int[header.numAttributes()];
        for(int i = 0; i < header.numAttributes() - 1; i++) {
            colWidths[i] = header.getAttributeNameByIndex(i).length();
        }
        for(DataRow d : set) {
            for(int i = 0; i < colWidths.length - 1; i++) {
                colWidths[i] = Math.max(colWidths[i], d.getNonLabel(i).toString().length());
            }
            colWidths[colWidths.length - 1] = Math.max(colWidths[colWidths.length - 1], d.getLabel().toString().length());
        }
        return colWidths;
    }
    
    private String rightPad(String word, int length) {
        int diff = length - word.length();
        StringBuilder sBuf = new StringBuilder();
        for(int i = 0 ; i < diff; i++) {
            sBuf.append(" ");
        }
        return word + sBuf.toString();
    }
    
}
