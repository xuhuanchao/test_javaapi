package com.xhc.test;

import com.xhc.test.javaapi.reflect.robot.AbstractRobot;
import com.xhc.test.javaapi.reflect.robot.specific.WaiterRobot;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase {
	/**
	 * Create the test case
	 *
	 * @param testName
	 *            name of the test case
	 */
	public AppTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(AppTest.class);
	}

	/**
	 * Rigourous Test :-)
	 */
	public void testApp() {
		assertTrue(true);
	}
	
//	@org.junit.Test
	public void test1(){
		/*Class<WaiterRobot> waiter = WaiterRobot.class;
    	try {
			Class waiterClass = Class.forName("com.xhc.test.javaapi.robot.specific.WaiterRobot");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	String robotType = "WaiterRobot";
    	
    	try {
			Class robotClass = Class.forName(robotType);
			AbstractRobot robot = (AbstractRobot)robotClass.newInstance();
			robot.say();
			robot.dump();
			robot.eat();
			robot.introduceSelf();
			robot.getCfgInfo();
			
		}catch (ClassNotFoundException e){
			e.printStackTrace();
		}catch (IllegalAccessException e){
			e.printStackTrace();
		}catch (InstantiationException e) {
			e.printStackTrace();
		}*/
	}
	
	
}
