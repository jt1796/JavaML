/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mlbean.shell

import com.mlbean.clustering.Classifier
import com.mlbean.dataObjects.DataHeader
import com.mlbean.dataObjects.DataPrinter
import com.mlbean.dataObjects.DataSet

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
        "env": [this.&env],
        "print": [this.&print, "dataset-name"],
        "load": [this.&load, "dataset-name", "filename"],
        "train": [this.&train, "[classier|clusterer|regression]", "datase-name"]
    ]

    public String run() {
        List<String> tokens = cmd.tokenize()
        List<String> args = tokens.drop(1)
        def method = tokens.get(0)
        def entry = execTable[method]
        if (entry == null) {
            return "command not found: ${method}"
        }
        if (args.size() + 1 != entry.size()) {
            return help()
        }
        return entry[0](*args)
    }
    
    private String train(String type, String dataset) {
        DataSet toTrainOn = env.getVariables()[dataset]
        if (toTrainOn) {
            return "dataset $dataset not in environment"
        }
    }

    private String load(String name, String fp) {
        DataHeader dh = null
        FSLoader fs = new FSLoader();
        if (fs.headerExists(fp)) {
            dh = fs.getHeader(fp)
        }
        DataSet ds = fs.load(fp, dh)
        env.getVariables()[name] = ds
    }

    private String env() {
        return env.getVariables().collect {
            "  ${it.key} DATASET"
        }.join("\n")
    }
    
    private String print(String ds) {
        if (null == env.getVariables()[ds]) {
            return "no dataset $ds loaded"
        }
        return env.getVariables()[ds].toString()
    }

    private String help() {
        String msg = "usage:"
        execTable.each { entry ->
            msg += "\n  " + entry.key
            List<String> args = entry.value.drop(1)
            args.each { msg += " --$it" }
        }
        return msg
    }
}
