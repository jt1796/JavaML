/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mlbean.shell;

import java.io.IOException;

/**
 *
 * @author john.tompkins
 */
public class main {
    public static void main(String[] args) {
        if (args.length == 2 && "script".equals(args[0])) {
            // script script = new Script()
            // take in string or a file name? 
            String scriptTxt = new FSLoader().loadScript(args[1]);
            Script script = new Script(scriptTxt);
            System.out.println(script.run());
            return;
        }
        Environment env = new Environment();
        System.out.println("Welcome to MLBean");
        while (true) {
            Command cmd = new Command(Command.InputStrategy.STDIN);
            try {
                cmd.fetch();
                cmd.exec(env);
                System.out.println(cmd.stdout);
            } catch(IOException e) {
                System.out.println("Error reading from stdin");
            }
        }
    }
}
