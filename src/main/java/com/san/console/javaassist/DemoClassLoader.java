package com.san.console.javaassist;

import java.net.URL;
import java.net.URLClassLoader;

public class DemoClassLoader extends URLClassLoader {
	private String clazzName;
	private byte[] byteCode;
	private static URL[] emptyUrls = new URL[] {};

	public DemoClassLoader(URL[] urls, ClassLoader parent) {
		super(urls, parent);
	}

	public DemoClassLoader(URL[] urls) {
		super(urls);
	}

	/*
	 * @Override public Class<?> findClass(final String name) throws ClassNotFoundException { return super.findClass(name); }
	 */

	@SuppressWarnings("resource")
	public Class<?> findConsoleExecutorClass() {
		return new DemoClassLoader(emptyUrls, this).defineClass(clazzName, byteCode, 0, byteCode.length);
	}

	public String getClazzName() {
		return clazzName;
	}

	public void setClazzName(String clazzName) {
		this.clazzName = clazzName;
	}

	public byte[] getByteCode() {
		return byteCode;
	}

	public void setByteCode(byte[] byteCode) {
		this.byteCode = byteCode;
	}

	public DemoClassLoader fetchNewClassLoader() {
		return new DemoClassLoader(emptyUrls, this);
	}
}