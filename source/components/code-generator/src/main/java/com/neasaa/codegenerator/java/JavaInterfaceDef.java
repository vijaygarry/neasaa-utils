package com.neasaa.codegenerator.java;

import com.neasaa.util.StringUtils;

public class JavaInterfaceDef extends JavaClassDef {
	@Override
	protected void appendClassDeclaration (StringBuilder sb) {
		sb.append("public interface ").append(getClassName());	
		
		if(!StringUtils.isEmpty(getParentClass())) {
			sb.append(" extends " ).append(getParentClass());
		}
		
		if(getInterfaces() != null && getInterfaces().size() > 0) {
			throw new RuntimeException ("Interface can not implement interface. Use parent class to extend interface.");
		}
	}
	
	@Override
	public String generateJavaClass () {
		StringBuilder sb = new StringBuilder();
		
		appendHeader(sb);
		appendPackage(sb);
		
		//Add all imports
		appendImports(sb);
		
		//Add class declaration
		appendClassDeclaration(sb);
		
		sb.append(" {").append(StringUtils.NEW_LINE).append(StringUtils.NEW_LINE);
		
		appendMethods(sb);
//		sb.append(getGetterSetterForFields(this.fields));
		sb.append("}").append(StringUtils.NEW_LINE);
		return sb.toString();
	}

}
