package aidv;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import com.fasterxml.jackson.databind.ObjectMapper;

import aidv.classes.Annotation;
import aidv.classes.Biomodel;
import aidv.classes.ModelElement;


public class test {

	public static void main(String[] args) throws Exception {
		for(int i=439;i<542;i++) {
			String formatted = String.format("%03d", i);
			String url="http://www.ebi.ac.uk/biomodels-main/download?mid=BIOMD0000000"+formatted;
			String json =connect("http://localhost:8080/AIDV/validate?biomodel="+url);
			System.out.println(formatted);
			Biomodel model = new ObjectMapper().readValue(json, Biomodel.class);
			System.out.println("loaded");		
			if(model!=null)try
	    	{
				File file= new File("annotation.log");
	    		FileWriter fw =new FileWriter( file.getPath() , true );
	    		if( file.exists()==false )			
	    			System.out.println("Datei wird erstellt.");    		
	    		PrintWriter pw = new PrintWriter( fw );
	    		for(Annotation a:getAnnotations(model)) {
					if(a.isExists()!=null)
					if(!a.isExists()) {
						pw.println("model: "+url+" nonexistent: "+a.getUrl());
					}
					if(a.isObsolete()!=null)
					if(a.isObsolete()) {
						pw.println("model: "+url+" obsolete: "+a.getUrl());
					}
				}
				fw.flush();
				fw.close();			
				pw.flush();
				pw.close();
	    	}catch( IOException e )
	    	{
	    		e.printStackTrace();
	    	}				
		}
	}
	public static Set<Annotation> getAnnotations(Biomodel biomodel){
		Set<Annotation> annotations=new HashSet<Annotation>();
		annotations=getAnnotations(annotations,biomodel.getFunctionDefinition());
		annotations=getAnnotations(annotations,biomodel.getUnitDefinition());
		annotations=getAnnotations(annotations,biomodel.getCompartementType());
		annotations=getAnnotations(annotations,biomodel.getSpeciesType());
		annotations=getAnnotations(annotations,biomodel.getCompartement());
		annotations=getAnnotations(annotations,biomodel.getSpecies());
		annotations=getAnnotations(annotations,biomodel.getParameter());
		annotations=getAnnotations(annotations,biomodel.getInitialAssignment());
		annotations=getAnnotations(annotations,biomodel.getAssignmentRule());
		annotations=getAnnotations(annotations,biomodel.getConstraint());
		annotations=getAnnotations(annotations,biomodel.getReaction());
		annotations=getAnnotations(annotations,biomodel.getEvent());
		return annotations;
		
	}
	public static Set<Annotation> getAnnotations(Set<Annotation> annotationSet,List<ModelElement> elements){
		if(elements!=null)
		for(ModelElement element:elements) {
			for(Annotation annotation:element.getAnnotations()) {
				if(!annotationSet.contains(annotation)) {
					annotationSet.add(annotation);
				}
			}
		}
		return annotationSet;
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
