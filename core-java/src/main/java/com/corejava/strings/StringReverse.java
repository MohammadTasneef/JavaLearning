package com.corejava.strings;

public class StringReverse {

	public static void main(String[] args) {
		String text = "Name is Tasneef";

		// walk backwards and append, so the result comes out reversed
		String reversed = "";
		for (int i = text.length() - 1; i >= 0; i--) {
			reversed = reversed + text.charAt(i);
		}

		System.out.println("Length = " + text.length());
		System.out.println(reversed);
	}
}