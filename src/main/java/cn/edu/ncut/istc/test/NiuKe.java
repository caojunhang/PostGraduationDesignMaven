package cn.edu.ncut.istc.test;

import org.junit.Test;

public class NiuKe {

	@Test
	public void testInt() {
		Integer i01 = 59;
		int i02 = 59;
		Integer i03 = Integer.valueOf(59);
		Integer i04 = new Integer(59);

		System.out.println(i01 == i02);
		System.out.println(i01 == i03);
		System.out.println(i03 == i04);
		System.out.println(i02 == i04);
	}

	/*
	 * toUpperCase()方法会返回一个String值，这里并没有对返回值进行处理。所以不影响x的值。
	 * 如果是x = x.toUpperCase();就是把返回值赋值给x
	 */
	@Test
	public void testString() {

		String x = "fmn";
		x.toUpperCase();
		String y = x.replace('f', 'F');
		y = y + "wxy";
		System.out.println(y);
	}
	
	@Test
	public void testStringV2() {
		String x="fmn";
		x = x.toUpperCase();
		String y = x.replace('F', 'f');
		y = y + "wxy";
		System.out.println(y);
	}

	

}
