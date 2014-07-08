package aidv.classes.browser;

import java.io.IOException;
import java.util.ArrayList;

import aidv.classes.Annotation;
import aidv.classes.Ontology;

public class InterProBrowser extends OntologyBrowser{
	@SuppressWarnings("serial")
	public InterProBrowser() {
		this.setOntologys(new ArrayList<Ontology>(){
	        {add(Ontology.INTERPRO);}
	    });		
	}
	@Override
	public Annotation get(Annotation annotation) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

}
