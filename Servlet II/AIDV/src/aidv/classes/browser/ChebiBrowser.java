package aidv.classes.browser;

import java.io.IOException;
import java.util.List;

import aidv.classes.Annotation;
import uk.ac.ebi.chebi.webapps.chebiWS.client.ChebiWebServiceClient;
import uk.ac.ebi.chebi.webapps.chebiWS.model.*;
public class ChebiBrowser extends OntologyBrowser {

	@Override
	public Annotation get(Annotation annotation) throws IOException {
		    try {

		      // Create client
		      ChebiWebServiceClient client = new ChebiWebServiceClient();
		      Entity entity = client.getCompleteEntity("CHEBI:15377");
		      System.out.println("GetName: " + entity.getChebiAsciiName());
		      List<DataItem> synonyms = entity.getSynonyms();
		      // List all synonyms
		      for ( DataItem dataItem : synonyms ) {
		        System.out.println("synonyms: " + dataItem.getData());
		      }

		    } catch ( ChebiWebServiceFault_Exception e ) {
		      System.err.println(e.getMessage());
		    }
		return null;
	}

}
