package com.corejava.strings;

public class CountVowels {

	public static void main(String[] args) {
		String text = "my name is tasneef";

		int vowels = 0;
		String withoutVowels = "";

		for (int i = 0; i < text.length(); i++) {
			char ch = text.charAt(i);
			if (ch == 'a' || ch == 'e' || ch == 'i' || ch == 'o' || ch == 'u') {
				vowels++;
			} else {
				withoutVowels = withoutVowels + ch;
			}
		}

		System.out.println("Without vowels = " + withoutVowels);
		System.out.println("Vowel count = " + vowels);
	}
}