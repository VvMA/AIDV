package aidv;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import aidv.classes.Annotation;
import aidv.classes.browser.ChEBIBrowser;
import aidv.classes.browser.SboBrowser;

public class ChebiTest {

	public static void main(String[] args) throws IOException {
		Annotation a=new Annotation();
		a.id="SBO:0000255";
		SboBrowser browser=new SboBrowser();
		a=browser.get(a);
		
		System.out.println(a.isObsolete());
	}
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
}
