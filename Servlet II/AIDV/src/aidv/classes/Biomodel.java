package aidv.classes;

import java.util.List;

/**
 * @author Stefan
 *
 */
public class Biomodel {
	
	String id;
	String name;
	// Modelelements represting the different parts of an sbml
	List<ModelElement> functionDefinition;
	List<ModelElement> unitDefinition;
	List<ModelElement> compartementType;
	List<ModelElement> speciesType;
	List<ModelElement> compartement;
	List<ModelElement> species;
	List<ModelElement> parameter;
	List<ModelElement> initialAssignment;
	List<ModelElement> assignmentRule;
	List<ModelElement> constraint;
	List<ModelElement> reaction;
	List<ModelElement> event;
	
	public List<ModelElement> getCompartementType() {
		return compartementType;
	}
	public void setCompartementType(List<ModelElement> compartementType) {
		this.compartementType = compartementType;
	}
	public List<ModelElement> getSpeciesType() {
		return speciesType;
	}
	public void setSpeciesType(List<ModelElement> speciesType) {
		this.speciesType = speciesType;
	}
	public List<ModelElement> getAssignmentRule() {
		return assignmentRule;
	}
	public void setAssignmentRule(List<ModelElement> assignmentRule) {
		this.assignmentRule = assignmentRule;
	}
	
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
