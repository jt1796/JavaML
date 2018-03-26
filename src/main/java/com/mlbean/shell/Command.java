/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mlbean.shell;

/**
 *
 * @author john.tompkins
 */
public class Command {
    
    InputStrategy inputStrategy;
    String cmdTxt;
    
    public Command(InputStrategy inputStrategy) {
        this.inputStrategy = inputStrategy;
    }
    
    public void setTxt(String txt) {
        this.cmdTxt = txt;
    }
    
    public enum InputStrategy {
        STDIN,
        BUFFER
    }
    
    public void fetch() {
        if (inputStrategy == InputStrategy.STDIN) {
            this.cmdTxt = "block on terminal io";
        }
    }
    
    public void exec(Environment env) {
        env.appendHistory(this);
        // parse and exec
    }
    
}
