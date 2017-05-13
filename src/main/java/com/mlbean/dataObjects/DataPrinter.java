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
        int tableWidth = getTableWidth(set);
        
        String prefix = "";
        for(int i = 0; i < colWidths.length - 1; i++) {
            table.append(prefix).append(rightPad(orderedNonLabels[i], colWidths[i]));
            prefix = " | ";
        }
        table.append(prefix).append(rightPad(labelName, colWidths[orderedNonLabels.length - 1]));
        table.append("\n");
        for(int i = 0; i < tableWidth; i++) {
            table.append("=");
        }
        table.append("\n");
        for(DataRow d : set) {
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
    
    private int getTableWidth(DataSet set) {
        int tableWidth = 0;
        int[] colWidths = new int[header.numAttributes()];
        for(int i = 0; i < header.numAttributes() - 1; i++) {
            colWidths[i] = orderedNonLabels[i].length();
        }
        colWidths[header.numAttributes() - 1] = this.labelName.length();
        for(DataRow d : set) {
            for(int i = 0; i < colWidths.length - 1; i++) {
                colWidths[i] = Math.max(colWidths[i], d.getNonLabel(i).toString().length());
            }
            colWidths[colWidths.length - 1] = Math.max(colWidths[colWidths.length - 1], d.getLabel().toString().length());
        }
        for(int i : colWidths) {
            tableWidth += i + 3;
        }
        tableWidth -= 3;
        return tableWidth;
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
