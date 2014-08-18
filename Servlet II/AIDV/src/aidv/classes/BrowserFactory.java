package aidv.classes;

import aidv.classes.browser.ChEBIBrowser;
import aidv.classes.browser.InterProBrowser;
import aidv.classes.browser.OyBrowser;
import aidv.classes.browser.QuickGoBrowser;
import aidv.classes.browser.SboBrowser;

/**
 * @author Stefan
 *
 */
public class BrowserFactory {
	// array representing the implemented OntologyBrowser's
	final static OyBrowser[] ontologybrowser={new QuickGoBrowser(),new SboBrowser(),new ChEBIBrowser(),new InterProBrowser()}; 
	/**
	 * @param o Ontology of a given Annotation
	 * @return OntologyBrowser for access to Ontology Resources
	 */
	public static OyBrowser getBrowser(Ontology o) {
		for(OyBrowser oBrowser:ontologybrowser) {
			if(oBrowser.getOntologys().indexOf(o)>=0)
				return oBrowser;
		}
		return null;		
	}
}
