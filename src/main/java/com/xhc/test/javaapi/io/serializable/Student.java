package com.xhc.test.javaapi.io.serializable;


public class Student extends Person implements IStudy{

    private String school;
    
    private transient String classname;

    public Student() {
        super();
        // TODO Auto-generated constructor stub
    }

    public Student(String name, int age, float heigh) {
        super(name, age, heigh);
    }
    
    public Student(String name, int age, float heigh, String school, String classname) {
        super(name, age, heigh);
        this.school = school;
        this.classname = classname;
    }

    @Override
    public void dowork() {
        System.out.printf("%s begin do home work.", getName());
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    @Override
    public String toString() {
        return super.toString() 
                + " school:" +school + ", classname:"+classname;
    }


}
