package aidv;

import uk.ac.ebi.miriam.lib.MiriamLink;

public class test {

	public static void main(String[] args) {
		MiriamLink link = new MiriamLink();
        link.setAddress("http://www.ebi.ac.uk/miriamws/main/MiriamWebServices");    
		String url=link.convertURN("urn:miriam:go:GO%3A0007274");

	}

}
