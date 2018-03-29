/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mlbean.shell;

import java.io.IOException;
import java.io.Console;
import java.util.Scanner;

/**
 *
 * @author john.tompkins
 */
public class Command {

    InputStrategy inputStrategy;
    String cmdTxt;
    String stdout;

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

    public void fetch() throws IOException {
        if (inputStrategy == InputStrategy.STDIN) {
            Scanner scan = new Scanner(System.in);
            this.cmdTxt = scan.nextLine();
            scan.close();
        }
    }

    public void exec(Environment env) {
        env.appendHistory(this);
        Exec e = new Exec(env, cmdTxt);
        this.stdout = e.run();
    }

}
