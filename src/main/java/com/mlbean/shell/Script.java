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
public class Script {
    
    String prog;
        
    public Script(String prog) {
        this.prog = prog;
    }
    
    public String run() {
        Environment env = new Environment();
        String result = "";
        for (String line : prog.split("\n")) {
            Command cmd = new Command(Command.InputStrategy.BUFFER);
            cmd.setTxt(line);
            cmd.exec(env);
            result += cmd.stdout;           
        }
        return result;
    }
}
