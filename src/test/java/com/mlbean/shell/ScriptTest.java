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
public class ScriptTest {
    
    @Test
    public void testWine() {
        String script = "load wine wine.data Alcohol\ntest wine wine";
        Script scr = new Script(script);
        assertTrue(scr.run().contains("0.21778922180607593 average miss"));
    }
    
}
