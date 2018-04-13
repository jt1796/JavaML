/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mlbean.shell;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author john.tompkins
 */
public class CommandTest {
    
    public CommandTest() {
    }
    
    @Test
    public void testLoad() throws Exception {
        Command cmd = new Command(Command.InputStrategy.BUFFER);
        cmd.setTxt("load wine wine.data Alcohol");
        cmd.fetch();
        Environment env = new Environment();
        cmd.exec(env);
        assertEquals(env.variables.size(), 1);
    }    
}
