package aidv.tools;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import aidv.classes.Annotation;
import aidv.classes.Biomodel;
import aidv.classes.BrowserFactory;
import aidv.classes.OntologyFactory;
import aidv.classes.ModelElement;
import aidv.classes.Ontology;
import aidv.classes.browser.Identifiers_org;
import aidv.classes.browser.OntologyBrowser;

public class ParserJ {
	Document document;
	
	
	public ParserJ(File f) throws ParserConfigurationException, SAXException, IOException{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		this.document = builder.parse(f);
	}
	public ParserJ(InputStream iStream) throws ParserConfigurationException, SAXException, IOException{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		this.document = builder.parse(iStream);
	}
	public ParserJ(String url) throws ParserConfigurationException, SAXException, IOException{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		this.document = builder.parse(url);
	}
	
	public Biomodel getBiomodel() throws Exception{
		return build(document);
	}
	
	private List<ModelElement> better_read(String tag, Document d)
			throws Exception {
		
		NodeList n_list = d.getElementsByTagName(tag);
		List<ModelElement> result = new ArrayList<ModelElement>();
		int it_o = n_list.getLength();
		for (int i = 0; i < it_o; i++) {
			Node node = n_list.item(i);
			List<String> temp = getHTML(StringtoArray(nodeasString(node)));
			if(temp.isEmpty()){
				return null;
			}
			Element e = (Element) node;
			ModelElement m1 = new ModelElement();
			List<Annotation> res = new ArrayList<Annotation>();
			int it = temp.size();
			for (int j = 0; j < it; j++) {
				Annotation a1 = new Annotation(temp.get(j));
				res.add(a1);
			}
			m1.setId(e.getAttribute("id"));
			if(e.hasAttribute("name"))
				m1.setName(e.getAttribute("name"));
			m1.setAnnotations(res);
			result.add(m1);
		}
		if(result.size()==0)return null;
		return result;
	}

	private Biomodel build(Document d) throws Exception {
		Biomodel model = new Biomodel();

		String[] name = getName(d);
		model.setId(name[0]);
		model.setName(name[1]);
		model.setFunctionDefinition(better_read("FunctionDefinition", d));
		validateAnnotations(model.getFunctionDefinition());
		
		model.setUnitDefinition(better_read("unitDefinition", d));
		validateAnnotations(model.getUnitDefinition());
		
		model.setCompartementType(better_read("compartementType", d));
		validateAnnotations(model.getCompartementType());
		
		model.setSpeciesType(better_read("speciesType", d));
		validateAnnotations(model.getSpeciesType());
		
		model.setCompartement(better_read("compartement", d));
		validateAnnotations(model.getCompartement());
		
		model.setSpecies(better_read("species", d));
		validateAnnotations(model.getSpecies());
		
		model.setParameter(better_read("parameter", d));
		validateAnnotations(model.getParameter());
		
		model.setInitialAssignment(better_read("initialAssignment", d));
		validateAnnotations(model.getInitialAssignment());
		
		model.setAssignmentRule(better_read("assignmentRule", d));
		validateAnnotations(model.getAssignmentRule());
		
		model.setConstraint(better_read("constraint", d));
		validateAnnotations(model.getConstraint());
		
		model.setReaction(better_read("reaction", d));
		validateAnnotations(model.getReaction());
		
		model.setEvent(better_read("event", d));
		validateAnnotations(model.getEvent());
		
		return model;
	}

	private String[] getName(Document d) {
		NodeList m_list = d.getElementsByTagName("model");
		String[] result = new String[2];
		result[0] = "not available";
		result[1] = "not available";
		for (int i = 0; i < m_list.getLength(); i++) {
			Node node = m_list.item(i);
			Element e = (Element) node;
			result[0] = (e.getAttribute("id"));
			result[1] = (e.getAttribute("name"));
		}
		return result;
	}

	private String nodeasString(Node node)
			throws TransformerFactoryConfigurationError, TransformerException {
		StringWriter str = new StringWriter();
		Transformer t = TransformerFactory.newInstance().newTransformer();
		t.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
		t.transform(new DOMSource(node), new StreamResult(str));

		return str.toString();
	}

	private String[] StringtoArray(String xml) {
		String[] result = xml.split("<");
		return result;
	}

	private void validateAnnotations(List<ModelElement> elements){
		List<Annotation>annotations=new ArrayList<Annotation>();
		if(elements!=null)
			for(ModelElement element:elements) {
			annotations=element.getAnnotations();
			for(Annotation annotation:annotations) {
				int index=annotations.indexOf(annotation);
				OntologyBrowser identifiers=new Identifiers_org();
				try {
				annotation=identifiers.get(annotation);
				}catch(Exception e) {
					e.printStackTrace();
				}
				
				if(annotation!=null) {
					Ontology ontology=OntologyFactory.getOntology(annotation);
					OntologyBrowser oBrowser=BrowserFactory.getBrowser(ontology);					
					if(oBrowser!=null) {		
						try {
							annotations.set(index,oBrowser.get(annotation));
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		}
	}
	private List<String> getHTML(String[] split) {

		List<String> a1 = new ArrayList<String>();
		String res = "rdf:resource=";
		String res2 = "sboTerm";
		for (int i = 0; i < split.length; i++) {
			if (split[i].contains(res)) {
				a1.add(split[i].substring(
						split[i].indexOf("rdf:resource=") + 14,
						split[i].indexOf("/>") - 1));
			}
			if (split[i].contains(res2)) {
				a1.add("http://identifiers.org/biomodels.sbo/"+split[i].substring(split[i].indexOf("SBO:"),
						split[i].indexOf("SBO:") + 11));
			}
		}

		return a1;
	}

}
