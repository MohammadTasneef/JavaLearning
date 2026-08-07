package com.corejava.oop;

/**
 * super reaches the parent's members: super.x reads the inherited field and
 * super.display calls the parent's version of an overridden method.
 */
public class SuperKeyword extends MethodOverriding {

	@Override
	public int display(int a) {
		System.out.println("Child class is displayed " + super.x);
		return a;
	}

	public void show() {
		super.display(10);
	}

	public static void main(String[] args) {
		SuperKeyword obj = new SuperKeyword();

		// 23 is passed but super.x is printed, so the inherited value shows instead
		obj.display(23);
		obj.show();
	}
}