package com.san;

import com.san.console.ConsoleService;

// To execute some dynamic java code
public class TestConsole {

	static String commands1 = "sum(new Integer(9),new Integer(13));";
	static String commands2 = "sum(13,15);";
	static String commands3 = "println(\"hello friends\\n\");";

	public static void main(String[] args) {
		
		ConsoleService service = new ConsoleService();

		String output = service.execute(commands1, false, true);
		System.out.println("Output1 => " + output);

		output = service.execute(commands2, false, true);
		System.out.println("Output2 => " + output);

		output = service.execute(commands3, false, true);
		System.out.println("Output3 => " + output);
	}

}
