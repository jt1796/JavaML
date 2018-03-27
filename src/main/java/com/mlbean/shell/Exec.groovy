/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mlbean.shell

/**
 *
 * @author john.tompkins
 */
class Exec {
    
    Environment env
    String cmd
    
    public Exec(Environment env, String cmd) {
        this.env = env
        this.cmd = cmd
    }
    
    def execTable = [
        "help": [this.&help],
        "load": [this.&load, "name", "filename"]
    ]
    
    public String run() {
        List<String> tokens = cmd.tokenize()
        List<String> args = tokens.drop(1)
        def method = tokens.get(0)
        def entry = execTable[method]
        if (entry == null) {
            return "command not found: ${method}"
        }
        return method(args)
    }
    
    private String load(String name, String fp) {
        env.getVariables()[name] = fp
    }
    
    private String help() {
        String msg = "usage:"
        execTable.each { entry ->
            msg += "\n  " + entry
            List<String> args = entry.val.drop(1)
            msg += args.collect { "  --$it" }
        }
        return msg
    }
}

