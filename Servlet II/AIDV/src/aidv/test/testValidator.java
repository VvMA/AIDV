package aidv.test;

import aidv.Validator;

public class testValidator {

	public static void main(String[] args) throws Exception {
		String url=null;
		url="http://www.ebi.ac.uk/biomodels-main/download?mid=BIOMD0000000001";
		String json = Validator.getBiomodel(url);
		System.out.println(json);

	}

}
