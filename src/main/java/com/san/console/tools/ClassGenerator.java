package com.san.console.tools;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

import com.san.console.ConsoleExecuter;

public class ClassGenerator {

	final String tmpDir = System.getProperty("java.io.tmpdir");
	final String classPath = tmpDir + "/java/DummyClassGenerator";
	final Class<?> clazz = ConsoleExecuter.class;
	final File root = new File(classPath); // On Windows running on C:\, this is C:\java.
	final File sourceFile = new File(root, clazz.getName().replace('.', '/') + ".java");

	public Class<?> generateClass(String methodBody) throws IOException, ClassNotFoundException {
		String sourceString = fileData(methodBody);
		byte[] sourceBytes = sourceString.getBytes(StandardCharsets.UTF_8);

		// Save source in .java file.
		sourceFile.getParentFile().mkdirs();
		Files.write(sourceFile.toPath(), sourceBytes);

		// Compile source file.
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		compiler.run(null, null, null, sourceFile.getPath());

		// Load and instantiate compiled class.
		// We need to create new instance of class loader each time when we want to load the modified class
		DemoClassLoader demoClassLoader = new DemoClassLoader(ClassGenerator.class.getClassLoader(), classPath);
		Class<?> cls = Class.forName(clazz.getName(), true, demoClassLoader);
		return cls;
	}

	private String fileData(String methodBody) {
		StringBuilder sb = new StringBuilder();

		Package pkg = clazz.getPackage();
		if (pkg != null) {
			sb.append("package " + pkg.getName() + ";");
		}

		sb.append("import java.util.*;");
		sb.append("import java.io.*;");

		// Start Class Definition
		sb.append("public class " + clazz.getSimpleName() + " {");
		// Start Class Body

		// print(Object)
		sb.append("public void print(Object obj) {System.out.print(obj);}");

		// println(Object)
		sb.append("public void println(Object obj) {System.out.print(obj);}");

		// sum(int, int)
		sb.append("public void sum(Integer x, Integer y) {System.out.println(\"x : \" + x + \", y : \" + y + \", (x+y) : \" + (x + y));}");

		// Start run method Definition
		sb.append("public void run() {");
		sb.append(methodBody);
		sb.append("}");
		// End run method Definition

		// End Class Body
		sb.append("}");
		// Start Class Definition
		return sb.toString();
	}
}
