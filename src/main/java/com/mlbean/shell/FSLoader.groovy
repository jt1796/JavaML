/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mlbean.shell

import com.mlbean.dataObjects.DataElement
import com.mlbean.dataObjects.DataHeader
import com.mlbean.dataObjects.DataRow
import com.mlbean.dataObjects.DataSet

/**
 *
 * @author john.tompkins
 */
class FSLoader {
    
    public boolean headerExists(String fp) {
        try {
            getHeader(fp)
            return true
        } catch(e) {
            println e
            // swallow
        }
        return false
    }
        
    public DataHeader getHeader(String fp) {
        List<String> firstLine
        new File(fp).withReader { firstLine = it.readLine().tokenize(',') }
        DataHeader dh = new DataHeader(*firstLine)
        return dh
    }
    
    // handle labeled?
    public DataSet load(String fp, DataHeader dh) {
        File file = new File(fp)
        DataSet dataSet = new DataSet(dh);
        boolean firstLine = true
        file.eachLine { String line ->
            if (firstLine) {
                firstLine = false
                return
            }
            List<String> elements = line.tokenize(',')
            dh.numAttributes().times {
                if (dh.getAttributeTypeByIndex(it) == "numeric") {
                    elements[it] = elements[it].toDouble()
                }
            }
            DataRow row = new DataRow((DataElement[]) elements.collect { it -> new DataElement(it) }.toArray())
            dataSet.addRow(row);
        }
        return dataSet;
    }
}

