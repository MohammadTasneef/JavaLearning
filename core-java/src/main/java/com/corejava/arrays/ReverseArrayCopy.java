package com.corejava.arrays;

import java.util.Scanner;

public class ReverseArrayCopy {

	public static void main(String[] args) {
		try (Scanner scanner = new Scanner(System.in)) {
			System.out.println("Enter the number of elements");
			int count = scanner.nextInt();

			if (count < 1) {
				System.out.println("Nothing to reverse");
				return;
			}

			// both arrays sized from the input. The old version hardcoded 10 and 4, so it
			// crashed as soon as more than four elements were entered.
			int[] source = new int[count];

			System.out.println("Enter the elements");
			for (int i = 0; i < count; i++) {
				source[i] = scanner.nextInt();
			}

			int[] reversed = new int[count];
			for (int i = count - 1, j = 0; i >= 0; i--, j++) {
				reversed[j] = source[i];
			}

			System.out.println("Reversed copy:");
			for (int i = 0; i < reversed.length; i++) {
				System.out.println(reversed[i]);
			}
		}
	}
}