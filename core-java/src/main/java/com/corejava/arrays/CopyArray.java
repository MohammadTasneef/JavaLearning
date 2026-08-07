package com.corejava.arrays;

public class CopyArray {

	public static void main(String[] args) {
		int[] source = { 1, 2, 3, 4, 5 };
		int[] copy = new int[source.length];

		for (int i = 0; i < source.length; i++) {
			copy[i] = source[i];
		}

		System.out.println("Copied array:");
		for (int i = 0; i < copy.length; i++) {
			System.out.print(copy[i] + " ");
		}
		System.out.println();
	}
}