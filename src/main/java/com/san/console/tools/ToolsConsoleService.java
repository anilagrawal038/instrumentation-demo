package com.san.console.tools;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import com.san.console.ConsoleOutputCapturer;

public class ToolsConsoleService {

	private final String CONSOLE_EXECUTER_CLASS_METHOD = "run";

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

	public void process(String body) throws Exception {
		ClassGenerator generator = new ClassGenerator();
		Class<?> clazz = generator.generateClass(body);
		Object obj = clazz.newInstance();
		clazz.getMethod(CONSOLE_EXECUTER_CLASS_METHOD).invoke(obj);
	}
}
