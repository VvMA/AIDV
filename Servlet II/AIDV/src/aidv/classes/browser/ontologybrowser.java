package aidv.classes.browser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import aidv.classes.Annotation;
import aidv.classes.Ontology;

public abstract class OntologyBrowser extends Thread{
	protected Annotation annotation;
	protected List<Ontology> ontologys=new ArrayList<Ontology>();
	
	public abstract Annotation get(Annotation annotation) throws IOException;
	
	public List<Ontology> getOntologys(){
		return ontologys;
	}
	public void setOntologys(List<Ontology> ontologys){
		this.ontologys=ontologys;
	}
	public Annotation getAnnotation() {
		return annotation;
	}
	public void setAnnotation(Annotation annotation) {
		this.annotation = annotation;
	}	
	@Override public void run()
	{
	 Annotation a=this.getAnnotation();
	 try {
		this.setAnnotation(get(a));
	 } catch (Exception e) {
		e.printStackTrace();
	 }
	}
}
