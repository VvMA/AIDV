package aidv.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import aidv.Validator;
import aidv.classes.Biomodel;

public class testValidator {

	public static void main(String[] args) throws Exception {
		String url=null;
		url="http://www.ebi.ac.uk/biomodels-main/download?mid=BIOMD0000000001";
		String json ;
//		= Validator.getBiomodel(url);
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();	
		json = ow.writeValueAsString(new Biomodel());
		System.out.println(json);
		
	}
}
