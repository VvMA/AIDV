package aidv.classes.browser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import aidv.classes.Annotation;
import aidv.classes.Ontology;

public abstract class OntologyBrowser {
	protected List<Ontology> ontologys=new ArrayList<Ontology>();
	
	public abstract Annotation get(Annotation annotation) throws IOException;
	
	public List<Ontology> getOntologys(){
		return ontologys;
	}
	public void setOntologys(List<Ontology> ontologys){
		this.ontologys=ontologys;
	}
}
