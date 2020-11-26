package com.ibm.wala.cast.python.loader;

import com.ibm.wala.cast.loader.AstDynamicField;
import com.ibm.wala.cast.python.types.PythonTypes;
import com.ibm.wala.classLoader.*;
import com.ibm.wala.ipa.cha.IClassHierarchy;
import com.ibm.wala.shrikeBT.Constants;
import com.ibm.wala.types.Selector;
import com.ibm.wala.types.TypeReference;
import com.ibm.wala.util.collections.HashMapFactory;
import com.ibm.wala.util.strings.Atom;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

public class PythonSyntheticClass extends SyntheticClass {
    private final Map<Atom, IField> fields;
    private final IClassHierarchy cha;

    public PythonSyntheticClass(TypeReference t, IClassHierarchy cha) {
        super(t, cha);
        this.cha = cha;
        fields = HashMapFactory.make();
    }

    @Override
    public IClassLoader getClassLoader() {
        return cha.getLoader(cha.getScope().getSyntheticLoader());
    }

    @Override
    public boolean isPublic() {
        return true;
    }

    @Override
    public boolean isPrivate() {
        return false;
    }

    @Override
    public int getModifiers() throws UnsupportedOperationException {
        return Constants.ACC_PUBLIC;
    }

    @Override
    public IClass getSuperclass() {
        return cha.lookupClass(PythonTypes.CodeBody);
    }

    @Override
    public Collection<? extends IClass> getDirectInterfaces() {
        return Collections.emptySet();
    }

    @Override
    public Collection<IClass> getAllImplementedInterfaces() {
        return Collections.emptySet();
    }

    @Override
    public IMethod getMethod(Selector selector) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IField getField(Atom name) {
        if (!fields.containsKey(name)) {
            fields.put(name, new AstDynamicField(false, cha.lookupClass(PythonTypes.Root), name, PythonTypes.Root));
        }
        return fields.get(name);
    }

    @Override
    public IMethod getClassInitializer() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<? extends IMethod> getDeclaredMethods() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<IField> getAllInstanceFields() {
        return fields.values();
    }

    @Override
    public Collection<IField> getAllStaticFields() {
        return Collections.emptySet();
    }

    @Override
    public Collection<IField> getAllFields() {
        return fields.values();
    }

    @Override
    public Collection<? extends IMethod> getAllMethods() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<IField> getDeclaredInstanceFields() {
        return fields.values();
    }

    @Override
    public Collection<IField> getDeclaredStaticFields() {
        return Collections.emptySet();
    }

    @Override
    public boolean isReferenceType() {
        return true;
    }
}
