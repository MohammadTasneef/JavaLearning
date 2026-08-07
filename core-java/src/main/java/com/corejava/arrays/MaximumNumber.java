package com.corejava.arrays;

import java.util.Scanner;

public class MaximumNumber {

	public static void main(String[] args) {
		try (Scanner scanner = new Scanner(System.in)) {
			System.out.println("Enter the number of elements");
			int count = scanner.nextInt();

			if (count < 1) {
				System.out.println("Nothing to compare");
				return;
			}

			// sized from the input. The old version always allocated 10, so entering more
			// than 10 elements threw ArrayIndexOutOfBoundsException.
			int[] numbers = new int[count];

			System.out.println("Enter the elements");
			for (int i = 0; i < count; i++) {
				numbers[i] = scanner.nextInt();
			}

			int max = numbers[0];
			for (int i = 1; i < count; i++) {
				if (numbers[i] > max) {
					max = numbers[i];
				}
			}

			System.out.println("Maximum number is = " + max);
		}
	}
}