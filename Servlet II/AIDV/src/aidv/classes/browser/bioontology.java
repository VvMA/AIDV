package aidv.classes.browser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import aidv.classes.Annotation;

public class bioontology extends ontologybrowser{
	static final String REST_URL = "http://data.bioontology.org";
	static final String API_KEY = "a2ab2aa3-a4fe-43ce-b316-a89017b75fb0";
    static final ObjectMapper mapper = new ObjectMapper();
    static final ObjectWriter writer = mapper.writerWithDefaultPrettyPrinter();
	
	public static Annotation get(Annotation annotation) throws IOException {
		JsonNode result = jsonToNode(connect(REST_URL + "/search?q=" + annotation.getId()+"&exact_match=true&include_obsolete=true&include_properties=true&require_definition=true&include_views=true")).get("collection");
		if(result.isArray())
			result=result.get(0);
		annotation.setObsolete((result.get("obsolete").asBoolean()));
		if(result.get("definition").isArray())
			annotation.setDefinition(result.get("definition").get(0).asText());
		annotation.setLabel((result.get("prefLabel").asText()));
		annotation.addLink(result.get("links").get("ui").asText());
		return annotation;
	}
	
    private static JsonNode jsonToNode(String json) {
        JsonNode root = null;
        try {
            root = mapper.readTree(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return root;
    }
    private static String connect(String urlToGet) {
        URL url;
        HttpURLConnection conn;
        BufferedReader rd;
        String line;
        String result = "";
        try {
            url = new URL(urlToGet);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", "apikey token=" + API_KEY);
            conn.setRequestProperty("Accept", "application/json");
            rd = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            while ((line = rd.readLine()) != null) {
                result += line;
            }
            rd.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
	public static void main(String[] args) throws Exception{
		String resource1="http://info.identifiers.org/go/GO:0006915.html";
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();		 
		String json = ow.writeValueAsString(bioontology.get(indentifiers_org.get(resource1)));
		System.out.println(json);
	 }

}
