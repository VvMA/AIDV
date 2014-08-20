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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import uk.ac.ebi.miriam.lib.MiriamLink;
import aidv.classes.Annotation;
import aidv.classes.Link;

/**
 * @author Stefan
 *
 */
public class Identifiers_org extends OntologyBrowser{
	
	/** Convert miriam urn to identifiers.org url 
	 * @param urn Miriam Registry URN 
	 * @return identifiers.org url
	 */
	public static String transformUrn(String urn) {
        MiriamLink link = new MiriamLink();
        link.setAddress("http://www.ebi.ac.uk/miriamws/main/MiriamWebServices");    
		String url=link.convertURN(urn);
		return url;
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
		String url =a.getUrl();
		if(url.matches("http://identifiers.org/.+")) {
			url=url.replace("http://identifiers.org/", "http://info.identifiers.org/").concat(".html");
			System.out.println(url);
			try {
			result=connect(url);
			}
			catch(IOException e) {
				a.setExists(false);
			}
		}
		else if(url.matches("urn:miriam:.+")) {
			a.setUrl(transformUrn(a.getUrl()));
			return get(a);
		}
		if(result!=null) {
			Document doc = Jsoup.parse(result);
			if(doc.select("div.info").text().startsWith("Alternative ways")) {
				a.setUrl(doc.select("div.info > a:nth-child(3)").get(0).attr("href").replace("http://info.","http://"));
				return get(a);
			}
			else {	
			Elements resources=	doc.select("#content").first().getElementsByClass("resource_info");		
			Annotation a1=new Annotation();
			a1.id=doc.select("div.info:nth-child(1) > span:nth-child(1)").first().text();
			a1.setUrl(a.getUrl());
			a1.setExists(true);
			ArrayList<Link>links=new ArrayList<Link>();
			System.out.println(resources.size());
			for(Element resource: resources) {
				String href=resource.getElementsByClass("format").select("a").get(0).attr("href");
				String name=resource.select("span.institution").get(0).text();
				links.add(new Link(name,href));
				System.out.println(resource.select("span.institution").get(0).text());
			}
			a1.setResource(links);
			return a1;
			}
		}
		else return a;
		
	}
}
