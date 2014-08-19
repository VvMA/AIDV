package aidv.classes.browser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import aidv.classes.Annotation;
import aidv.classes.Ontology;
import uk.ac.ebi.chebi.webapps.chebiWS.client.ChebiWebServiceClient;
import uk.ac.ebi.chebi.webapps.chebiWS.model.*;
/**
 * @author Stefan
 *
 */
public class ChEBIBrowser extends OntologyBrowser {
	/**
	 * 
	 */
	@SuppressWarnings("serial")
	public ChEBIBrowser() {
		this.setOntologys(new ArrayList<Ontology>(){
	        {add(Ontology.CHEBI);}
	    });		
	}
	/* (non-Javadoc)
	 * @see aidv.classes.browser.OntologyBrowser#get(aidv.classes.Annotation)
	 */
	@Override
	public Annotation get(Annotation annotation) throws IOException {
		try {
		      // Create client
		      ChebiWebServiceClient client = new ChebiWebServiceClient();
		      Entity entity = client.getCompleteEntity(annotation.id);
		      annotation.setLabel(entity.getChebiAsciiName());
			  annotation.setDefinition(entity.getDefinition());
			  annotation.setExists(true);

		    } catch ( ChebiWebServiceFault_Exception e ) {
		      System.err.println(e.getMessage());
		      annotation.setExists(false);
		    }
		return annotation;
	}

}
