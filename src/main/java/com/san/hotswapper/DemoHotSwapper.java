package com.san.hotswapper;

import java.io.IOException;

import com.san.DemoTarget;
import com.sun.jdi.connect.IllegalConnectorArgumentsException;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;
import javassist.util.HotSwapper;

// Note : To use below functionality need to run(Not debug) the application using below command.
// java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=8000 <class-name>

// Ref : http://www.javassist.org/html/javassist/util/HotSwapper.html

public class DemoHotSwapper {

	public static void main(String[] args) throws InterruptedException, NotFoundException, CannotCompileException, IllegalConnectorArgumentsException, IOException {
		DemoTarget obj = new DemoTarget();
		obj.testDemo();
		DemoHotSwapper swapper = new DemoHotSwapper();
		swapper.testInstrumentation();
		obj.testDemo();
	}

	public void testInstrumentation() throws NotFoundException, CannotCompileException, IllegalConnectorArgumentsException, IOException {
		ClassPool classPool = ClassPool.getDefault();
		CtClass ctClass = classPool.get(DemoTarget.class.getName());
		ctClass.stopPruning(true);
		CtMethod ctMethod = ctClass.getDeclaredMethod("testDemo");
		ctMethod.setBody("{System.out.println(\"Instrumented testDemo() being executed ........\");Thread.sleep(2000L);}");
		if (ctClass.isFrozen()) {
			ctClass.defrost();
		}
		ctClass.rebuildClassFile();
		HotSwapper hs = new HotSwapper(8000); // 8000 is a port number.
		hs.reload(DemoTarget.class.getName(), ctClass.toBytecode());
	}
}
