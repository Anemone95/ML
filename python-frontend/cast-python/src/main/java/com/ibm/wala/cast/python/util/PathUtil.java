package com.ibm.wala.cast.python.util;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class PathUtil {
    public static String relPath(String fullPath, String[] rootPath) {
        String[] path = fullPath.split("/");
        String[] relPath = Arrays.copyOfRange(path, rootPath.length, path.length);
        return String.join("/", relPath);
    }

    public static String relPath(String fullPath, String rootPath) {
        return relPath(fullPath, rootPath.split("/"));
    }

    public static Path getPath(String base, String... more){
        if (System.getProperty("os.name").toLowerCase().contains("windows")){
            return Paths.get(base.substring(1), more);
        } else {
            return Paths.get(base, more);
        }
    }
}
