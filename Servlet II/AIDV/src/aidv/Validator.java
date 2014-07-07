package aidv;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

import aidv.classes.Annotation;
import aidv.classes.Biomodel;
import aidv.classes.BrowserFactory;
import aidv.classes.Ontology;
import aidv.classes.OntologyFactory;
import aidv.tools.ParserJ;
import aidv.classes.browser.Identifiers_org;
import aidv.classes.browser.OntologyBrowser;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public class Validator {
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
				if(oBrowser!=null) {		
					try {
						annotation=oBrowser.get(annotation);
					} catch (IOException e) {
						// TODO Auto-generated catch block
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
	public static String getBiomodel(String url) {
		String json=null;
		try {
			ParserJ parser = new ParserJ(url);
			Biomodel model = parser.getBiomodel();
			ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();	
			json = ow.writeValueAsString(model);
		}catch(Exception e) {			
			e.printStackTrace();
		}
		return json;
	}
	public static String getBiomodel(File biomodel) {
		String json=null;
		try {
			ParserJ p1 = new ParserJ(biomodel);
			Biomodel b1 = p1.getBiomodel();
			ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();	
			json = ow.writeValueAsString(b1);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return json;
	}
}
