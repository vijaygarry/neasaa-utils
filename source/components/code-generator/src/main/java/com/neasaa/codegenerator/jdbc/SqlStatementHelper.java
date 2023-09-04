package com.neasaa.codegenerator.jdbc;

import java.math.BigDecimal;
import java.sql.JDBCType;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SqlStatementHelper {

	private static final Logger LOG = LoggerFactory.getLogger(SqlStatementHelper.class);
	private static final String NULL_VALUE = "NULL";
	
	public static String generateSetterStatement (JDBCType aJdbcType, String aColIndex, String aColValueString) throws Exception {
		StringBuilder sb = new StringBuilder();
		switch (aJdbcType) {
		case CHAR:
		case VARCHAR:
			sb.append( "setStringInStatement");
			break;
		case SMALLINT:
			sb.append( "setSmallIntInStatement");
			break;
		case BIGINT:
			sb.append( "setLongInStatement");
			break;
		case INTEGER:
			sb.append( "setIntInStatement");
			break;
		case FLOAT:
		case REAL:
			sb.append( "setFloatInStatement");
			break;
		case DOUBLE:
			sb.append( "setDoubleInStatement");
			break;
		case NUMERIC:
		case DECIMAL:
			sb.append( "setBigDecimalInStatement");
			break;
		case TIMESTAMP:
			sb.append( "setTimestampInStatement");
			break;
		case DATE:
			sb.append( "setDateInStatement");
			break;
		case BOOLEAN:
			sb.append( "setBooleanInStatement");
			break;
		case BIT:
			sb.append( "setBooleanInStatement");
			break;
		case TINYINT:
		case LONGVARCHAR:
		case TIME:
		case TIME_WITH_TIMEZONE:
		case TIMESTAMP_WITH_TIMEZONE:
		case BINARY:
		case VARBINARY:
		case LONGVARBINARY:
		case NULL:
		case OTHER:
		case JAVA_OBJECT:
		case DISTINCT:
		case STRUCT:
		case ARRAY:
		case BLOB:
		case CLOB:
		case REF:
		case DATALINK:
		case ROWID:
		case NCHAR:
		case NVARCHAR:
		case LONGNVARCHAR:
		case NCLOB:
		case SQLXML:
		case REF_CURSOR:

		default:
			throw new Exception( "Data type " + aJdbcType
					+ " not supported by utility." + " ColIndex:" + aColIndex + ", colValue:" + aColValueString);
		}
		sb.append("(prepareStatement, ").append(aColIndex).append(", ").append(aColValueString).append(");");
		return sb.toString();
	}
	
	public static String getResultSetMethod (String aResultSetVarName, JDBCType aJdbcType, String aColName) throws Exception {
		StringBuilder sb = new StringBuilder();
		switch (aJdbcType) {
		case CHAR:
		case VARCHAR:
			return ( aResultSetVarName + ".getString(\"" + aColName + "\")");
		case SMALLINT:
			return ( aResultSetVarName + ".getShort(\"" + aColName + "\")");
		case BIGINT:
			return ( aResultSetVarName + ".getLong(\"" + aColName + "\")");			
		case INTEGER:
			return ( aResultSetVarName + ".getInt(\"" + aColName + "\")");
		case FLOAT:
		case REAL:
			sb.append( "setFloatInStatement ");
			break;
		case DOUBLE:
			sb.append( "setDoubleInStatement ");
			break;
		case NUMERIC:
		case DECIMAL:
			return ( aResultSetVarName + ".getBigDecimal(\"" + aColName + "\")");
		case TIMESTAMP:
			return ( "AbstractDao.getTimestampFromResultSet(" + aResultSetVarName + ", \"" + aColName + "\")");
		case DATE:
			return ( "AbstractDao.getDateFromResultSet(" + aResultSetVarName + ", \"" + aColName + "\")");
		case BOOLEAN:
			return ( aResultSetVarName + ".getBoolean(\"" + aColName + "\")");
		case BIT:
			return ( aResultSetVarName + ".getBoolean(\"" + aColName + "\")");
		case TINYINT:
		case LONGVARCHAR:
		case TIME:
		case TIME_WITH_TIMEZONE:
		case TIMESTAMP_WITH_TIMEZONE:
		case BINARY:
		case VARBINARY:
		case LONGVARBINARY:
		case NULL:
		case OTHER:
		case JAVA_OBJECT:
		case DISTINCT:
		case STRUCT:
		case ARRAY:
		case BLOB:
		case CLOB:
		case REF:
		case DATALINK:
		case ROWID:
		case NCHAR:
		case NVARCHAR:
		case LONGNVARCHAR:
		case NCLOB:
		case SQLXML:
		case REF_CURSOR:

		default:
			throw new Exception( "Data type " + aJdbcType
					+ " not supported by utility." + " ColName:" + aColName);
		}
//		sb.append("( prepareStatement, ").append(aColIndex).append(", ").append(aColValueString).append(" );");
		return sb.toString();
	}
	/**
	 * <pre>
	 * Table 16. PostgreSQL data types		
	PostgreSQL data type	->	JDBC data type
	----------------------------------------------
	BIGINT			->	BIGINT
	BIGSERIAL		->	BIGINT
	BIT			->	BIT or BINARY
	BIT VARYING		->	BINARY
	BOOLEAN			->	BOOLEAN
	BYTEA			->	LONGVARBINARY
	CHARACTER		->	CHAR
	CHARACTER VARYING	->	VARCHAR
	DATE			->	DATE
	DOUBLE PRECISION	->	DOUBLE
	INTEGER			->	INTEGER
	NUMERIC			->	NUMERIC
	REAL			->	REAL
	SERIAL			->	INTEGER
	SMALLINT		->	SMALLINT
	TEXT			->	LONGVARCHAR
	TIME			->	TIMESTAMP
	TIME WITH TIMEZONE	->	TIMESTAMP
	TIMESTAMP		->	TIMESTAMP
	TIMESTAMP WITH TIMEZONE ->	TIMESTAMP
	XML			->	SQLXML
	 * </pre>
	 * 
	 * @param aPreparedStatement
	 * @param aColumnDefinition
	 * @param aColValue
	 * @param aColIndex
	 * @throws Exception
	 */
	public static void setColumnValue ( PreparedStatement aPreparedStatement, ColumnDefinition aColumnDefinition,
			String aColValue, int aColIndex ) throws Exception {

		JDBCType jdbcType = aColumnDefinition.getDataType();
		if ( LOG.isDebugEnabled() ) {
			LOG.debug( "Setting value <" + aColValue + "> in SQL statement for " + aColumnDefinition.getColumnName()
					+ " at index " + aColIndex );
		}

		// If value is null, then set the null value and return.
		if ( isNullValue( aColValue ) ) {
			aPreparedStatement.setNull( aColIndex, jdbcType.getVendorTypeNumber().intValue() );
			return;
		}

		switch (jdbcType) {
		case CHAR:
		case VARCHAR:
			setStringInStatement( aPreparedStatement, aColIndex, aColValue );
			break;
		case SMALLINT:
			setSmallIntInStatement( aPreparedStatement, aColIndex, aColValue );
			break;
		case BIGINT:
			setLongInStatement( aPreparedStatement, aColIndex, aColValue );
			break;
		case INTEGER:
			setIntInStatement( aPreparedStatement, aColIndex, aColValue );
			break;
		case FLOAT:
		case REAL:
			setFloatInStatement( aPreparedStatement, aColIndex, aColValue );
			break;
		case DOUBLE:
			setDoubleInStatement( aPreparedStatement, aColIndex, aColValue );
			break;
		case NUMERIC:
		case DECIMAL:
			setBigDecimalInStatement( aPreparedStatement, aColIndex, aColValue );
			break;
		case TIMESTAMP:
//			setTimestampInStatement( aPreparedStatement, aColIndex, aColValue, aColumnDefinition );
			break;
		case BOOLEAN:
			setBooleanInStatement( aPreparedStatement, aColIndex, aColValue );
			break;
		case DATE:
		case BIT:
		case TINYINT:
		case LONGVARCHAR:
		case TIME:
		case TIME_WITH_TIMEZONE:
		case TIMESTAMP_WITH_TIMEZONE:
		case BINARY:
		case VARBINARY:
		case LONGVARBINARY:
		case NULL:
		case OTHER:
		case JAVA_OBJECT:
		case DISTINCT:
		case STRUCT:
		case ARRAY:
		case BLOB:
		case CLOB:
		case REF:
		case DATALINK:
		case ROWID:
		case NCHAR:
		case NVARCHAR:
		case LONGNVARCHAR:
		case NCLOB:
		case SQLXML:
		case REF_CURSOR:

		default:
			throw new Exception( "Data type " + aColumnDefinition.getDataType()
					+ " not supported by engine. Coulmn name:" + aColumnDefinition.getColumnName());
		}

	}
	
	/**
	 * Returns true if value is null or empty string or value is "NULL".
	 * 
	 * @param aColumnValue
	 * @return
	 */
	private static boolean isNullValue ( String aColumnValue ) {
		if ( aColumnValue == null || aColumnValue.length() == 0 || aColumnValue.equalsIgnoreCase( NULL_VALUE ) ) {
			return true;
		}
		return false;
	}
	
	
	/**
	 * This method check the specified string value. If specified string is null, then set null value for specified index
	 * else sets specified string at specified index.
	 * 
	 * @param aPreparedStatement
	 * @param aIndex
	 * @param aStringValue
	 * @throws SQLException
	 */
	public final static void setStringInStatement ( PreparedStatement aPreparedStatement, int aIndex,
			String aStringValue ) throws SQLException {
		aPreparedStatement.setString( aIndex, aStringValue );
	}
	
	public final static void setLongInStatement ( PreparedStatement aPreparedStatement, int aIndex, String aStringValue )
			throws Exception {
		long longValue = -1l;
		try {
			longValue = Long.parseLong( aStringValue );
		}
		catch ( NumberFormatException e ) {
			throw new Exception (
					"Failed to parse long value <" + aStringValue + "> at index " + aIndex + ". Cause " + e.getMessage());
		}
		aPreparedStatement.setLong( aIndex, longValue );
	}
	
	
	public final static void setIntInStatement ( PreparedStatement aPreparedStatement, int aIndex, String aStringValue )
			throws Exception {
		int intValue = -1;
		try {
			intValue = Integer.parseInt( aStringValue );
		}
		catch ( NumberFormatException e ) {
			throw new Exception(
					"Failed to parse integer value <" + aStringValue + "> at index " + aIndex + ". Cause " + e.getMessage());
		}
		aPreparedStatement.setInt( aIndex, intValue );
	}


	public final static void setSmallIntInStatement ( PreparedStatement aPreparedStatement, int aIndex,
			String aStringValue ) throws Exception {
		short smallIntValue = -1;
		try {
			smallIntValue = Short.parseShort( aStringValue );
		}
		catch ( NumberFormatException e ) {
			throw new Exception ( "Failed to parse short (smallint) value <" + aStringValue + "> at index "
					+ aIndex + ". Cause " + e.getMessage() );
		}
		aPreparedStatement.setShort( aIndex, smallIntValue );
	}
	
	/**
	 * This method will be used to set float as well as real SQL data type.
	 * 
	 * @param aPreparedStatement
	 * @param aIndex
	 * @param aStringValue
	 * @throws SQLException
	 * @throws Exception
	 */
	public final static void setFloatInStatement ( PreparedStatement aPreparedStatement, int aIndex, String aStringValue )
			throws SQLException, Exception {
		float floatValue = -1;
		try {
			floatValue = Float.parseFloat( aStringValue );
		}
		catch ( NumberFormatException e ) {
			throw new Exception(
					"Failed to parse float value <" + aStringValue + "> at index " + aIndex + ". Cause " + e.getMessage());
		}
		aPreparedStatement.setFloat( aIndex, floatValue );
	}


	public final static void setDoubleInStatement ( PreparedStatement aPreparedStatement, int aIndex,
			String aStringValue ) throws SQLException, Exception {
		double doubleValue = -1;
		try {
			doubleValue = Double.parseDouble( aStringValue );
		}
		catch ( NumberFormatException e ) {
			throw new Exception(
					"Failed to parse double value <" + aStringValue + "> at index " + aIndex + ". Cause " + e.getMessage());
		}
		aPreparedStatement.setDouble( aIndex, doubleValue );
	}
	
	/**
	 * This method should be used for setting DECIMAL and NUMERIC in prepared statement. <br>
	 * Some notes on DECIMAL and NUMERIC: <br>
	 * The sole distinction between DECIMAL and NUMERIC is that the SQL-92 specification requires that NUMERIC types be
	 * represented with exactly the specified precision, whereas for DECIMAL types, it allows an implementation to add
	 * additional precision beyond that specified when the type was created. Thus a column created with type NUMERIC(12,4)
	 * will always be represented with exactly 12 digits, whereas a column created with type DECIMAL(12,4) might be
	 * represented by some larger number of digits. <br>
	 * The corresponding SQL types DECIMAL and NUMERIC are defined in SQL-92 and are very widely implemented. These SQL
	 * types takes precision and scale parameters. The precision is the total number of decimal digits supported, and the
	 * scale is the number of decimal digits after the decimal point. The scale must always be less than or equal to the
	 * precision. So for example, the value "12.345" has a precision of 5 and a scale of 3, and the value ".11" has a
	 * precision of 2 and a scale of 2. JDBC requires that all DECIMAL and NUMERIC types support both a precision and a
	 * scale of at least 15.
	 * 
	 * @param aPreparedStatement
	 * @param aIndex
	 * @param aStringValue
	 * @throws SQLException
	 * @throws Exception
	 */
	public final static void setBigDecimalInStatement ( PreparedStatement aPreparedStatement, int aIndex,
			String aStringValue ) throws SQLException, Exception {
		BigDecimal bigDecimalValue = null;
		try {
			bigDecimalValue = new BigDecimal( aStringValue );
		}
		catch ( NumberFormatException e ) {
			throw new Exception(
					"Failed to parse bigdecimal value <" + aStringValue + "> at index " + aIndex + ". Cause " + e.getMessage());
		}
		aPreparedStatement.setBigDecimal( aIndex, bigDecimalValue );
	}


	public final static void setBooleanInStatement ( PreparedStatement aPreparedStatement, int aIndex,
			String aStringValue ) throws SQLException, Exception {
		boolean booleanValue = false;
		try {
			booleanValue = Boolean.parseBoolean( aStringValue );
		}
		catch ( NumberFormatException e ) {
			throw new Exception(
					"Failed to parse boolean value <" + aStringValue + "> at index " + aIndex + ". Cause " + e.getMessage() );
		}
		aPreparedStatement.setBoolean( aIndex, booleanValue );
	}
	
//	public final static void setTimestampInStatement ( PreparedStatement aPreparedStatement, int aIndex, String aColValue,
//			ColumnDefinition aColumnDefinition ) throws SQLException, Exception {
//		String strValue = aColValue;
//
//		String converterName = aColumnDefinition.getConverter();
//		DataConverter<Date> dateConverter = null;
//		if ( converterName != null ) {
//			dateConverter = DataConverterRegistry.getConverterByName( converterName, Date.class );
//			if ( dateConverter == null ) {
//				throw new Exception( "Date converter <" + converterName + "> not found define for column "
//						+ aColumnDefinition.getSourceColumnName() );
//			}
//		}
//		else {
//			// Default converter for Date
//			dateConverter = DataConverterRegistry.getConverterByName( DEFAULT_DATE_CONVERTER_NAME, Date.class );
//			if ( dateConverter == null ) {
//				throw new Exception( "Default date converter <" + DEFAULT_DATE_CONVERTER_NAME
//						+ "> not found required for column " + aColumnDefinition.getSourceColumnName());
//			}
//		}
//
//		Date dateValue = dateConverter.convert( strValue );
//
//		aPreparedStatement.setTimestamp( aIndex, DateUtils.dateToSqlTimestamp( dateValue ),
//				DateUtils.getUtcCalendarInstance() );
//	}
}
