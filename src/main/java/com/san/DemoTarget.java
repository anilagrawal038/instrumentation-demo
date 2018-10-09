package com.san;

//to be instrumented java class

public class DemoTarget {

	public void testDemo() throws InterruptedException {
		System.out.println("testDemo() being executed ........");
		Thread.sleep(2000L);
	}

	public void test() throws InterruptedException {
		while (true) {
			testDemo();
			Thread.sleep(2000L);
		}
	}

}
