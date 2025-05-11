package com.neasaa.codegenerator.jdbc;

public class EntityDaoGeneratorHelper {

	public static String  getDaoInterfaceName (String aEntityClassName) {
		return aEntityClassName + "Dao";
	}
	
//	public static String  getDaoImplName (String aEntityClassName) {
//		boolean doNotGenerateDaoInterface = BaseConfig.getBooleanProperty(CodeGeneratorConstants.JAVA_CLASS_USE_LOMBOK_GETTER, false);
//		if(doNotGenerateDaoInterface) {
//			return getDaoInterfaceName (aEntityClassName);
//		} else {
//			return getDaoInterfaceName (aEntityClassName) + "Impl";
//		}
//	}
	
	public static String getInsertMethodName (String aEntityClassName) {
		return "insert" + aEntityClassName;
	}
	
}
