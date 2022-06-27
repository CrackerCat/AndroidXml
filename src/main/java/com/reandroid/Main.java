package com.reandroid;

import com.reandroid.xml.XMLException;

import java.io.IOException;

class Main {
    public static void main(String[] args){
        try {
            MainHelper.init(args);
            MainHelper.run();
        } catch (IOException | XMLException e) {
            MainHelper.logError("");
            MainHelper.logError("ERROR: "+e.getMessage());
        }
    }
}
