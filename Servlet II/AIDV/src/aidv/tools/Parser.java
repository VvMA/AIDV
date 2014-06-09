package aidv.tools;

import java.io.File;
import java.util.ArrayList;






import org.jdom2.Document;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;

public class Parser {

	private static String[] StringtoArray(String xml) {
		String[] result = xml.split("<");
		return result;
	}

	private static ArrayList<String> getHTML(String[] split) {
		//String[] result = new String[split.length];
		ArrayList<String> a1 = new ArrayList<String>();
		String res = "rdf:resource=";
		for (int i = 0; i < split.length; i++) {
			if (split[i].contains(res)) {
				a1.add(split[i].substring(
						split[i].indexOf("rdf:resource=") + 14,
						split[i].indexOf("/>") - 2));
			}
		}

		return a1;
	}

	private static String readXMLtoString(File f) throws Exception {
		Document doc = null;
		SAXBuilder builder = new SAXBuilder();
		doc = builder.build(f);
		XMLOutputter out = new XMLOutputter();
		return out.outputString(doc);
        }
	
	public static String[] sbmltoStringArray(File f) throws Exception{
		String[] result = getHTML(StringtoArray(readXMLtoString(f))).toArray(new String[0]);
		
		return result;
	}
	

	//public static void main(String[] args) throws Exception {
	//	File f = new File("test1.xml");
	//	for(int i=0;i<sbmltoStringArray(f).length;i++){
	//		System.out.println(sbmltoStringArray(f)[i]);
	//	}
	//}

}
