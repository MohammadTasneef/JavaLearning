package com.corejava.strings;

public class CountWhiteSpace {

	public static void main(String[] args) {
		String text = " my name is tasneef ";

		int spaces = 0;
		String withoutSpaces = "";

		for (int i = 0; i < text.length(); i++) {
			char ch = text.charAt(i);
			if (ch == ' ') {
				spaces++;
			} else {
				withoutSpaces = withoutSpaces + ch;
			}
		}

		System.out.println("Without spaces = " + withoutSpaces);
		System.out.println("Space count = " + spaces);
	}
}