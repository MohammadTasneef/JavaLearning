package com.corejava.arrays;

public class SecondLargestNumber {

	public static void main(String[] args) {
		int[] arr = { 8, 5, 9, 2, 6, 1, 34, 51 };

		int largest = Integer.MIN_VALUE;
		int secondLargest = Integer.MIN_VALUE;

		// Both used to be seeded with arr[0], which returned the largest value twice
		// whenever the largest happened to sit at index 0.
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] > largest) {
				secondLargest = largest;
				largest = arr[i];
			} else if (arr[i] > secondLargest && arr[i] != largest) {
				secondLargest = arr[i];
			}
		}

		System.out.println("Largest = " + largest);
		System.out.println("Second largest = " + secondLargest);
	}
}