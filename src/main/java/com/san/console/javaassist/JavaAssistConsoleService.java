package com.san.console.javaassist;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.net.URL;
import java.net.URLClassLoader;
import com.san.console.ConsoleOutputCapturer;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

public class JavaAssistConsoleService {

	private final String CONSOLE_EXECUTER_CLASS = "com.san.console.ConsoleExecuter";
	private final String CONSOLE_EXECUTER_CLASS_METHOD = "run";
	private boolean isConsoleExecuterInitialized = false;
	private CtClass ctClass;
	private CtMethod ctMethod;
	ClassPool classPool;
	boolean isClassInitialized = false;
	DemoClassLoader classLoader;

	public String execute(String body, boolean isEnableStackTrace) {
		ConsoleOutputCapturer outputCapturer = new ConsoleOutputCapturer();
		String output = "";
		try {
			outputCapturer.start();
			process(body);
			output = outputCapturer.stop();
		} catch (Throwable e) {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			PrintStream ps = new PrintStream(baos);
			try {
				e.printStackTrace(ps);
				ps.flush();
				output = baos.toString();
				ps.close();
				baos.close();
			} catch (Exception ignore) {
			} finally {
				baos = null;
				ps = null;
			}
		}
		return output;
	}

	// Use new instance of classLoader for each method call
	public void process(String body) throws Exception {
		initializeConsoleExecuter();
		ctMethod.setBody(body);
		Class<?> clazz = ctClass.toClass(classLoader.fetchNewClassLoader(), null);
		if (ctClass.isFrozen()) {
			ctClass.defrost();
		}
		Object obj = clazz.newInstance();
		clazz.getMethod(CONSOLE_EXECUTER_CLASS_METHOD).invoke(obj);
	}

	private void initializeConsoleExecuter() throws Exception {
		if (!isConsoleExecuterInitialized) {
			initializeClassPool();
			ctClass = classPool.get(CONSOLE_EXECUTER_CLASS);
			ctMethod = ctClass.getDeclaredMethod(CONSOLE_EXECUTER_CLASS_METHOD);
			ctClass.stopPruning(true);
			isConsoleExecuterInitialized = true;
		}
	}

	private void initializeClassPool() throws Exception {
		if (!isConsoleExecuterInitialized) {
			ClassLoader cl = JavaAssistConsoleService.class.getClassLoader();
			URL[] urls = ((URLClassLoader) cl).getURLs();
			classLoader = new DemoClassLoader(new URL[] {}, cl);
			classLoader.setClazzName(CONSOLE_EXECUTER_CLASS);
			classPool = ClassPool.getDefault();
			for (URL url : urls) {
				classPool.appendClassPath(url.getPath());
			}
			classPool.importPackage("java.util");
			classPool.importPackage("java.io");
			isConsoleExecuterInitialized = true;
		}
	}

}
