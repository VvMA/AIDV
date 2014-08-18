package aidv.classes;

import java.util.List;

/**
 * @author Stefan
 *
 */
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
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Annotation other = (Annotation) obj;
		if (url == null) {
			if (other.url != null) {
				return false;
			}
		} else if (!url.equals(other.url)) {
			return false;
		}
		return true;
	}
	
}
