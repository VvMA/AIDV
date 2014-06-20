package aidv.classes;

import java.util.List;

public class Biomodel {
	
	String id;
	String name;
	List<ModelElement> functionDefinition;
	List<ModelElement> unitDefinition;
	List<ModelElement> compartement;
	List<ModelElement> species;
	List<ModelElement> parameter;
	List<ModelElement> initialAssignment;
	List<ModelElement> rule;
	List<ModelElement> constraint;
	List<ModelElement> reaction;
	List<ModelElement> event;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<ModelElement> getFunctionDefinition() {
		return functionDefinition;
	}
	public void setFunctionDefinition(List<ModelElement> functionDefinition) {
		this.functionDefinition = functionDefinition;
	}
	public List<ModelElement> getUnitDefinition() {
		return unitDefinition;
	}
	public void setUnitDefinition(List<ModelElement> unitDefinition) {
		this.unitDefinition = unitDefinition;
	}
	public List<ModelElement> getCompartement() {
		return compartement;
	}
	public void setCompartement(List<ModelElement> compartement) {
		this.compartement = compartement;
	}
	public List<ModelElement> getSpecies() {
		return species;
	}
	public void setSpecies(List<ModelElement> species) {
		this.species = species;
	}
	public List<ModelElement> getParameter() {
		return parameter;
	}
	public void setParameter(List<ModelElement> parameter) {
		this.parameter = parameter;
	}
	public List<ModelElement> getInitialAssignment() {
		return initialAssignment;
	}
	public void setInitialAssignment(List<ModelElement> initialAssignment) {
		this.initialAssignment = initialAssignment;
	}
	public List<ModelElement> getRule() {
		return rule;
	}
	public void setRule(List<ModelElement> rule) {
		this.rule = rule;
	}
	public List<ModelElement> getConstraint() {
		return constraint;
	}
	public void setConstraint(List<ModelElement> constraint) {
		this.constraint = constraint;
	}
	public List<ModelElement> getReaction() {
		return reaction;
	}
	public void setReaction(List<ModelElement> reaction) {
		this.reaction = reaction;
	}
	public List<ModelElement> getEvent() {
		return event;
	}
	public void setEvent(List<ModelElement> event) {
		this.event = event;
	}
	
}
