package com.ibm.wala.cast.python.util;

import java.net.MalformedURLException;
import java.net.URL;
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


    public static Path getPath(URL url){
        if (System.getProperty("os.name").toLowerCase().contains("windows")){
            return Paths.get(url.getPath().substring(1));
        } else {
            return Paths.get(url.getPath());
        }
    }
    public static Path getPath(String base, String... more){
        if (System.getProperty("os.name").toLowerCase().contains("windows")){
            try {
                return Paths.get(new URL(base).getPath().substring(1), more);
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return null;
            }
        } else {
            return Paths.get(base, more);
        }
    }
}
