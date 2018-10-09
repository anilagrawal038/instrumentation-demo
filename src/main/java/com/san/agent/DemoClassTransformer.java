package com.san.agent;

import java.io.ByteArrayInputStream;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

// Note : Do not import or use any transformation target class in ClassFileTransformer
// Otherwise transform() function will not be invoked for that class

public class DemoClassTransformer implements ClassFileTransformer {

	public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
		byte[] byteCode = classfileBuffer;

		// since this transformer will be called when all the classes are
		// loaded by the classloader, we are restricting the instrumentation
		// using if block only for the DemoTarget class

		String clazzName = "com/san/DemoTarget"; // DemoTarget.class.getName().replace('.', '/');
		// System.out.println("Inside DemoClassTransformer (" + clazzName + ")");
		// System.out.println("Current Class : " + className);

		if (className.equals(clazzName)) {
			System.out.println("Instrumenting " + clazzName + " ......");
			try {
				ClassPool classPool = ClassPool.getDefault();
				CtClass ctClass = classPool.makeClass(new ByteArrayInputStream(classfileBuffer));
				CtMethod[] methods = ctClass.getDeclaredMethods();
				// For demo purpose we will transform only "testDemo()" function of DemoTarget class
				for (CtMethod method : methods) {
					if (method.getName().indexOf("testDemo") <= -1) {
						continue;
					}
					method.addLocalVariable("startTime", CtClass.longType);
					method.insertBefore("startTime = System.nanoTime();");
					method.insertAfter("System.out.println(\"Execution Duration " + "(nano sec): \"+ (System.nanoTime() - startTime) );");
				}
				byteCode = ctClass.toBytecode();
				ctClass.detach();
				System.out.println("Instrumentation complete.");
			} catch (Throwable ex) {
				System.out.println("Exception: " + ex);
				ex.printStackTrace();
			}
		}
		return byteCode;
	}

}
