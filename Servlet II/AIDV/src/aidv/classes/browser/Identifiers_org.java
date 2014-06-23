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

public class Identifiers_org extends OntologyBrowser{
	
	public static String transformUrn(String urn) {
        MiriamLink link = new MiriamLink();
        link.setAddress("http://www.ebi.ac.uk/miriamws/main/MiriamWebServices");    
		String url=link.convertURN(urn);
		return url;
	}
	
	public static String connect(String urlToGet) {
	 	URL url;
        HttpURLConnection conn;
        BufferedReader rd;
        String line;
        String result = null;
        try {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
	}
	
	public  Annotation get(Annotation a) throws IOException {
		String result=null;
		String url =a.getUrl();
		if(url.matches("http://identifiers.org/.+")) {
			url=url.replace("http://identifiers.org/", "http://info.identifiers.org/").concat(".html");
			System.out.println(url);
			result=connect(url);
		}
		else if(url.matches("urn:miriam:.+")) {
			a.setUrl(transformUrn(a.getUrl()));
			return get(a);
		}
		if(result!=null) {
			Document doc = Jsoup.parse(result);
			Elements resources=	doc.getElementsByClass("resource");		
			Annotation a1=new Annotation();
			a1.setUrl(a.getUrl());
			a1.setExists(true);
			ArrayList<Link>links=new ArrayList<Link>();
			for(Element resource: resources) {
				String href=resource.select("div.resource_info > a").get(0).attr("href");
				String name=resource.select("div.resource_info > a > span.desc").get(0).text();
				links.add(new Link(name,href));
			}
			a1.setResource(links);
			return a1;
		}
		else return null;
		
	}
	
	 public static void main(String[] args) throws Exception{
		 Annotation a =new Annotation("http://identifiers.org/go/GO:0006915");
		 a=new Annotation("urn:miriam:go:GO%3A0007274");
		 OntologyBrowser identifiers=new Identifiers_org();
		 a= identifiers.get(a);
		 ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();	
		 System.out.println(ow.writeValueAsString(a));
	 }
}
