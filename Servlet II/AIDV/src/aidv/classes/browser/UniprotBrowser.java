package aidv.classes.browser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import aidv.Validator;
import aidv.classes.Annotation;
import aidv.classes.Link;
import aidv.classes.Ontology;

public class UniprotBrowser extends OntologyBrowser {
	@SuppressWarnings("serial")
	public UniprotBrowser() {
		this.setOntologys(new ArrayList<Ontology>(){
	        {add(Ontology.UNIPROT);}
	    });		
	}
	/** Helpermethod for connecting to the UNIPROT website
	 * 
	 * @param urlToGet url representing the ResultPage for the entry of a given annotation
	 * @return ResultPage for the entry of a given annotation of InterPro
	 * @throws IOException
	 */
	public static String connect(String urlToGet) throws IOException {
	 	URL url;
        HttpURLConnection conn;
        BufferedReader rd;
        String line;
        String result = null;
        url = new URL(urlToGet);
        conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        InputStream in=conn.getInputStream();
        rd = new BufferedReader(
                new InputStreamReader(in));
        while ((line = rd.readLine()) != null) {
        	if(result==null)
        		result=new String();
            result += line;
        }
        rd.close();
        return result;
	}
	@Override
	public Annotation get(Annotation a) throws IOException {
		String result=null;
		Link interpro=null;
		for(Link link:a.getResource()) {
			if(link.getUrl().contains(".uniprot.org/uniprot/"))
				interpro=link;
		}
		String url =interpro.getUrl();
		System.out.println(url);
		try {
			result=connect(url);
		}
		catch(IOException e) {
			e.printStackTrace();
			a.setExists(false);
		}
		if(result!=null) {
			a.setObsolete(false);
			Document doc = Jsoup.parse(result);
			Elements status=doc.select("#status");			
			if(status!=null) {
				if(status.text().contains("Obsolete")) {
					a.setObsolete(true);					
				}else {
					a.setLabel(doc.select("span.FULL").text());
					
				}
			}
			
		}
		else {
			a.setExists(false);
		}
		return a;
	}

	public static void main(String[] args) throws Exception {
		System.out.println(Validator.getAnnotation("http://identifiers.org/uniprot/P0CD34"));
	}
}
