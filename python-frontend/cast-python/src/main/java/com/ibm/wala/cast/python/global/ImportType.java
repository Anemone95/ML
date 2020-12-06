package com.ibm.wala.cast.python.global;

public enum ImportType {
    IMPORT("import"), IMPORT_INIT("importInit"), IMPORT_BUILTIN("importBuiltIn");
    private final String type;
    private ImportType(String type){
        this.type=type;
    }
}
