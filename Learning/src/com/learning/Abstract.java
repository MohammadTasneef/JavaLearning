package com.learning;

   abstract class hi {
	void disp() {
		System.out.println("Hello");
	}
	abstract void greet();
	
}
 class Parent extends hi{
	 
  	 void greet() {
		 System.out.println("Hi");
	 }
	 
	 void disp() {
		 System.out.println("Overidden disp method");		 
	 }
	 
 }
 
 public class Abstract {
	 public static void main(String[] args) {
		 Parent p = new Parent();
// hi p = new hi(); // This line will cause a compilation error because you cannot instantiate(create and object) an abstract class.
		 p.greet();
		 p.disp();
	 }
 }