package com.corejava.oop;

/**
 * A constructor has the same name as the class and runs when the object is created, before
 * any of its methods can be called.
 */
public class ConstructorDemo {

	int num = 49;

	public ConstructorDemo() {
		System.out.println("Constructor is executed");
	}

	public static void main(String[] args) {
		ConstructorDemo obj = new ConstructorDemo();
		System.out.println(obj.num);
	}
}