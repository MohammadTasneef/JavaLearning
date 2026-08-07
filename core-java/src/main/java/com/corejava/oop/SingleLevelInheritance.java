package com.corejava.oop;

public class SingleLevelInheritance extends InstanceMethods {

	public int display(int id) {
		System.out.println("The ID number is = " + id);
		return id;
	}

	public static void main(String[] args) {
		SingleLevelInheritance obj = new SingleLevelInheritance();

		int sum = obj.add(10, 20);           // inherited from InstanceMethods
		int product = obj.multiply(5, 20);   // inherited from InstanceMethods
		System.out.println("Sum is = " + sum);
		System.out.println("Multiplication is = " + product);

		obj.show(sum);                       // inherited from InstanceMethods
		obj.display(product);                // declared here

		InstanceMethods.addStatically(2, 3); // static, called on the class
	}
}