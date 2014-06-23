package aidv.classes;

public enum Ontology {
	SBO("http://identifiers.org/biomodels.sbo/SBO:\\d{7}$")
	,GO("http://identifiers.org/go/GO:\\d{7}$");
	String identifier_pattern;
	Ontology(String identifierPattern){
		this.identifier_pattern=identifierPattern;
	}
	public Boolean matches(String uri) {
		return uri.matches(identifier_pattern);
	}
}
