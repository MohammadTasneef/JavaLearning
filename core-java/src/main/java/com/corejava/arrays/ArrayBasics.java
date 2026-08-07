package com.corejava.arrays;

public class ArrayBasics {

	public static void main(String[] args) {
		int[] a = { 1, 2, 3, 4, 5 };
		int[] b = new int[4]; // every index defaults to 0

		System.out.println(b.length);
		System.out.println(a.length);
		System.out.println(a[0]);

		for (int i = 0; i < a.length; i++) {
			System.out.println("Value of each element is = " + a[i]);
		}

		// for each is shorter but can only move forward
		for (int value : a) {
			System.out.println(value);
		}
	}
}
