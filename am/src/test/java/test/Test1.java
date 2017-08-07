package test;

import javax.servlet.http.HttpServlet;

public class Test1 extends HttpServlet {
	public static void main(String[] args) {

		char x = 'x';
		Object y = x;
		System.out.println(y.getClass());
		
		
		
	}
}
