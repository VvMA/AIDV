package aidv;

import java.io.IOException;
import java.util.List;




import uk.ac.ebi.chebi.webapps.chebiWS.client.ChebiWebServiceClient;
import uk.ac.ebi.chebi.webapps.chebiWS.model.ChebiWebServiceFault_Exception;
import uk.ac.ebi.chebi.webapps.chebiWS.model.DataItem;
import uk.ac.ebi.chebi.webapps.chebiWS.model.Entity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import aidv.classes.Annotation;
import aidv.classes.BrowserFactory;
import aidv.classes.Ontology;
import aidv.classes.OntologyFactory;
import aidv.classes.browser.Identifiers_org;
import aidv.classes.browser.OntologyBrowser;
import aidv.classes.browser.SboBrowser;

public class OntologyTest {

	public static void main(String[] args) throws Exception { 
		System.out.println("http://identifiers.org/go/GO:0006915".matches("http://identifiers.org/.+"));
		OntologyBrowser browser=new SboBrowser();
		OntologyBrowser identifier=new Identifiers_org();		
		Annotation a=new Annotation("http://identifiers.org/chebi/CHEBI:15355");
		a=identifier.get(a);
		Ontology ontology=OntologyFactory.getOntology(a);
		OntologyBrowser oBrowser=BrowserFactory.getBrowser(ontology);					
		if(oBrowser!=null) {		
			try {
				a=oBrowser.get(a);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();	
		String json = ow.writeValueAsString(a);
		System.out.println(json);
	}
    
}
