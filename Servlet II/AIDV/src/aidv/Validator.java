package aidv;

import java.io.File;
import java.io.IOException;

import aidv.classes.Annotation;
import aidv.classes.Biomodel;
import aidv.classes.BrowserFactory;
import aidv.classes.Ontology;
import aidv.classes.OntologyFactory;
import aidv.classes.parser.ParserJ;
import aidv.classes.browser.Identifiers_org;
import aidv.classes.browser.OntologyBrowser;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public class Validator {
	/**
	 * @param uri identifiers.org or miriam registry uri (e.g. "http://identifiers.org/go/GO:0006915" or "urn:miriam:go:GO%3A0007274")
	 * @return json representation of a annotation
	 */
	public static String getAnnotation(String uri) {
		String json=null;
		Annotation annotation=new Annotation(uri);
		try {
			OntologyBrowser identifiers=new Identifiers_org();
			try {
			annotation=identifiers.get(annotation);
			}catch(Exception e) {
				e.printStackTrace();
			}			
			if(annotation!=null) {
				Ontology ontology=OntologyFactory.getOntology(annotation);
				OntologyBrowser oBrowser=BrowserFactory.getBrowser(ontology);	
				System.out.println(ontology);
				if(oBrowser!=null) {		
					try {
						annotation=oBrowser.get(annotation);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();	
			json = ow.writeValueAsString(annotation);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return json;
	}
	/**
	 * @param url url of a given xml file, must be directly downloadable
	 * @return json representation of a Biomodel
	 */
	public static String getBiomodel(String url) {
		String json=null;
		try {
			ParserJ parser = new ParserJ(url);
			System.out.println("loaded");
			Biomodel model = parser.getBiomodel();
			ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();	
			json = ow.writeValueAsString(model);
		}catch(Exception e) {			
			e.printStackTrace();
		}
		return json;
	}
	/**
	 * @param file xml file of a given biomodel
	 * @return json representation of a Biomodel
	 */
	public static String getBiomodel(File file) {
		String json=null;
		try {
			ParserJ p1 = new ParserJ(file);
			Biomodel b1 = p1.getBiomodel();
			ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();	
			json = ow.writeValueAsString(b1);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return json;
	}
}
