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

public class InterProBrowser extends OyBrowser{
	@SuppressWarnings("serial")
	public InterProBrowser() {
		this.setOntologys(new ArrayList<Ontology>(){
	        {add(Ontology.INTERPRO);}
	    });		
	}
	/** Helpermethod for connecting to the InterPro websites
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
	public  Annotation get(Annotation a){
		String result=null;
		String url =a.getResource().get(0).getUrl();
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
			a.setLabel(doc.select(".strapline").html());
			a.setDefinition(doc.select("div.entry_desc").text());
		}
		return a;
		
	}

}
