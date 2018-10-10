package com.san;

import com.san.console.ConsoleService;

// To execute some dynamic java code
public class TestConsole {

	static String commands = "sum(new Integer(9),new Integer(13));";
	static String commands1 = "sum(13,15);";

	public static void main(String[] args) {
		ConsoleService service = new ConsoleService();
		String output = service.execute(commands1, true, true);
		System.out.println("Output => " + output);
	}

}
