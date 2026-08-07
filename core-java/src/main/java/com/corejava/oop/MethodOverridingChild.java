package com.corejava.oop;

/**
 * A subclass declaring a method with the same signature as its parent overrides it, so the
 * child version is the one that runs.
 */
public class MethodOverridingChild extends MethodOverriding {

	int a = 26;

	@Override
	public int display(int a) {
		System.out.println("Child class is displayed " + a);
		return a;
	}

	public static void main(String[] args) {
		MethodOverridingChild obj = new MethodOverridingChild();
		obj.display(obj.a);
		obj.display(24);
	}
}