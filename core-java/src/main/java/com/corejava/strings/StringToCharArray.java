package com.corejava.strings;

public class StringToCharArray {

	public static void main(String[] args) {
		String text = "My Name Is Tasneef";
		char[] characters = text.toCharArray();

		// length is a field on an array, not a method like String.length()
		for (int i = 0; i < characters.length; i++) {
			System.out.println(characters[i]);
		}
	}
}