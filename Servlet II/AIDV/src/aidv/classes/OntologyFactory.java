package aidv.classes;

/**
 * @author Stefan
 *
 */
public class OntologyFactory {
	/**
	 * @param annotation
	 * @return
	 */
	public static Ontology getOntology(Annotation annotation) {
		for(Ontology o:Ontology.values()) {
			if(o.matches(annotation.getUrl()))
				return o;
		}
		return null;
	}
}
