package aidv.tools;

import java.io.File;
import java.io.IOException;
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
import aidv.classes.ModelElement;

public class ParserJ {
	File f;
	
	public ParserJ(File f){
		this.f = f;
	}
	
	public Biomodel getBiomodel() throws Exception{
		return build(f);
	}

	private List<ModelElement> better_read(String tag, File f)
			throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(f);
		NodeList n_list = document.getElementsByTagName(tag);
		List<ModelElement> result = new ArrayList<ModelElement>();
		int it_o = n_list.getLength();
		for (int i = 0; i < it_o; i++) {
			Node node = n_list.item(i);
			Element e = (Element) node;
			ModelElement m1 = new ModelElement();
			List<String> temp = getHTML(StringtoArray(nodeasString(node)));
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
		return result;
	}

	private Biomodel build(File f) throws Exception {

		Biomodel b1 = new Biomodel();

		String[] name = getName(f);
		b1.setId(name[0]);
		b1.setName(name[1]);

		b1.setFunctionDefinition(better_read("FunctionDefinition", f));
		b1.setUnitDefinition(better_read("unitDefinition", f));
		b1.setCompartementType(better_read("compartementType", f));
		b1.setSpeciesType(better_read("speciesType", f));
		b1.setCompartement(better_read("compartement", f));
		b1.setSpecies(better_read("species", f));
		b1.setParameter(better_read("parameter", f));
		b1.setInitialAssignment(better_read("initialAssignment", f));
		b1.setAssignmentRule(better_read("assignmentRule", f));
		b1.setConstraint(better_read("constraint", f));
		b1.setReaction(better_read("reaction", f));
		b1.setEvent(better_read("event", f));

		return b1;
	}

	private String[] getName(File f) throws SAXException, IOException,
			ParserConfigurationException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;

		builder = factory.newDocumentBuilder();
		Document document = builder.parse(f);
		NodeList m_list = document.getElementsByTagName("model");
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

	private List<String> getHTML(String[] split) {

		List<String> a1 = new ArrayList<String>();
		String res = "rdf:resource=";
		String res2 = "sboTerm";
		for (int i = 0; i < split.length; i++) {
			if (split[i].contains(res)) {
				a1.add(split[i].substring(
						split[i].indexOf("rdf:resource=") + 14,
						split[i].indexOf("/>") - 2));
			}
			if (split[i].contains(res2)) {
				a1.add("http://identifiers.org/biomodels.sbo/"+split[i].substring(split[i].indexOf("SBO:"),
						split[i].indexOf("SBO:") + 11));
			}
		}

		return a1;
	}

}
