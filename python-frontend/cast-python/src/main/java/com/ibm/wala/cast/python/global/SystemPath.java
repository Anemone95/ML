package com.ibm.wala.cast.python.global;

import com.ibm.wala.cast.python.util.PathUtil;
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

            Path importScript = PathUtil.getPath(scriptName);

            int i = 0;
            while (module.charAt(i) == '.') {
                importScript = importScript.getParent();
                i++;
            }
            if (i+1<module.length()){
                // 防止 `from . import yyy`
                importScript = importScript.resolve(module.substring(i));
            }
            Path importedPath;
            if (i > 0) {
                // from .xxx import yyy
                importedPath = importScript;
            } else {
                // import xxx
                importedPath = appPath.resolve(module);
            }
            if (importedPath.toFile().isDirectory()) {
                // `import lib`
                importedPath = importedPath.resolve("__init__");
            }
            return importedPath;
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
