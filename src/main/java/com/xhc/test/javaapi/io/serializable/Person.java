package com.xhc.test.javaapi.io.serializable;

import java.io.Serializable;

public class Person implements Serializable{

    private String name;
    
    private int age;
    
    private float heigh;

    
    public Person() {
        super();
        // TODO Auto-generated constructor stub
    }

    public Person(String name, int age, float heigh) {
        super();
        this.name = name;
        this.age = age;
        this.heigh = heigh;
    }


    @Override
    public String toString() {
        return "name:" + name + ", age:"+age + ", heigh:"+heigh;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public float getHeigh() {
        return heigh;
    }

    public void setHeigh(float heigh) {
        this.heigh = heigh;
    }
    
    
    
}
