package com.neasaa.codegenerator;

import java.util.ArrayList;
import java.util.List;

import com.neasaa.util.StringUtils;

public class CodeGenerationCliParameter {
	
	
	private List<CodeGeneratorMode> modes;
	private String applicationConfigFilename = null;
	
	public List<CodeGeneratorMode> getModes() {
		return modes;
	}
	
	public String getApplicationConfigFilename() {
		return this.applicationConfigFilename;
	}
	
	public static CodeGenerationCliParameter parseCommandLineParams (String aArgs[]) {
		CodeGenerationCliParameter cliParams = new CodeGenerationCliParameter();
		
		if(aArgs == null || aArgs.length == 0) {
			printUsage();
			System.exit(1);
		}
		
		List<String> modeInputList = StringUtils.parseStringToList(aArgs[0]);
		cliParams.modes = new ArrayList<>();
		for (String modeName: modeInputList) {
			CodeGeneratorMode mode = CodeGeneratorMode.getCodeGeneratorMode(modeName);
			if(mode == null) {
				System.err.println ("Invalid mode " + modeName + " provided. \n");
				printUsage();
			}
			cliParams.modes.add(mode);
		}
		
		if(aArgs.length > 1) {
			cliParams.applicationConfigFilename = aArgs[1];
		}
		return cliParams;
	}
	
	public static void printUsage () {
		System.out.println ( CodeGenerator.class + " <mode> <applicationConfig>");
		System.out.println(" mode: ");
		for (CodeGeneratorMode mode : CodeGeneratorMode.values()) {
			System.out.println(" \t " + StringUtils.rightPad(mode.name(), ' ', 30)  + " - " + mode.getDescription());
		}
		System.out.println("\napplicationConfig - Specify application config path");
	}
}
