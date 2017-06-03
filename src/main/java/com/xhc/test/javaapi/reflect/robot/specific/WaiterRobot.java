package com.xhc.test.javaapi.reflect.robot.specific;

import java.util.HashMap;
import java.util.Map;

import com.xhc.test.javaapi.reflect.robot.AbstractRobot;

public class WaiterRobot extends AbstractRobot implements IWatier{

	public String baseCode = "waiter";
	
	public String typeName = "服务员";
	
	public String name = "张三";
	
	public String level = "A1";
	
	private String language = "chinese";
	
	

	public WaiterRobot() {
		super();
	}

	public WaiterRobot(String name, String level, String language) {
		super();
		this.name = name;
		this.level= level;
		this.language = language;
	}

	
	@Override
	public void service() {
		System.out.println(name +" say with "+language+", May I service you ?");
	}

}
