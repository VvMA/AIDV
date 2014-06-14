package aidv.tools;

import java.io.File;

public class ParserTest {

	public static void main(String[] args) throws Exception {
		File f = new File("test2.xml");
		Parser p1 = new Parser(f);
		for(int i=0;i<p1.getIDENT().length;i++){
			System.out.println(""+p1.getIDENT()[i]);
		}
		System.out.println("--------------------------------");
		for(int i=0;i<p1.getSBO().length;i++){
			System.out.println(""+p1.getSBO()[i]);
		}

	}

}
