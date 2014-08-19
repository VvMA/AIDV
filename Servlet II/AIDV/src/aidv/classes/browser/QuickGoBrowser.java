package aidv.classes.browser;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import aidv.classes.Annotation;
import aidv.classes.Link;
import aidv.classes.Ontology;

public class QuickGoBrowser extends OntologyBrowser{
	
	@SuppressWarnings("serial")
	public QuickGoBrowser() {
		this.setOntologys(new ArrayList<Ontology>(){
	        {add(Ontology.GO);}
	    });		
	}	
	/* (non-Javadoc)
	 * @see aidv.classes.browser.OntologyBrowser#get(aidv.classes.Annotation)
	 */
	@Override
	public Annotation get(Annotation annotation) throws IOException {
		
		 URL u=new URL("http://www.ebi.ac.uk/QuickGO/GTerm?id="+annotation.id+"&format=oboxml");
	        // Connect
	        HttpURLConnection urlConnection = (HttpURLConnection) u.openConnection();

	        // Parse an XML document from the connection
	        InputStream inputStream = urlConnection.getInputStream();
	        Document xml;
			try {
				xml = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(inputStream);
			
		        inputStream.close();
	
		        // XPath is here used to locate parts of an XML document
		        XPath xpath=XPathFactory.newInstance().newXPath();
	
		        //Locate the term name and print it out

				annotation.setLabel(xpath.compile("/obo/term/name").evaluate(xml));
				annotation.setDefinition(xpath.compile("/obo/term/def/defstr").evaluate(xml));
				annotation.setExists(true);
				if(xpath.compile("/obo/term/is_obsolete").evaluate(xml).equals("1"))annotation.setObsolete(true);
				else annotation.setObsolete(false);
				XPathExpression expr =xpath.compile("/obo/term/consider");
				ArrayList<Link> consider=new ArrayList<Link>();
		        NodeList nodes = (NodeList) expr.evaluate(xml, XPathConstants.NODESET);		        
		        for (int i = 0; i < nodes.getLength(); i++) {
		        	Node n=nodes.item(i);
		        	Link link=new Link(n.getTextContent(),"http://identifiers.org/go/"+n.getTextContent());
		        	consider.add(link);
		        }
		        if(nodes.getLength()>0)
		        	annotation.setConsider(consider);
			} catch (XPathExpressionException|SAXException | ParserConfigurationException e) {
				e.printStackTrace();
			}
		return annotation;
	}

}
