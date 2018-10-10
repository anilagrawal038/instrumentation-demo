package com.san.console.tools;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.san.console.ConsoleExecuter;

public class DemoClassLoader extends ClassLoader {

	private String classPath;

	public DemoClassLoader(ClassLoader parent, String classPath) {
		super(parent);
		this.classPath = classPath;
	}

	public Class<?> loadClass(String name) throws ClassNotFoundException {
		if (name.equals(ConsoleExecuter.class.getName())) {
			return getClass(name);
		}
		return super.loadClass(name);
	}

	private Class<?> getClass(String name) throws ClassNotFoundException {
		String file = name.replace('.', File.separatorChar) + ".class";
		byte[] b = null;
		try {
			b = loadClassFileData(file);
			Class<?> c = defineClass(name, b, 0, b.length);
			resolveClass(c);
			return c;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	private byte[] loadClassFileData(String name) throws IOException {
		// InputStream stream = getClass().getClassLoader().getResourceAsStream(name);
		String path = classPath + File.separatorChar + name;
		InputStream stream = new FileInputStream(path);
		int size = stream.available();
		byte buff[] = new byte[size];
		DataInputStream in = new DataInputStream(stream);
		in.readFully(buff);
		in.close();
		return buff;
	}
}
