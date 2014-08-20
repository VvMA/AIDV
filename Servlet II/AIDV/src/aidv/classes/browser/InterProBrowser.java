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
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import aidv.classes.Annotation;
import aidv.classes.Link;
import aidv.classes.Ontology;

public class InterProBrowser extends OntologyBrowser{
	@SuppressWarnings("serial")
	public InterProBrowser() {
		this.setOntologys(new ArrayList<Ontology>(){
	        {add(Ontology.INTERPRO);}
	    });		
	}
	/** Helpermethod for connecting to the InterPro website
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
	/* (non-Javadoc)
	 * @see aidv.classes.browser.OntologyBrowser#get(aidv.classes.Annotation)
	 */
	@Override
	public  Annotation get(Annotation a){
		String result=null;
		Link interpro=null;
		for(Link link:a.getResource()) {
			if(link.getUrl().contains("http://www.ebi.ac.uk/interpro/"))
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
			Elements warning=doc.select("#search-results > div:nth-child(2)");
			if(warning!=null) {
				if(warning.text().contains("did not match any records in our database"))
					a.setExists(false);
			}
			else {
				a.setLabel(doc.select(".strapline").text());
				a.setDefinition(doc.select("div.entry_desc").text());
			}
		}
		else {
			a.setExists(false);
		}
		return a;
		
	}

}
