package com.corejava.oop;

public class MethodOverriding extends InstanceMethods {

	int a = 25;

	public int display(int a) {
		System.out.println("Parent class is displayed " + a);
		return a;
	}

	public static void main(String[] args) {
		MethodOverriding obj = new MethodOverriding();
		obj.display(obj.a);
	}
}