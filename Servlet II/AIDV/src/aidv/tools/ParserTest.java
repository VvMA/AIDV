package aidv.tools;

import java.io.File;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import aidv.classes.Biomodel;
// Testfall behandelt nur Species (wird alles einziges ausgegeben)
// Methode nur zum Testen und uu nicht ressourcenschonend 
public class ParserTest {

	public static void main(String[] args) throws Exception {
		File f = new File("test3.xml");
		ParserJ p1 = new ParserJ(f);
		Biomodel b1 = p1.getBiomodel();
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();	
		String json = ow.writeValueAsString(b1);
		System.out.println(json);
//
//		System.out.println("ID : " + b1.getId());
//		System.out.println("Name : " + b1.getName());
//
//		List<ModelElement> temp = b1.getSpecies();//hier Ausgabe ändern
//		int t1 = temp.size();
//		for (int i = 0; i < t1; i++) {
//			System.out.println(temp.get(i).getId());
//			int t2 = temp.get(i).getAnnotations().size();
//			for (int j = 0; j < t2; j++) {
//				System.out
//						.println(temp.get(i).getAnnotations().get(j).getUrl());
//			}
//		}
	}

}


