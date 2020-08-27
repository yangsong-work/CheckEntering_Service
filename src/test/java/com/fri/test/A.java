package com.fri.test;

public class A {
    private String name ;
    private String age ;
    private int high;
    private Integer higher;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public int getHigh() {
        return high;
    }

    public void setHigh(int high) {
        this.high = high;
    }

    public Integer getHigher() {
        return higher;
    }

    public void setHigher(Integer higher) {
        this.higher = higher;
    }

    @Override
    public String toString() {
        return "A{" +
                "name='" + name + '\'' +
                ", age='" + age + '\'' +
                ", high=" + high +
                ", higher=" + higher +
                '}';
    }
}
