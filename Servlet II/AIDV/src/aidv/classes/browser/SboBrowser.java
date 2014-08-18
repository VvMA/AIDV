package aidv.classes.browser;

import java.io.IOException;
import java.util.ArrayList;

import uk.ac.ebi.sbo.common.Term;
import uk.ac.ebi.sbo.ws.client.SBOLink;
import aidv.classes.Annotation;
import aidv.classes.Ontology;

/**
 * @author Stefan
 *
 */
public class SboBrowser extends OyBrowser{

	@SuppressWarnings("serial")
	public SboBrowser() {
		this.setOntologys(new ArrayList<Ontology>(){
	        {add(Ontology.SBO);}
	    });		
	}
	@Override
	public Annotation get(Annotation annotation) throws IOException {
	    // creation of the link to the SBO Web Services
	    SBOLink link = new SBOLink();	    
	    // term retrieval (with direct access to its details)
	    Term term = link.getTerm(annotation.id);	    
	    annotation.setLabel(term.getName());
	    annotation.setDefinition(term.getDefinition());
	    annotation.setObsolete(term.isObsolete());	    
		return annotation;
	}

}
