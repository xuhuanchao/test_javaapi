package com.xhc.test.javaapi.reflect.robot.specific;


import com.xhc.test.javaapi.reflect.robot.AbstractRobot;

public class WaiterRobot extends AbstractRobot implements IWatier{

	
	private String language = "chinese";
	
	public WaiterRobot() {
		super();
	}
	
	public WaiterRobot(String name, String level, String language) {
		super();
		this.name = name;
		this.level = level;
		this.language = language;
		this.typeName = "服务员";
		this.baseCode = "waiter";
	}




	public WaiterRobot(String baseCode, String typeName, String name, String level, String language) {
		super();
		this.baseCode = baseCode;
		this.typeName = typeName;
		this.name = name;
		this.level = level;
		this.language = language;
	}




	@Override
	public void service() {
		System.out.println(name +" say with "+language+", May I service you ?");
	}

}
