package com.ibm.wala.cast.python.global;

import java.nio.file.Path;
import java.util.Set;

public class SystemPath {
    private Set<Path> libPath;
    private Path appPath;
    private boolean appPathLocked;

    public Set<Path> getLibPath() {
        return libPath;
    }

    public void setLibPath(Set<Path> libPath) {
        this.libPath = libPath;
    }

    public Path getAppPath() {
        return appPath;
    }

    public void setAppPath(Path appPath) {
        if (!appPathLocked){
            this.appPath = appPath;
            appPathLocked=true;
        } else {
            System.err.println("app path setted before");
        }
    }

    private static class SingletonHolder {
        private static SystemPath instance = new SystemPath();
    }

    private SystemPath() {
    }

    public static SystemPath getInstance() {
        return SingletonHolder.instance;
    }

}
