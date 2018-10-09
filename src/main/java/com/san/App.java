package com.san;

public class App {

	public static void main(String args[]) throws InterruptedException {
		DemoTarget l = new DemoTarget();
		l.test();
	}
}

/*
 * 
 * Steps to load Java Agent statically :- 
 * 	1- Validate maven project using command : mvn validate
 * 	2- Build maven project using command : mvn package
 * 	3- Execute jar from project home using command : java -javaagent:target/instrumentation-demo-0.0.1-SNAPSHOT-jar-with-dependencies.jar -jar target/instrumentation-demo-0.0.1-SNAPSHOT-jar-with-dependencies.jar
 * 	4- Observe the output console of application 
 * 
 * 
 * Steps to load Java Agent Dynamically :- 
 * 	1- Validate maven project using command : mvn validate
 * 	2- Build maven project using command : mvn package
 * 	3- Execute jar from project home using command : java -jar target/instrumentation-demo-0.0.1-SNAPSHOT-jar-with-dependencies.jar 
 * 	4- Launch JAVA Agent (DemoAgentLoader) dynamically using command : java -cp target/instrumentation-demo-0.0.1-SNAPSHOT-jar-with-dependencies.jar com.san.agent.DemoAgentLoader
 * 	5- Observe the output console of previous application
 * 
 */