package com.neasaa.codegenerator.java;

import java.util.ArrayList;
import java.util.List;

public class JavaMethodDef {
	
	public static String PRIVATE_ACCESS_IDENTIFIER = "private";
	public static String PUBLIC_ACCESS_IDENTIFIER = "public";
	public static String PROTECTED_ACCESS_IDENTIFIER = "protected";
	public static String DEFAULT_ACCESS_IDENTIFIER = "";
	
	
	private String accessIdentifier = "public";
	private String returnType = "void";
	private List<String> annotations = new ArrayList<>();
	private String methodName;
	private String methodParams;
	private String methodImplementation = "";
	private List<String> exceptions = new ArrayList<>();
	private boolean abstractMethod = false;
	
	
	private static final String NEW_LINE = "\n";
	
	public JavaMethodDef(String aMethodName, String aMethodParams) {
		super();
		this.methodName = aMethodName;
		this.methodParams = aMethodParams;
	}
	
	public String generateMethodCode() {
		StringBuilder sb = new StringBuilder();
		if(this.annotations != null && this.annotations.size() > 0) {
			for(String annotation : annotations) {
				sb.append("\t").append(annotation).append(NEW_LINE);
			}
		}
		sb.append("\t").append(this.accessIdentifier).append(" ").append(this.returnType);
		sb.append(" ").append(this.methodName).append("(").append(this.methodParams).append( ")");
		
		if(this.exceptions != null && this.exceptions.size() > 0) {
			boolean first = true;
			for(String exception : exceptions) {
				if(first) {
					sb.append(" throws ");
					first = false;
				} else {
					sb.append(", ");	
				}
				sb.append(exception);
			}
		}
		if(this.abstractMethod) {
			sb.append(";");
		} else {
			sb.append(" {").append(NEW_LINE);
			sb.append(this.methodImplementation).append(NEW_LINE);
			sb.append("\t}");
		}
		return sb.toString();
	}

	public void addMethodImplementation(String aMethodImplementation) {
		methodImplementation = methodImplementation + aMethodImplementation;
	}
	
	public void addMethodException (String aMethodException) {
		this.exceptions.add(aMethodException);
	}
	
	public void addAnnotation (String aAnnotation) {
		this.annotations.add(aAnnotation);
	}
	
	public void setAccessIdentifier(String aAccessIdentifier) {
		this.accessIdentifier = aAccessIdentifier;
	}

	public void setReturnType(String aReturnType) {
		this.returnType = aReturnType;
	}

	public void setMethodParams(String aMethodParams) {
		this.methodParams = aMethodParams;
	}
	
	public void setAbstractMethod(boolean aAbstractMethod) {
		this.abstractMethod = aAbstractMethod;
	}
	
}
