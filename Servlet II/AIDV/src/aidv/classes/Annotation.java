package aidv.classes;

import java.util.ArrayList;
import java.util.List;

public class Annotation {
	String id;
	String label;
	boolean exists;
	boolean obsolete;
	String definition;	
	List<String> physicalLinks;
	
	public String returnasString(){
		String result="ID: "+id+" /n label :"+label+" /n definition";
		
		return result;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public boolean isExists() {
		return exists;
	}
	public void setExists(boolean exists) {
		this.exists = exists;
	}
	public boolean isObsolete() {
		return obsolete;
	}
	public void setObsolete(boolean obsolete) {
		this.obsolete = obsolete;
	}
	public String getDefinition() {
		return definition;
	}
	public void setDefinition(String definition) {
		this.definition = definition;
	}
	public List<String> getPhysicalLinks() {
		return physicalLinks;
	}
	public void setLinks(List<String> physicalLinks) {
		this.physicalLinks = physicalLinks;
	}
	public void addLink(String link) {
		
		if(this.physicalLinks==null) this.physicalLinks=new ArrayList<String>();
			this.physicalLinks.add(link);
		
	}
}
