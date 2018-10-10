package com.san.console;

public class ConsoleExecuter {

	public void print(Object obj) {
		System.out.print(obj);
	}

	public void println(Object obj) {
		System.out.println(obj);
	}

	public void run() {
		println("Test code executed");
	}

	public void sum(Integer x, Integer y) {
		System.out.println("x : " + x + ", y : " + y + ", (x+y) : " + (x + y));
	}

}
