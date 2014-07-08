package aidv.classes;

import aidv.classes.browser.OntologyBrowser;
import aidv.classes.browser.QuickGoBrowser;
import aidv.classes.browser.SboBrowser;

public class BrowserFactory {
	final static OntologyBrowser[] ontologybrowser={new QuickGoBrowser(),new SboBrowser()}; 
	public static OntologyBrowser getBrowser(Ontology o) {
		for(OntologyBrowser oBrowser:ontologybrowser) {
			if(oBrowser.getOntologys().indexOf(o)>=0)
				return oBrowser;
		}
		return null;		
	}
}
