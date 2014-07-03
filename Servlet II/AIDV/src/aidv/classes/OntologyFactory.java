package aidv.classes;

public class OntologyFactory {
	public static Ontology getOntology(Annotation annotation) {
		for(Ontology o:Ontology.values()) {
			if(o.matches(annotation.getUrl()))
				return o;
		}
		return null;
	}
}
