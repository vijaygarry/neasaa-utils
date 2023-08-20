package com.neasaa.codegenerator.jdbc;

public class EntityDaoGeneratorHelper {

	public static String  getDaoInterfaceName (String aEntityClassName) {
		return aEntityClassName + "Dao";
	}
	
	public static String  getDaoImplName (String aEntityClassName) {
		return getDaoInterfaceName (aEntityClassName) + "Impl";
	}
	
	public static String getInsertMethodName (String aEntityClassName) {
		return "insert" + aEntityClassName;
	}
	
}
