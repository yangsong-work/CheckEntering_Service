package com.fri.checkenteringservice;

public class A {
    String aa = "ee";
    public void happy(){
        System.out.println("Ahappy");
    }

    public String getAa() {
        return aa;
    }

    public void setAa(String aa) {
        this.aa = aa;
    }

    @Override
    public String toString() {
        return "A{" +
                "aa='" + aa + '\'' +
                '}';
    }

    public static void main(String[] args) {
        B b = new B();
        System.out.println(b.toString());
    }

}
class B extends A{
    public String getAaa() {
        return bbb;
    }

    public void setAaa(String aaa) {
        this.bbb = bbb;
    }

    @Override
    public String toString() {
        return "B{" +
                "bbb='" + bbb + '\'' +
                '}';
    }

    String bbb = "we";
}