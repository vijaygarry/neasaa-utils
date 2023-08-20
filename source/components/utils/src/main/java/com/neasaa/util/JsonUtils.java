package com.neasaa.util;

import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;


/**
 * 
 * @author Vijay G
 * @version 1.0 Nov 14, 2018
 */
public class JsonUtils {
	/**
	 * Converts object to JSON representative String
	 * 
	 * @return
	 * @throws Exception 
	 */
	public static String convertObjectToJSonString ( Object aObject ) throws Exception {

		final ObjectMapper mapper = new ObjectMapper();
		final ObjectWriter writer = mapper.writer();

		try {
			return writer.with( SerializationFeature.INDENT_OUTPUT ).writeValueAsString( aObject );
		}
		catch ( JsonProcessingException ex ) {
			throw new Exception( "Error while converting objects to JSON representation", ex );
		}
	}
	
	/**
	 * Converts object to JSON representative String
	 * 
	 * @return
	 * @throws Exception 
	 */
	public static String convertObjectToJSonString ( Object aObject, boolean aIndentOutput ) throws Exception {

		final ObjectMapper mapper = new ObjectMapper();
		final ObjectWriter writer = mapper.writer();

		try {
			if(aIndentOutput) {
				writer.with( SerializationFeature.INDENT_OUTPUT );
			}
			return writer.writeValueAsString( aObject );
		}
		catch ( JsonProcessingException ex ) {
			throw new Exception( "Error while converting objects to JSON representation", ex );
		}
	}


	/**
	 * Converts JSON String to specified class instance
	 * 
	 * @return Return java object for JSON String
	 * @throws Exception 
	 */
	public static <R> R convertJsonStringToObject ( String aJsonString, Class<R> aClass ) throws Exception {

		final ObjectMapper mapper = new ObjectMapper();

		try {
			return mapper.readValue( aJsonString, aClass );
		}
		catch ( IOException ex ) {
			throw new Exception ( "Error converting json string to object. \n JSON String <" + aJsonString
					+ ">,\n trying to convert to class  <" + aClass + ">", ex );
		}
	}


	/**
	 * Converts JSON String to specified class instance
	 * 
	 * @return Return java object for JSON String
	 * @throws Exception 
	 */
	public static <R> R convertJsonStringToObject ( String aJsonString, TypeReference<R> aTypeReference ) throws Exception {

		final ObjectMapper mapper = new ObjectMapper();		
		return mapper.readValue( aJsonString, aTypeReference );
		
	}
	
	public static Map<String, Object> convertJsonStringToMap (String aJsonString) throws Exception {
		return convertJsonStringToObject(aJsonString, new TypeReference<Map<String, Object>>(){});		
	}
	
	public static String convertMapToJsonString (Map<String, Object> aMap) throws Exception {
		return convertObjectToJSonString(aMap);		
	}
}
