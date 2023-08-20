# code-generator
This is the utility project for generating java code for specific use case.

Project has following mode:
1. DB_TABLE_LIST:
	* Print tables for specified database/schema
1. DB_TABLE_DETAILS:
	* Print details for specific table
1. TABLE_CREATE_ENTITY_CLASS:
	* Generate entity class for specific table
1. TABLE_CREATE_ROWMAPPER_CLASS:
	* Generate (Spring) row mapper class for specific table
1. TABLE_CREATE_DAO_CLASS:
	* Generates (Spring) DAO classes (Interface and implementation)for specific table


## Usage
```
class com.neasaa.codegenerator.CodeGenerator <mode> <applicationConfig>
 mode: 
 	 DB_TABLE_LIST                  - Print list of tables
 	 DB_TABLE_DETAILS               - Print details for specified table name
 	 TABLE_CREATE_ENTITY_CLASS      - Create Entity class for specified table name
 	 TABLE_CREATE_ROWMAPPER_CLASS   - Create Table row mapper class for specified table name
 	 TABLE_CREATE_DAO_CLASS         - Create DAO class for specified table name
 	 UPDATE_CLASS_HEADER            - Update class header

applicationConfig - Specify application config path
```
