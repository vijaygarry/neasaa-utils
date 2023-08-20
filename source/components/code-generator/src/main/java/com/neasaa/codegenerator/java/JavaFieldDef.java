package com.neasaa.codegenerator.java;

import com.neasaa.util.StringUtils;

public class JavaFieldDef {
	
	public static final String SERIAL_VERUID_FIELD_NAME = "serialVersionUID";
	
	//Access identifier e.g. public, private etc
	private final String accessIdentifier;
	//Data type e.g. String, Date, int etc.
	private final String dataType;
	//Field name 
	private final String fieldName;
	/**
	 * Default value for this field. <br>
	 * Note: If this is string value, specify double quote in value.
	 */
	private String defaultValue;
	
	private boolean finalField = false;
	private boolean staticField = false;
	
	/**Is this field transient.<br>
	 * The transient keyword is used to exclude variable during serialization.<br>
	 * Transient can not be used with the static keyword
	 */
	private boolean transientField = false;
	
	/**
	 * Is this field volatime.<br>
	 * Volatile keyword is used to flush changes directly to the main memory<br>
	 * Volatile can be used with the final keyword
	 */
	private boolean volatileField = false;
	
	
	public JavaFieldDef(String aAccessIdentifier, String aDataType, String aFieldName, boolean aIsStatic, boolean aIsFinal, String aDefaultValue) {
		super();
		this.accessIdentifier = aAccessIdentifier;
		this.dataType = aDataType;
		this.fieldName = aFieldName;
		this.staticField = aIsStatic;
		this.finalField = aIsFinal;
		this.defaultValue = aDefaultValue;
	}
	
	public JavaFieldDef(String aAccessIdentifier, String aDataType, String aFieldName) {
		super();
		this.accessIdentifier = aAccessIdentifier;
		this.dataType = aDataType;
		this.fieldName = aFieldName;
	}
	
	public JavaFieldDef(String aDataType, String aFieldName) {
		this("private",aDataType, aFieldName);
	}
	/**
	 * Sequence of modifers are as follows:
	 * static <br>
	 * final <br>
	 * transient <br>
	 * volatile <br>
	 * @return
	 */
	public String getFieldDecration () {
		StringBuilder sb = new StringBuilder();
		
		sb.append(this.accessIdentifier);
		
		if(this.staticField) {
			sb.append(" static");
		}
		
		if(this.finalField) {
			sb.append(" final");
		}
		if(this.transientField) {
			if(this.staticField) {
				throw new RuntimeException ("Invaiod field definition provided for field name " + this.fieldName +". transient field can not be static.");
			}
			sb.append(" transient");
		}
		if(this.volatileField) {
			if(this.finalField) {
				throw new RuntimeException ("Invaiod field definition provided for field name " + this.fieldName +". volatile field can not be final.");
			}
			sb.append(" volatile");
		}
		
		sb.append(" ").append(this.dataType).append(" ").append(this.fieldName);
		if(this.defaultValue != null) {
			sb.append(" = ").append(this.defaultValue);
		}
		sb.append(";");	
		return sb.toString();
	}
		
	public static JavaFieldDef getSerialVerUIDFieldDef () {
		String serialVerUID = String.valueOf(System.currentTimeMillis()) + "L";
		return new JavaFieldDef( "public", "long", "serialVersionUID", true, true, serialVerUID);
	}
	
	public String getFieldGetterMethod () {
		//Do not generate getter for serial ver uid filed
		if(SERIAL_VERUID_FIELD_NAME.equalsIgnoreCase(this.fieldName)) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		
		sb.append("\t").append("public ").append(this.dataType).append(" " ).append(getGetterMethodName()).append(" () {").append(StringUtils.NEW_LINE);
		sb.append("\t\treturn this.").append(this.fieldName).append(";").append(StringUtils.NEW_LINE);
		sb.append("\t").append("}").append(StringUtils.NEW_LINE);
		
		return sb.toString();
	}
	
	public String getFieldSetterMethod () {
		//Do not generate setter for serial ver uid filed
		if(SERIAL_VERUID_FIELD_NAME.equalsIgnoreCase(this.fieldName)) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		String capitalizeFieldName = StringUtils.capitalize(this.fieldName);
		
		String setterParamName = "a" + capitalizeFieldName;
		sb.append("\t").append("public void ").append(getSetterMethodName()).append(" (").append(this.dataType).append(" ").append(setterParamName).append(") {").append(StringUtils.NEW_LINE);
		sb.append("\t\tthis.").append(this.fieldName).append(" = ").append(setterParamName).append(";").append(StringUtils.NEW_LINE);
		sb.append("\t").append("}").append(StringUtils.NEW_LINE).append(StringUtils.NEW_LINE);
		return sb.toString();
	}
	
	public String getGetterMethodName () {
		String capitalizeFieldName = StringUtils.capitalize(this.fieldName);
		return "get" + capitalizeFieldName;
	}
	
	public String getSetterMethodName () {
		String capitalizeFieldName = StringUtils.capitalize(this.fieldName);
		return "set" + capitalizeFieldName ;
	}
		
}
