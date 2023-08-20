package com.neasaa.codegenerator;

public enum CodeGeneratorMode {
	DB_TABLE_LIST ("Print list of tables"), 
	DB_TABLE_DETAILS ("Print details for specified table name"), 
	TABLE_CREATE_ENTITY_CLASS ("Create Entity class for specified table name"),
	TABLE_CREATE_ROWMAPPER_CLASS ("Create Table row mapper class for specified table name"),
	TABLE_CREATE_DAO_CLASS ("Create DAO class for specified table name"),
	UPDATE_CLASS_HEADER ("Update class header");
	
	private String description;
	private CodeGeneratorMode (String aDescription) {
		this.description = aDescription;
	}
	
	public String getDescription() {
		return description;
	}
	
	public static CodeGeneratorMode getCodeGeneratorMode (String aModeName) {
		for (CodeGeneratorMode mode : CodeGeneratorMode.values()) {
			if(mode.name().equalsIgnoreCase(aModeName)) {
				return mode;
			}
		}
		return null;
	}
}
