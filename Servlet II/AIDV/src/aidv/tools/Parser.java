package aidv.tools;

import java.io.File;
import java.util.ArrayList;






import java.util.HashSet;
import java.util.Set;

import org.jdom2.Document;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;

public class Parser {
	HashSet<String> a1 = new HashSet<String>();
	HashSet<String> a2 = new HashSet<String>();
	File f;
	
	public Parser(File f) throws Exception{
		this.f=f;
		load();
	}
	
	

	private String[] StringtoArray(String xml) {
		String[] result = xml.split("<");
		return result;
	}

	private void getHTML(String[] split) {
		
		//HashSet<String> a1 = new HashSet<String>();
		String res = "rdf:resource=";
		String res2 = "sboTerm";
		for (int i = 0; i < split.length; i++) {
			if (split[i].contains(res)) {
				a1.add(split[i].substring(
						split[i].indexOf("rdf:resource=") + 14,
						split[i].indexOf("/>") - 2));
			}
			if(split[i].contains(res2)){
				a2.add(split[i].substring(
						split[i].indexOf("SBO:") ,
						split[i].indexOf("SBO:")+11 ));
			}
		}

		//return a1;
	}

	private String readXMLtoString(File f) throws Exception {
		Document doc = null;
		SAXBuilder builder = new SAXBuilder();
		doc = builder.build(f);
		XMLOutputter out = new XMLOutputter();
		return out.outputString(doc);
        }
	
	public void load() throws Exception{
		getHTML(StringtoArray(readXMLtoString(f)));
	}
	
	public String[] getIDENT(){
		String[] result =a1.toArray(new String[0]);
		return result;
	}
	
	public String[] getSBO(){
		String[] result =a2.toArray(new String[0]);
		return result;
	}
	

}
