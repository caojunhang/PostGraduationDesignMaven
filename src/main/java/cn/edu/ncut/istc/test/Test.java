package cn.edu.ncut.istc.test;

public class Test {

	public static void main(String[] args) {
		Parent p = new Child();
		p.sing();
	}

}
class Parent {
	public void sing() {
		System.out.println("Parent is sing");
	}

}

class Child extends Parent {
	public void sing() {
		System.out.println("Child is sing");
	}

}