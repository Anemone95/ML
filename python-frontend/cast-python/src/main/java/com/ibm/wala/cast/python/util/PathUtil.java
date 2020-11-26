package com.ibm.wala.cast.python.util;

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
}
