package com.ibm.wala.cast.python2.loader;

import com.ibm.wala.cast.python.loader.PythonLoaderFactory;
import com.ibm.wala.classLoader.IClassLoader;
import com.ibm.wala.ipa.cha.IClassHierarchy;

public class Python2LoaderFactory extends PythonLoaderFactory {

	@Override
	protected IClassLoader makeTheLoader(IClassHierarchy cha) {
		return new Python2Loader(cha);
	}

}
