package com.ibm.wala.cast.python.module;

import com.ibm.wala.classLoader.SourceFileModule;
import com.ibm.wala.classLoader.SourceURLModule;

import java.net.URL;

public class PyLibURLModule extends SourceURLModule {
    public PyLibURLModule(URL url) {
        super(url);
    }
}
