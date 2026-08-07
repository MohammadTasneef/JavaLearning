package com.corejava.arrays;

public class ArrayRotation {

	public static void main(String[] args) {
		int[] arr = { 8, 5, 9, 2, 6, 1, 34, 51 };

		System.out.println("Before rotation:");
		print(arr);

		// left rotate by one, the first element moves to the end
		int first = arr[0];
		for (int i = 1; i < arr.length; i++) {
			arr[i - 1] = arr[i];
		}
		arr[arr.length - 1] = first;

		System.out.println("After rotation:");
		print(arr);
	}

	private static void print(int[] arr) {
		for (int value : arr) {
			System.out.print(value + " ");
		}
		System.out.println();
	}
}