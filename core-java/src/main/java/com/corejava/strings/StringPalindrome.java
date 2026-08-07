package com.corejava.strings;

public class StringPalindrome {

	public static void main(String[] args) {
		String text = "reviver";

		String reversed = "";
		for (int i = text.length() - 1; i >= 0; i--) {
			reversed = reversed + text.charAt(i);
		}

		System.out.println(reversed);

		if (text.equals(reversed)) {
			System.out.println("String is a palindrome");
		} else {
			System.out.println("String is not a palindrome");
		}
	}
}