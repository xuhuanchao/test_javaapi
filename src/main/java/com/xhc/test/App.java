package com.xhc.test;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

import com.xhc.test.javaapi.reflect.robot.AbstractRobot;
import com.xhc.test.javaapi.reflect.robot.specific.IWatier;
import com.xhc.test.javaapi.reflect.robot.specific.WaiterRobot;

/**
 * Hello world!
 *
 */
public class App {
	
	private String robotPath = "com/xhc/test/javaapi/reflect/robot/specific";
	
	private String robotPackage = "com.xhc.test.javaapi.reflect.robot.specific";
	
	private Map<Integer, String> robotIndexs = new HashMap<Integer, String>();
	
	private Map<Integer, Field> robotFields = new HashMap<Integer, Field>();
	
	private Map<Integer, Method> robotMethods = new HashMap<Integer, Method>();
	
	private Integer choseIndex ;
	
	private AbstractRobot choseRobot;
	
	/**
	 * 列出所有机器人名字
	 */
	private void showAllRobot() {
		String basePath = Class.class.getClass().getResource("/").getPath();
		File robotDir = new File(basePath + robotPath);
		if(robotDir.isDirectory()){
			String[] files = robotDir.list();
			for(String filename : files){
				int number = 1;
				try {
					if(filename.endsWith(".class")){
						Class fclass = Class.forName(robotPackage + "." + filename.substring(0, filename.lastIndexOf(".class")));
						if(!fclass.isInterface()){
							String robotName = fclass.getSimpleName();
							System.out.println(number + ". " + robotName);
							robotIndexs.put(number, robotName);
							number++;
						}
					}
					
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} 
			}
			
		}
	}
	
	public void startShow() {
		Scanner scanner = new Scanner(System.in);
		String inputStr = "";
		System.out.println("欢迎来到机器人测试世界！");
		
		System.out.println("请输入需要进行操作的选项：");
		System.out.println("1. 进行反射测试");
		System.out.println("2. others");
		
		
		inputStr = scanner.nextLine();
		if(inputStr.equals("1")){
			System.out.println("请选择要是用的机器人进行测试：");
			showAllRobot();
			inputStr = scanner.nextLine();
			
			String robotName = robotIndexs.get(new Integer(inputStr));
			System.out.println("您选择了机器人：" + robotName);
			choseIndex = new Integer(inputStr);
			Class clazz = AbstractRobot.class;  //默认抽象机器人
			try {
				clazz = Class.forName(robotPackage + "." + robotName);
				
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
				return;
			}
			inputStr = choiseOption(clazz);
			while(true){
				if(inputStr.equals("1")){
					getFields(clazz);
					System.out.println("获得属性列表：");
					showFields();
				} else if (inputStr.equals("2")){
					getMethods(clazz);
					System.out.println("获得方法列表：");
					showMethods();
				} else if (inputStr.equals("3")){
					try {
						choseRobot = (AbstractRobot)clazz.newInstance();
						
						
					} catch (InstantiationException e) {
						e.printStackTrace();
						return;
					} catch (IllegalAccessException e) {
						e.printStackTrace();
						return;
					}
				} else if (inputStr.equals("4")){
					System.out.println("请输入要执行动作的编号：");
					inputStr = scanner.nextLine();
					Method method = robotMethods.get(new Integer(inputStr));
					try {
						method.invoke(choseRobot);
					} catch (IllegalAccessException e) {
						e.printStackTrace();
						return;
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
						return;
					} catch (InvocationTargetException e) {
						e.printStackTrace();
						return;
					}
				
				} else if(inputStr.equals("5")){
					break;
				}
				inputStr = choiseOption(clazz);
			}
			System.out.println("再见！");
		}

	}
	
	
	/**
	 * 让用户做出 操作的选择
	 * @return
	 */
	private String choiseOption (Class clazz){
		Scanner scanner = new Scanner(System.in);
		System.out.println("\n#####请选择要测试的功能：");
		System.out.println("1.获得属性");
		System.out.println("2.获得方法");
		System.out.println("3.创建一个机器人");
		System.out.println("4.调用机器人的一个动作");
		System.out.println("5.退出");
		
		String num = scanner.nextLine();
		int inputNum = Integer.parseInt(num);
		if(inputNum != 1 && inputNum != 2 && inputNum !=3 && inputNum !=4 && inputNum !=5 ){
			System.out.println("输入不合法请重新输入");
			num = choiseOption(clazz);
		}
		return num;	
	} 
	
	
	/**
	 * 获得某个Class 的字段
	 * @param clazz
	 * @param t
	 */
	public void getFields (Class clazz) {
		Field[] fields = clazz.getFields();
		int lastNum =fields.length;
		for(int i=0, j=fields.length ; i<j; i++){
			robotFields.put(i+1 , fields[i]);
		}
		
		fields = clazz.getDeclaredFields();
		for(int i=0, j=fields.length ; i<j; i++){
			if(!robotFields.containsValue(fields[i])){
				lastNum++;
				robotFields.put(lastNum , fields[i]);	
			}
		}
	}
	
