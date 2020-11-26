package com.ibm.wala.cast.python3.loader;

import com.ibm.wala.classLoader.IClassLoader;
import com.ibm.wala.ipa.cha.IClassHierarchy;
import com.ibm.wala.cast.python.loader.PythonLoaderFactory;

public class Python3LoaderFactory extends PythonLoaderFactory {

	@Override
	protected IClassLoader makeTheLoader(IClassHierarchy cha) {
		return new Python3Loader(cha);
	}

}
