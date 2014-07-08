package aidv.classes;

import java.util.List;

public class Annotation {
	
	public String id;
	String url;
	String label;
	String definition;
	Boolean exists;
	Boolean obsolete;	
	List<Link> resource;
	List<Link> consider;
	
	public Annotation() {	
	}
	public Annotation(String url) {
		this();
		this.setUrl(url);
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getDefinition() {
		return definition;
	}
	public void setDefinition(String definition) {
		this.definition = definition;
	}
	public Boolean isExists() {
		return exists;
	}
	public void setExists(Boolean exists) {
		this.exists = exists;
	}
	public Boolean isObsolete() {
		return obsolete;
	}
	public void setObsolete(Boolean obsolete) {
		this.obsolete = obsolete;
	}
	public List<Link> getResource() {
		return resource;
	}
	public void setResource(List<Link> resource) {
		this.resource = resource;
	}
	public List<Link> getConsider() {
		return consider;
	}
	public void setConsider(List<Link> consider) {
		this.consider = consider;
	}
}
