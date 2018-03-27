/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mlbean.shell;

import com.mlbean.dataObjects.DataSet;
import java.util.HashMap;
import java.util.LinkedList;

/**
 *
 * @author john.tompkins
 */
public class Environment {
    
    LinkedList<Command> history;
    HashMap<String, DataSet> variables;
    
    public Environment() {
        history = new LinkedList<>();
        variables = new HashMap<>();
    }
    
    public void appendHistory(Command cmd) {
        history.push(cmd);
    }
    
    public HashMap<String, DataSet> getVariables() {
        return variables;
    }
}
