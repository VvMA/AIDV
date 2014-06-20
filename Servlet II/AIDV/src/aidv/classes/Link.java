package aidv.classes;

public class Link {
	
	String name;
	String url;
	
	public Link(String name,String url) {
		this.name=name;
		this.url=url;		
	}
	public Link() {
		this(null,null);
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}	
}
