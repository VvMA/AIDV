package aidv.classes.browser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import uk.ac.ebi.chebi.webapps.chebiWS.client.ChebiWebServiceClient;
import uk.ac.ebi.chebi.webapps.chebiWS.model.*;
import aidv.classes.Annotation;
import aidv.classes.Ontology;
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
		      System.out.println(entity.getChebiAsciiName());
			  annotation.setDefinition(entity.getDefinition());
			  annotation.setExists(true);

		    } catch ( ChebiWebServiceFault_Exception e ) {
		      annotation.setExists(false);
		    }
		return annotation;
	}
	

}
