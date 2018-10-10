package com.san.console;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import com.san.console.javaassist.JavaAssistConsoleService;
import com.san.console.tools.ToolsConsoleService;

//Note : There are two ways to execute java code at runtime
//---- 1 : JAVA ASSIST API (Limitation : Only Java 1.5 syntax supported)
//---- 2 : JAVA TOOLS API (Limitation : I/O operation needed)

public class ConsoleService {

	private static final ToolsConsoleService toolsConsoleService = new ToolsConsoleService();
	private static final JavaAssistConsoleService javaAssistConsoleService = new JavaAssistConsoleService();

	public String execute(String body, boolean java5Compatible, boolean isEnableStackTrace) {
		ConsoleOutputCapturer outputCapturer = new ConsoleOutputCapturer();
		String output = "";
		try {
			outputCapturer.start();
			if (java5Compatible) {
				javaAssistConsoleService.process(body);
			} else {
				toolsConsoleService.process(body);
			}
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

}
