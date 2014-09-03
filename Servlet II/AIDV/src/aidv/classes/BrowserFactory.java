package aidv.classes;

import aidv.classes.browser.ChEBIBrowser;
import aidv.classes.browser.InterProBrowser;
import aidv.classes.browser.OntologyBrowser;
import aidv.classes.browser.QuickGoBrowser;
import aidv.classes.browser.SboBrowser;
import aidv.classes.browser.UniprotBrowser;

/**
 * @author Stefan
 *
 */
public class BrowserFactory {
	// array representing the implemented OntologyBrowser's
	final static OntologyBrowser[] ontologybrowser={new QuickGoBrowser(),new SboBrowser(),new ChEBIBrowser(),new InterProBrowser(),new UniprotBrowser()}; 
	/**
	 * @param o Ontology of a given Annotation
	 * @return OntologyBrowser for access to Ontology Resources
	 */
	public static OntologyBrowser getBrowser(Ontology o) {
		for(OntologyBrowser oBrowser:ontologybrowser) {
			if(oBrowser.getOntologys().indexOf(o)>=0)
				return oBrowser;
		}
		return null;		
	}
}
