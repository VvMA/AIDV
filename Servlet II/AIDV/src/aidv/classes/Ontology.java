package aidv.classes;

public enum Ontology {
	SBO("http://identifiers.org/biomodels.sbo/SBO:\\d{7}$")
	,GO("http://identifiers.org/go/GO:\\d{7}$")
	,CHEBI("http://identifiers.org/chebi/CHEBI:\\d+")
	,INTERPRO("http://identifiers.org/interpro/IPR\\d{6}"),
	UNIPROT("http://identifiers.org/uniprot/.*");
	String identifier_pattern;
	Ontology(String identifierPattern){
		this.identifier_pattern=identifierPattern;
	}
	public Boolean matches(String uri) {
		return uri.matches(identifier_pattern);
	}
}
