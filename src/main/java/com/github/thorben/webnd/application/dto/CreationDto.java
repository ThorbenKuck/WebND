package com.github.thorben.webnd.application.dto;

public class CreationDto {

	private String description;
	private String name;
	private Integer typeId;
	private String preDeterminedValue;
	private boolean required;

	public CreationDto(String name, String description, Integer typeId, String preDeterminedValue, boolean required) {
		this.description = description;
		this.typeId = typeId;
		this.name = name;
		this.preDeterminedValue = preDeterminedValue;
		this.required = required;
	}

	public Integer getTypeId() {
		return typeId;
	}

	public String getPreDeterminedValue() {
		return preDeterminedValue;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public boolean isRequired() {
		return required;
	}
}
