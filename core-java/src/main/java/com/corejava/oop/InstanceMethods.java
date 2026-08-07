package com.corejava.oop;

/**
 * Base of the inheritance examples in this package. Holds one instance method and one
 * static method so the difference between the two is visible in the subclasses.
 */
public class InstanceMethods {

	int x = 5;
	static int y = 4;

	// instance method with a return type
	public int add(int a, int b) {
		return a + b;
	}

	public int multiply(int a, int b) {
		return a * b;
	}

	// instance method without a return type
	public void show(int value) {
		System.out.println("The value is = " + value);
	}

	// static method, callable without an object
	public static int addStatically(int a, int b) {
		System.out.println(y);
		return a + b;
	}

	public static void main(String[] args) {
		InstanceMethods obj = new InstanceMethods();

		int sum = obj.add(10, 20);
		System.out.println(sum);

		int product = obj.multiply(30, 40);
		obj.show(sum);
		obj.show(product);

		System.out.println(addStatically(2, 3));
	}
}