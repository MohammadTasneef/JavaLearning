package com.corejava.oop;

/**
 * Same operations as InstanceMethods but all static, so they are called on the class
 * instead of on an object.
 */
public class StaticMethods {

	static int y = 4;

	public static int add(int a, int b) {
		return a + b;
	}

	public static int multiply(int a, int b) {
		return a * b;
	}

	public static void show(int value) {
		System.out.println("The value is " + value);
	}

	public static void main(String[] args) {
		int sum = add(10, 20);
		System.out.println(sum);

		int product = multiply(20, 10);
		show(sum);
		show(product);
	}
}