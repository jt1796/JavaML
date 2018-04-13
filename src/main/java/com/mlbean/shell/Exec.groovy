/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mlbean.shell

import com.mlbean.clustering.Classifier
import com.mlbean.clustering.Clusterer
import com.mlbean.clustering.KNN
import com.mlbean.dataObjects.DataElement
import com.mlbean.dataObjects.DataHeader
import com.mlbean.dataObjects.DataPrinter
import com.mlbean.dataObjects.DataRow
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
        "load": [this.&load, "dataset-name", "filename", "label"],
        "train": [this.&train, "[classier|clusterer|regression]", "datase-name"],
        "test": [this.&kFold, "type", "dataset"]
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
    
    private String kFold(String type, String dataset) {
        if (!env.getVariables().containsKey(dataset)) {
            return "No dataset $dataset loaded!"
        }
        DataSet toTrainOn = env.getVariables()[dataset]
        boolean isNominal = toTrainOn.getHeader().getAttributeTypeByIndex(toTrainOn.getHeader().numAttributes() - 1) == "nominal"
        double score = 0.0
        List<DataSet> sets = toTrainOn.chunk(10)
        for (DataSet set : sets) {
            Classifier c = new KNN();
            c.train(set)
            for (DataSet other : sets) {
                if (other == set) {
                    continue
                }
                for (DataRow dr : other) {
                    DataElement prediction = c.predict(*dr.getNonLabels(), dr.getLabel())
                    DataElement actual = dr.getLabel()
                    if (isNominal && prediction.equals(actual)) {
                        score += 1;
                    }
                    if (!isNominal) {
                        double diff = Math.abs(prediction.getNumericValue() - actual.getNumericValue())
                        score += diff
                    }
                }
            }
        }
        if (isNominal) {
            return """
                10-fold validation summary:
                ${100 * 10 * score / toTrainOn.size() } percent accuracy
            """
        }
        return """
            10-fold validation summary:
            ${score / (9 * 8 * toTrainOn.size()) } average miss
        """
    }
    
    private String train(String type, String dataset) {
        DataSet toTrainOn = env.getVariables()[dataset]
        //TODO: better way of loading algs
        Clusterer c;
        if (type == "knn") {
            c = new KNN();
        }
        c.train(toTrainOn);
    }
    
    private String predict(String trained, List<String> args) {
        Classifier c = new KNN();
        c.predict(x); // how to recover DataElement? Instead maybe do k fold validation.
        return ""
    }

    private String load(String name, String fp, String label) {
        DataHeader dh = null
        FSLoader fs = new FSLoader();
        if (fs.headerExists(fp)) {
            dh = fs.getHeader(fp)
        }
        DataSet ds = fs.load(fp, dh, label)
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
