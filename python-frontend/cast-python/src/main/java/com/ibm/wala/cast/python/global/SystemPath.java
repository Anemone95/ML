package com.ibm.wala.cast.python.global;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.nio.file.Path;
import java.nio.file.Paths;
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
        if (!appPathLocked) {
            this.appPath = appPath;
            appPathLocked = true;
        } else {
            System.err.println("app path setted before");
        }
    }

    public Path getImportModule(String scriptName, String module) {
        // if app module
        if (scriptName.startsWith(appPath.toUri().toString().replace("file:///", "file:/"))) {
            return appPath.resolve(module);
        }
        System.err.println("Can't get module: " + module + " in " + appPath);
        throw new NotImplementedException();
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
