package com.neasaa.codegenerator;

public interface CodeGeneratorConstants {
	String DAO_CLASS_PACKAGE_CONFIG_NAME = "java.generated.file.dao.package";
	String ENTITY_CLASS_PACKAGE_CONFIG_NAME = "java.generated.file.entity.package";
	
	/**
	 * All DAO impl class will extend this class name. 
	 */
	String ABSTRACT_DAO_CLASS_NAME = "AbstractDao";
	
	String ABSTRACT_ENTITY_CLASS_NAME = "BaseEntity";
}
