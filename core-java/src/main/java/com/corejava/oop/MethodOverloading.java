package com.corejava.oop;

/**
 * Overloading: several methods share a name but differ in their parameters, so the compiler
 * picks one based on the arguments. Overriding, the other route to polymorphism, is in
 * MethodOverriding.
 */
public class MethodOverloading {

	public int sum(int a, int b) {
		return a + b;
	}

	public int sum(int a, int b, int c) {
		return a + b + c;
	}

	public double sum(double a, double b) {
		return a + b;
	}

	public static void main(String[] args) {
		MethodOverloading obj = new MethodOverloading();

		System.out.println("Sum is = " + obj.sum(4, 6));
		System.out.println("Sum is = " + obj.sum(4, 6, 10));
		System.out.println("Sum is = " + obj.sum(32.3, 5.4));
	}
}