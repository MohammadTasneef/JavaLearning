package com.corejava.oop;

/**
 * An abstract class can hold both a finished method and an unfinished one. It cannot be
 * instantiated directly, so a subclass has to supply the abstract method.
 */
abstract class Greeter {

	void describe() {
		System.out.println("Hello");
	}

	abstract void greet();
}

class EnglishGreeter extends Greeter {

	@Override
	void greet() {
		System.out.println("Hi");
	}

	@Override
	void describe() {
		System.out.println("Overridden describe method");
	}
}

public class AbstractClassDemo {

	public static void main(String[] args) {
		// new Greeter() would not compile - an abstract class cannot be instantiated
		Greeter greeter = new EnglishGreeter();
		greeter.greet();
		greeter.describe();
	}
}