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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 *
 * @author John
 */
public class DataHeader {
    private ArrayList<DataSpot> header = new ArrayList<>();
    private HashMap<String, Integer> indexLookup = new HashMap<>();
    
    public DataHeader(String... namesAndTypes) {
        if(namesAndTypes.length < 4) {
            throw new RuntimeException("There must be at least two attributes");
        }
        if (namesAndTypes.length % 2 == 1) {
            throw new RuntimeException("parity of arguments must be even");
        }
        for(int i = 0; i < namesAndTypes.length; i += 2) {
            String name = namesAndTypes[i];
            String type = namesAndTypes[i + 1];
            if (indexLookup.containsKey(name)) {
                throw new RuntimeException("Cannot reuse attribute names: " + name);
            }
            if (!("nominal".equals(type) || "numeric".equals(type))) {
                throw new RuntimeException("Invalid type for attribute with name: " + name);
            }
            indexLookup.put(name, i / 2);
            header.add(new DataSpot(namesAndTypes[i], namesAndTypes[i + 1]));
        }
    }
    
    public int numAttributes() {
        return header.size();
    }
    
    public int getAttributeIndexByName(String name) {
        return indexLookup.get(name);
    }
    
    public String getAttributeTypeByName(String name) {
        int index = indexLookup.get(name);
        return header.get(index).type;
    }
    
    public String getAttributeNameByIndex(int index) {
        return header.get(index).name;
    }
    
    public String getAttributeTypeByIndex(int index) {
        return header.get(index).type;
    }
    
    private class DataSpot {
        String type = "";
        String name = "";
        
        public DataSpot(String name, String type) {
            this.type = type;
            this.name = name;
        }
    }
}
