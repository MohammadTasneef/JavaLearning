package com.learning;

public class multiLevelInheritance extends singleLevelInheritance {

	public static void main(String[] args) {

		multiLevelInheritance obj2 = new multiLevelInheritance();
		int a = obj2.add(1, 2);                                // user defined from nonStaticMethod class
		int b = obj2.multiply(3, 4);                          // user defined from nonStaticMethod class
		obj2.show(a);                                        // user defined from nonStaticMethod class
		obj2.show(b);                                       // user defined from nonStaticMethod class
		obj2.display(a);                                   // user defined from singleLevelInheritance class
		obj2.display(b);                                  // user defined from singleLevelInheritance class
		int c = nonStaticMethod.add1(5, 6);              // user defined from multiLevelInheritance class
		System.out.println("Sum is = " + c);

	}

}
