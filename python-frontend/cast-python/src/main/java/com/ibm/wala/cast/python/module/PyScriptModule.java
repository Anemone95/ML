package com.ibm.wala.cast.python.module;

import com.ibm.wala.classLoader.SourceURLModule;

import java.net.URL;

public class PyScriptModule extends SourceURLModule {
    public PyScriptModule(URL url) {
        super(url);
    }
    public String getName() {
        return getURL().toString();
    }
}
