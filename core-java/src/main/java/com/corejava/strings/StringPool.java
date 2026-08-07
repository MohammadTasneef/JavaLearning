package com.corejava.strings;

public class StringPool {

	public static void main(String[] args) {
		char[] characters = { 65, 66, 67, 68, 69 };

		// this constructor builds the String on the heap rather than reusing the pool
		String all = new String(characters);
		System.out.println(all);

		// start at index 1 and take 2 characters
		String part = new String(characters, 1, 2);
		System.out.println(part);
	}
}