package aidv.classes.browser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import aidv.classes.Annotation;

public class indentifiers_org extends ontologybrowser{
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
	public static Annotation get(String url) throws IOException {
		Document doc = Jsoup.parse(connect(url));
		String annoid = doc.select("div.info:nth-child(1) > span:nth-child(1)").text();
		Elements resources=	doc.getElementsByClass("resource");
		for(Element resource: resources) {
//			System.out.println(resource.select("div.resource_info").attr("href"));
//			System.out.println(resource.select("div.resource_info").get(0).html());
		}
		Annotation a=new Annotation();
		a.setId(annoid);
		a.setExists(true);
		return a;
	}
	
	 public static void main(String[] args) throws Exception{
		 String resource1="http://info.identifiers.org/go/GO:0006915.html";
		 get(resource1);
	 }
}
