package com.corejava.strings;

public class DuplicateCharacters {

	public static void main(String[] args) {
		String text = "my name is tasneef";
		char[] characters = text.toCharArray();

		int duplicates = 0;
		for (int i = 0; i < characters.length; i++) {
			for (int j = i + 1; j < characters.length; j++) {
				if (characters[i] == characters[j] && characters[i] != ' ') {
					System.out.println(characters[i]);
					duplicates++;
				}
			}
		}

		System.out.println("Duplicate count = " + duplicates);
	}
}