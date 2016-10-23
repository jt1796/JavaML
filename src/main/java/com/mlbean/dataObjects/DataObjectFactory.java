package com.mlbean.dataObjects;

/**
 * Created by jtompkins on 10/21/16.
 */
public class DataObjectFactory {

    public DataObjectFactory() {

    }

    public DataRow genRow(Object... args) {
        return new DataRow(genElementArray(args));
    }

    public DataElement[] genElementArray(Object... args) {
        DataElement[] arr = new DataElement[args.length];
        for(int i = 0; i < arr.length; i++) {
            arr[i] = genElement(args[i]);
        }
        return arr;
    }

    public DataElement genElement(Object e) {
        if(e instanceof String) {
            return new DataElement((String) e);
        }
        if(e instanceof Double) {
            return new DataElement((Double) e);
        }
        if(e instanceof Integer) {
            return new DataElement((Integer) e);
        }
        return null;
    }
}
