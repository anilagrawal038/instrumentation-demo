package com.san.agent;

import java.lang.instrument.Instrumentation;

import com.san.DemoTarget;

public class DemoJavaAgent {

	// Ref : https://www.baeldung.com/java-instrumentation
	// Ref : https://blogs.sap.com/2016/03/09/java-bytecode-instrumentation-using-agent-breaking-into-java-application-at-runtime/
	// Ref : https://docs.oracle.com/javase/10/docs/api/java/lang/instrument/package-summary.html

	// to load agent statically
	public static void premain(String agentArgs, Instrumentation inst) {
		System.out.println("[Agent] In premain method");
		inst.addTransformer(new DemoClassTransformer());
	}

	// to load agent dynamically
	public static void agentmain(String agentArgs, Instrumentation inst) throws ClassNotFoundException {
		System.out.println("[Agent] In agentmain method");
		inst.addTransformer(new DemoClassTransformer(), true);
		// Due to above line, new changes will be effective for future objects

		// To transform already loaded Class<DemoTarget> and DemoTarget objects
		Class<?> clazz = DemoTarget.class; // Class.forName("com.san.agent.DemoTarget");
		try {
			inst.retransformClasses(clazz);
		} catch (Exception ex) {
			throw new RuntimeException("Transform failed for class: [" + clazz.getName() + "]", ex);
		}
	}
}
