package aidv.classes.parser;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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

public class ParserJ{
	
	Document document;
	Biomodel biomodel;
	Set<Annotation> annotationSet;
	List<Annotation> annotationList;
	
	/**
	 * Constructor
	 * accepts a Biomodell as File, Stream or URL 
	 * @param f, iStream, url 
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public ParserJ(File f) throws ParserConfigurationException, SAXException, IOException{
		this();
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		this.document = builder.parse(f);
	}
	public ParserJ(InputStream iStream) throws ParserConfigurationException, SAXException, IOException{
		this();
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		this.document = builder.parse(iStream);
	}
	public ParserJ() throws ParserConfigurationException, SAXException, IOException{
		annotationSet=new HashSet<Annotation>();
		annotationList=new ArrayList<Annotation>();
	}
	public ParserJ(String url) throws ParserConfigurationException, SAXException, IOException{
		this();
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		this.document = builder.parse(url);
	}
	/**
	 * calls build to create a Biomodel from document, if not null
	 * @return Biomodel
	 * @throws Exception
	 */
	public Biomodel getBiomodel() throws Exception{
		if(this.biomodel==null)
			return build(document);
		else
			return this.biomodel;
	}
	
	/**
	 * @param biomodel
	 * @throws Exception
	 */
	public void setBiomodel(Biomodel biomodel) throws Exception{
		this.biomodel=biomodel;
	}
	
	/**
	 * 
	 * @param tag (tag value that we're searching for)
	 * @param d (Document that should be searched)
	 * @return List
	 * @throws Exception
	 */
	
	private List<ModelElement> better_read(String tag, Document d)
			throws Exception {
		
		NodeList n_list = d.getElementsByTagName(tag);//gets all nodes by tag
		List<ModelElement> result = new ArrayList<ModelElement>();
		int it_o = n_list.getLength();
		for (int i = 0; i < it_o; i++) {
			Node node = n_list.item(i);
			List<String> temp = getHTML(StringtoArray(nodeasString(node)));//all nodes were transformed into String, split into single lines and searched for AnnotationLinks
			//if(temp.isEmpty()){
			//	return null; //if the node did not contain any AnnotationLinks the result is null
			//}
			Element e = (Element) node;
			ModelElement m1 = new ModelElement();
			List<Annotation> res = new ArrayList<Annotation>();
			int it = temp.size();
			for (int j = 0; j < it; j++) {
				Annotation a1 = new Annotation(temp.get(j));
				res.add(a1); //adds found AnnotationLinks
			}
			m1.setId(e.getAttribute("id")); //sets id and name
			if(e.hasAttribute("name"))
				m1.setName(e.getAttribute("name"));
			m1.setAnnotations(res);
			result.add(m1);
		}
		if(result.size()==0)return null;
		return result;
	}
	/**
	 * builds Biomodel from Document d by requesting values for every node
	 * @param d
	 * @return Biomodel
	 * @throws Exception
	 */
	private Biomodel build(Document d) throws Exception {
		this.biomodel = new Biomodel();
		String[] name = getName(d);
		this.biomodel.setId(name[0]);
		this.biomodel.setName(name[1]);
		this.biomodel.setFunctionDefinition(better_read("FunctionDefinition", d));
		this.biomodel.setUnitDefinition(better_read("unitDefinition", d));
		this.biomodel.setCompartementType(better_read("compartementType", d));
		this.biomodel.setSpeciesType(better_read("speciesType", d));
		this.biomodel.setCompartement(better_read("compartement", d));
		this.biomodel.setSpecies(better_read("species", d));
		this.biomodel.setParameter(better_read("parameter", d));
		this.biomodel.setInitialAssignment(better_read("initialAssignment", d));
		this.biomodel.setAssignmentRule(better_read("assignmentRule", d));
		this.biomodel.setConstraint(better_read("constraint", d));
		this.biomodel.setReaction(better_read("reaction", d));
		this.biomodel.setEvent(better_read("event", d));
		this.validateAnnotations();
		return this.biomodel;
	}
	/**
	 * gets id and name for Biomodel
	 * @param d
	 * @return String[]
	 */
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
			if(result[0].equals(result[1])){
				result[1]=null;
			}
		}
		return result;
	}
	/**
	 * transforms a node to String
	 * @param node
	 * @return String
	 * @throws TransformerFactoryConfigurationError
	 * @throws TransformerException
	 */
	private String nodeasString(Node node)
			throws TransformerFactoryConfigurationError, TransformerException {
		StringWriter str = new StringWriter();
		Transformer t = TransformerFactory.newInstance().newTransformer();
		t.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
		t.transform(new DOMSource(node), new StreamResult(str));
		return str.toString();
	}
	/**
	 * splits String to single lines
	 * @param xml
	 * @return String[]
	 */
	private String[] StringtoArray(String xml) {
		String[] result = xml.split("<");
		return result;
	}
	/**
	 * 
	 */
	public void validateAnnotations() {
		if(this.biomodel!=null) {
			validateAnnotations(this.biomodel.getFunctionDefinition());
			validateAnnotations(this.biomodel.getUnitDefinition());
			validateAnnotations(this.biomodel.getCompartementType());
			validateAnnotations(this.biomodel.getSpeciesType());
			validateAnnotations(this.biomodel.getCompartement());
			validateAnnotations(this.biomodel.getSpecies());
			validateAnnotations(this.biomodel.getParameter());
			validateAnnotations(this.biomodel.getInitialAssignment());
			validateAnnotations(this.biomodel.getAssignmentRule());
			validateAnnotations(this.biomodel.getConstraint());
			validateAnnotations(this.biomodel.getReaction());
			validateAnnotations(this.biomodel.getEvent());
		}
	}
	/**
	 * 
	 * @param elements
	 */
	private void validateAnnotations(List<ModelElement> elements){
		List<Annotation>annotations=new ArrayList<Annotation>();
		if(elements!=null)
			for(ModelElement element:elements) {
				annotations=element.getAnnotations();
				int index=0;
				for(Annotation annotation:annotations) {
					System.out.println(annotation.getUrl());
					if(annotationSet.contains(annotation)) {
						annotations.set(index,annotationList.get(annotationList.indexOf(annotation)));
					}
					else{
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
									annotation=oBrowser.get(annotation);							
								} catch (IOException e) {
									e.printStackTrace();
								}
							}
							annotations.set(index,annotation);
						}
						annotationSet.add(annotation);
						annotationList.add(annotation);
					}
					index++;
				}
			}
	}
	/**
	 * gets AnnotationLinks from a String
	 * @param split
	 * @return List<String>
	 */
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
