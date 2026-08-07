package com.corejava.oop;

/**
 * Two levels up the chain: MultiLevelInheritance extends SingleLevelInheritance, which
 * extends InstanceMethods, so members from both parents are available here.
 */
public class MultiLevelInheritance extends SingleLevelInheritance {

	public static void main(String[] args) {
		MultiLevelInheritance obj = new MultiLevelInheritance();

		int sum = obj.add(1, 2);            // from InstanceMethods
		int product = obj.multiply(3, 4);   // from InstanceMethods
		obj.show(sum);                      // from InstanceMethods
		obj.show(product);
		obj.display(sum);                   // from SingleLevelInheritance
		obj.display(product);

		System.out.println("Sum is = " + InstanceMethods.addStatically(5, 6));
	}
}