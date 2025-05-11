package com.neasaa.codegenerator;

public interface CodeGeneratorConstants {
	String DAO_CLASS_PACKAGE_CONFIG_NAME = "java.generated.file.dao.package";
	String ENTITY_CLASS_PACKAGE_CONFIG_NAME = "java.generated.file.entity.package";
	
	String JAVA_CLASS_USE_LOMBOK_SETTER = "JAVA.CLASS.USE.LOMBOK.FOR.SETTER";
	String JAVA_CLASS_USE_LOMBOK_GETTER = "JAVA.CLASS.USE.LOMBOK.FOR.GETTER";
	String JAVA_DAO_DO_NOT_CREATE_INTERFACE = "JAVA.DAO.DO.NOT.CREATE.INTERFACE";
	
	/**
	 * All DAO impl class will extend this class name. 
	 */
	String ABSTRACT_DAO_CLASS_NAME = "AbstractDao";
	
	String ABSTRACT_ENTITY_CLASS_NAME = "BaseEntity";
}
