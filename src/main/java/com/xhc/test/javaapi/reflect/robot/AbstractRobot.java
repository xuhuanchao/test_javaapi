package com.xhc.test.javaapi.reflect.robot;

import java.util.Map;

public abstract class AbstractRobot implements Action {


	protected String baseCode = "abstractRobot";
	
	public String typeName = "baseRobot";
	
	public String name = "robot";
	
	public String level = "Z1";
	
	public void introduceSelf(){
		System.out.println("Robot name is "+ name + ", type is " + typeName );
	}
	
	public void getCfgInfo(){
		System.out.println("baseCode is " + baseCode + " , typeName is " + typeName + " , name is " + name + " , level is " + level);
	}


	
	
//	@Override
//	public String toString() {
//		return "baseCode is " + baseCode + " , typeName is " + typeName + " , name is " + name + " , level is " + level;
//	}

	/**
	 *  Action implement 
	 */
	@Override
	public void say() {
		System.out.println("Hello my name is "+name);
	}

	@Override
	public void dump() {
		System.out.println(name+" is dumping.");
	}

	@Override
	public void eat() {
		System.out.println(name +" is eating something.");
	}

	@Override
	public void sleep() {
		System.out.println(name + " is sleeping.");
	}

	
	
}
