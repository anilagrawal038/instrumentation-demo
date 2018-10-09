package com.san.agent;

import java.io.File;
import java.util.Optional;

import com.sun.tools.attach.VirtualMachine;

public class DemoAgentLoader {

	// Note : attach.dll/libattach.so must be present on java path to load JAVA Agent dynamically

	public static void main(String[] args) {
		String applicationName = "instrumentation-demo";
		String agentFilePath = "target/" + applicationName + "-0.0.1-SNAPSHOT-jar-with-dependencies.jar";
		String jvmPid = null;
		Optional<String> jvmProcessOpt = null;
		if (args.length <= 0) {
			// iterate all jvms and get the first one that matches our application name
			jvmProcessOpt = Optional.ofNullable(VirtualMachine.list().stream().filter(jvm -> {
				System.out.println("jvm:{}" + jvm.displayName());
				return jvm.displayName().contains(applicationName);
			}).findFirst().get().id());

			if (!jvmProcessOpt.isPresent()) {
				System.out.println("Target Application not found");
				return;
			}
		}

		File agentFile = new File(agentFilePath);
		try {
			if (args.length > 0) {
				jvmPid = args[0];
			} else {
				jvmPid = jvmProcessOpt.get();
			}
			System.out.println("Attaching to target JVM with PID: " + jvmPid);
			VirtualMachine jvm = VirtualMachine.attach(jvmPid);
			jvm.loadAgent(agentFile.getAbsolutePath());
			jvm.detach();
			System.out.println("Attached to target JVM and loaded Java agent successfully");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
