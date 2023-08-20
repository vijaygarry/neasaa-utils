package com.neasaa.csv.parser;

public class AbstractShareParser {
//	@Override
//	public  E parse(CSVRecord aCSVRecord, EntityType aEntityType) throws SaixException {
//		return doParse(aCSVRecord, aEntityType);
//	}
//
//	protected abstract E doParse (CSVRecord aCSVRecord, EntityType aEntityType) throws SaixException;
//	
//	public String getCSVColumnValue (CSVRecord aCSVRecord, String aColumnName) {
//		String columnName = aColumnName.toUpperCase();
//		if(aCSVRecord.isSet(columnName)) {
//			return aCSVRecord.get(columnName);
//		}
//		return null;
//	}
//	
//	public String getTransformCSVColumnValue(CSVRecord aCSVRecord, String aColumnName, EntityType aEntityType)
//			throws SaixException {
//		String csvColumnValue = getCSVColumnValue(aCSVRecord, aColumnName);
//		String transformerName = aEntityType.name() + "." + aColumnName;
//		TransformerInfo transformerInfo = TransformerInfoMap.getTransformerInfo(transformerName);
//		if (transformerInfo == null) {
//			return csvColumnValue;
//		} else {
//			try {
//				return TransformerInfoMap.transformData(csvColumnValue, transformerInfo, String.class);
//			} catch (Exception e) {
//				throw new SaixException("Error in data transformation for " + aEntityType.name() + " field "
//						+ aColumnName + " error: " + e.getMessage(), e);
//			}
//		}
//	}
//	
//	protected long getLongValue (CSVRecord aCSVRecord, String aColumnName) throws SaixException {
//		String strValue = aCSVRecord.get(aColumnName);
//		if(StringUtils.isEmpty(strValue)) {
//			throw new SaixException ("Column value is not specified for " + aColumnName);
//		}
//		return StringUtils.stringToLong(strValue);
//	}
//	
//	protected long getLongValue (CSVRecord aCSVRecord, String aColumnName, long aDefaultValue) {
//		try {
//			return getLongValue(aCSVRecord, aColumnName);
//		} catch (SaixException ex) {
//			return aDefaultValue;
//		}
//	}
//	
//	protected String getStringValue (CSVRecord aCSVRecord) {
//		if(aCSVRecord == null) {
//			return null;
//		}
//		StringBuilder sb = new StringBuilder();
//		Iterator<String> iterator = aCSVRecord.iterator();
//		while(iterator.hasNext()) {
//			sb.append(iterator.next());
//		}
//		return sb.toString();
//	}
//	
//	protected String getMd5ForCsvRecord (CSVRecord aCSVRecord) {
//		if(aCSVRecord == null) {
//			return null;
//		}
//		try {
//			return EncryptionUtil.generateMd5(getStringValue(aCSVRecord));
//		} catch (NoSuchAlgorithmException e) {
//			return null;
//		}
//	}
//	
//	//Method specific to one format
//	@Override
//	protected AccountEntity doParse(CSVRecord aNextCSVRecord, EntityType aEntityType) throws SaixException {
//		AccountEntity entity = new AccountEntity();		
//		entity.setMd5(getMd5ForCsvRecord(aNextCSVRecord));
//		entity.setAccountId(getTransformCSVColumnValue(aNextCSVRecord, "ACCOUNTID",aEntityType));
//		entity.setAccountName(getTransformCSVColumnValue(aNextCSVRecord, "ACCOUNTNAME", aEntityType));
//		entity.setStatus(getTransformCSVColumnValue(aNextCSVRecord, "STATUS", aEntityType));
//		entity.setLastUpdatedTimestamp(getTransformCSVColumnValue (aNextCSVRecord, "LASTUPDATETIMESTAMP", aEntityType));
//		entity.setLastLoginTimestamp(getTransformCSVColumnValue(aNextCSVRecord, "LASTLOGINTIMESTAMP", aEntityType));
//		entity.setPassword(getTransformCSVColumnValue(aNextCSVRecord, "PASSWORD", aEntityType));
//		entity.setFailedLoginCount(getTransformCSVColumnValue(aNextCSVRecord, "FAILEDLOGINCOUNT", aEntityType));
//		entity.setChangePwdCount(getTransformCSVColumnValue(aNextCSVRecord, "CHANGEPWDCOUNT", aEntityType));
//		entity.setLastPwdChangeTimestamp(getTransformCSVColumnValue(aNextCSVRecord, "LASTPWDCHANGETIMESTAMP", aEntityType));
//		entity.setType(getTransformCSVColumnValue(aNextCSVRecord, "TYPE", aEntityType));
//		entity.setPwdExpirationDate(getTransformCSVColumnValue(aNextCSVRecord, "PWDEXPIRATIONDATE", aEntityType));
//		entity.setUsage(getTransformCSVColumnValue(aNextCSVRecord, "USAGE", aEntityType));
//		entity.setLinkedToIdentity(getTransformCSVColumnValue(aNextCSVRecord, "LINKEDTOIDENTITY", aEntityType));
//		entity.setActivationDate(getTransformCSVColumnValue(aNextCSVRecord, "ACTIVATIONDATE", aEntityType));
//		entity.setDeactivationDate(getTransformCSVColumnValue(aNextCSVRecord, "DEACTIVATIONDATE", aEntityType));
//		entity.setExpirationState(getTransformCSVColumnValue(aNextCSVRecord, "EXPIRATION_STATE", aEntityType));
//		entity.setPrivilegedCount(getTransformCSVColumnValue(aNextCSVRecord, "PRIVILEGED_COUNT", aEntityType));
//		entity.setDescription(getTransformCSVColumnValue(aNextCSVRecord, "DESCRIPTION", aEntityType));
//		entity.setEmployeeId(getTransformCSVColumnValue(aNextCSVRecord, "EMPLOYEEID", aEntityType));	
//		
//		entity.setApplicationId(getTransformCSVColumnValue(aNextCSVRecord, "APPLICATIONID", aEntityType));
//		
//		entity.setCustomAttribute1(getTransformCSVColumnValue(aNextCSVRecord, "CUSTOM_ATTR1", aEntityType));
//		entity.setCustomAttribute2(getTransformCSVColumnValue(aNextCSVRecord, "CUSTOM_ATTR2", aEntityType));
//		entity.setCustomAttribute3(getTransformCSVColumnValue(aNextCSVRecord, "CUSTOM_ATTR3", aEntityType));
//		entity.setCustomAttribute4(getTransformCSVColumnValue(aNextCSVRecord, "CUSTOM_ATTR4", aEntityType));
//		entity.setCustomAttribute5(getTransformCSVColumnValue(aNextCSVRecord, "CUSTOM_ATTR5", aEntityType));
//		entity.setCustomAttribute6(getTransformCSVColumnValue(aNextCSVRecord, "CUSTOM_ATTR6", aEntityType));
//		entity.setCustomAttribute7(getTransformCSVColumnValue(aNextCSVRecord, "CUSTOM_ATTR7", aEntityType));
//		entity.setCustomAttribute8(getTransformCSVColumnValue(aNextCSVRecord, "CUSTOM_ATTR8", aEntityType));
//		entity.setCustomAttribute9(getTransformCSVColumnValue(aNextCSVRecord, "CUSTOM_ATTR9", aEntityType));
//		entity.setCustomAttribute10(getTransformCSVColumnValue(aNextCSVRecord, "CUSTOM_ATTR10", aEntityType));
//		
//		
//		Date currDate = new Date();
//		entity.setCreatedDate(currDate);
//		entity.setUpdatedDate(currDate);
//		
//		return entity;
//	}

}
