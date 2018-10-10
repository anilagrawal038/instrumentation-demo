package com.san;

import java.io.IOException;

import com.san.hotswapper.DemoHotSwapper;
import com.sun.jdi.connect.IllegalConnectorArgumentsException;

import javassist.CannotCompileException;
import javassist.NotFoundException;

//Note : To use below functionality need to run(Not debug) the application using below command.
//java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=8000 <class-name>

public class TestHotSwapper {

	public static void main(String[] args) throws InterruptedException, NotFoundException, CannotCompileException, IllegalConnectorArgumentsException, IOException {
		DemoTarget obj = new DemoTarget();
		obj.testDemo();
		DemoHotSwapper swapper = new DemoHotSwapper();
		swapper.testInstrumentation();
		obj.testDemo();
	}

}