	/**
	 * 展示Field 集合
	 */
	public void showFields() {
		Iterator<Entry<Integer, Field>> iterator = robotFields.entrySet().iterator();
		while(iterator.hasNext()){
			Entry<Integer, Field> next = iterator.next();
			Field value = next.getValue();
			System.out.println(next.getKey()+". Modifier:" + Modifier.toString(value.getModifiers()) + 
					" , Return type:" + value.getType() + " , DeclaringClass:" + value.getDeclaringClass().getTypeName() + 
					" , FieldName:" + value.getName());
		}
	}
	
	/**
	 * 获得 Class 基类AbstractRobo和其实现类的的方法
	 * @param clazz
	 */
	public void getMethods (Class clazz) {
		Method[] methods = clazz.getMethods();
		int lastNum = 0;
		for(int i=0, j=methods.length ; i<j; i++){
			String superClassName = methods[i].getDeclaringClass().getSimpleName();
			if(superClassName.equals(clazz.getSimpleName()) || superClassName.equals("AbstractRobot")){
				robotMethods.put(++lastNum , methods[i]);
			}
		}
		
		methods = clazz.getDeclaredMethods();
		for(int i=0, j=methods.length ; i<j; i++){
			if(!robotMethods.containsValue(methods[i])){
				lastNum++;
				robotMethods.put(lastNum , methods[i]);	
			}
		}
	}
	
	public void showMethods() {
		 Iterator<Entry<Integer, Method>> iterator = robotMethods.entrySet().iterator();
		while(iterator.hasNext()){
			Entry<Integer, Method> next = iterator.next();
			Method value = next.getValue();
			System.out.println(next.getKey()+". MethodBelong:" + value.getDeclaringClass().getSimpleName() + " , MethodName:" + value.getName());
		}
	}
	
	
	
	public static void main(String[] args) {
		App app = new App();
		app.startShow();
		
//		test1(new String[] {});
	}
	
    public static void test1( String[] args ) {
    	Class<WaiterRobot> waiter = WaiterRobot.class;
    	try {
//			Class waiterClass = Class.forName("com.xhc.test.javaapi.reflect.robot.specific.WaiterRobot");
			
			Class t = Class.forName("java.lang.Thread");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
    	
    	String robotType = "com.xhc.test.javaapi.reflect.robot.specific.WaiterRobot";
    	
    	try {
			Class robotClass = Class.forName(robotType);
			System.out.println("typeName : " + robotClass.getTypeName() + ", " + robotClass.getName() + " , " + robotClass.getSimpleName() );
			
			String type = "com.xhc.test.javaapi.reflect.robot.Action";
			Class int1 = Class.forName(type);
			System.out.println("int1 : "+ int1.getName() +  ", " +int1.getSuperclass());
			
			AbstractRobot robot = (AbstractRobot)robotClass.newInstance();
			robot.say();
			robot.dump();
			robot.eat();
			robot.introduceSelf();
			robot.getCfgInfo();
			
			System.out.println("===== getFields");
			Field[] filds = robotClass.getFields();
			for(int i=0 ; i<filds.length ; i++){
				System.out.println(filds[i] + " , type is " + filds[i].getType());
			}
			
			System.out.println("===== getDeclaredFields");
			filds = robotClass.getDeclaredFields();
			for(int i=0 ; i<filds.length ; i++){
				System.out.println(filds[i]);
			}
			
			
			System.out.println("======= getMethods");
			Method[] methods = robotClass.getMethods();
			for(int i=0; i<methods.length; i++){
				System.out.println(methods[i]);
			}
			
			System.out.println("======= getDeclaredMethods");
			methods = robotClass.getDeclaredMethods();
			for(int i=0; i<methods.length; i++){
				System.out.println(methods[i]);
			}
			
			
			
			
			Class[] classes = robotClass.getDeclaredClasses();
			
			
			Constructor[] constructors = robotClass.getConstructors();
			
			Field baseNumberField = robotClass.getField("name");
			baseNumberField.setAccessible(true);
			System.out.println(baseNumberField.get(robot));
			
			Field backnameField = robotClass.getDeclaredField("backname");
			backnameField.setAccessible(true);
			System.out.println(backnameField.get(robot));
			
			
		}catch (ClassNotFoundException e){
			e.printStackTrace();
		}catch (IllegalAccessException e){
			e.printStackTrace();
		}catch (InstantiationException e) {
			e.printStackTrace();
		}catch (NoSuchFieldException e){
			e.printStackTrace();
		}
    }
}

