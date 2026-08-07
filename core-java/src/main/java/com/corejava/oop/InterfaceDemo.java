package com.corejava.oop;

/**
 * Interface methods are abstract by default, so the abstract keyword on them is redundant.
 * The implementing class must supply all of them, and can add methods of its own.
 */
interface Displayable {

	void display(String text);

	void greet(int number);

	void reset();
}

class ConsoleDisplay implements Displayable {

	@Override
	public void display(String text) {
		System.out.println("Value is displayed = " + text);
	}

	@Override
	public void greet(int number) {
		System.out.println("Hello = " + number);
	}

	@Override
	public void reset() {
		System.out.println("Reset");
	}

	// a concrete class can also declare methods that are not on the interface
	void extra() {
		System.out.println("A concrete class can have non abstract methods too");
	}
}

public class InterfaceDemo {

	public static void main(String[] args) {
		ConsoleDisplay display = new ConsoleDisplay();

		display.display("tasneef");
		display.greet(7);
		display.reset();
		display.extra();
	}
}