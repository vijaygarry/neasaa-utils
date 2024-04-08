package com.neasaa.util;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;

public class FileUtils {
	public static final Pattern VALID_FILE_NAME_PATERN = Pattern.compile("^[^/\\?><,{}\\[\\]`~!@#$%^&*()+=|:;'\\\".\\s]+$");
	public static final String SORT_BY_NAME = "NAME";
	public static final String SORT_BY_LAST_MODIFY = "LAST_MODIFY";
	// -------------------------------------------------------------------------
	/**
	 * This method creates a file to specified path. If data is empty, creates a
	 * empty file.
	 * 
	 * @param aFilepath
	 * @param aData
	 * @param aOverWrite
	 * @throws IOException
	 * @throws Exception 
	 */
	public static final void createFile ( String aFilepath, String aData,
			boolean aOverWrite ) throws Exception {

		File file = new File( aFilepath );
		if ( file.isDirectory() ) {
			throw new Exception (
					"Path specified is directory. Please specify valid filePath." );
		}

		// If parent directory doesn't exists, then create directory
		if ( file.getParentFile() != null && !file.getParentFile().exists() ) {
			file.getParentFile().mkdirs();
		}

		if ( aData == null ) {
			// If data is null, create empty file.
			createEmptyFile( aFilepath, aOverWrite );
		}
		else {
			// If data is not null, then create file and add data to file
			// overwrite is true means delete existing data and write new data to file
			// i.e. Append is false
			// If overwrite is false, means if file exists append the data at the end
			// of file.
			boolean appendData = !aOverWrite;
			writeStringToFile( aFilepath, aData, appendData );
		}
	}

	// -------------------------------------------------------------------------
	/**
	 * Return true if file exists in classpath else return false.
	 * 
	 * @param aFilename
	 * @return
	 */
	public static boolean isFileExistsInClassPath(String aFilename) {
		InputStream inputStream = null;
		try {
			// load the properties file from the class path
			inputStream = FileUtils.class.getClassLoader().getResourceAsStream("./" + aFilename);
			if (inputStream == null) {
				return false;
			} else {
				return true;
			}
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException ex) {
				}
			}
		}
	}
	
	//-------------------------------------------------------------------------
	/**
	 * This method creates a folder for specified path.
	 * 
	 * @param aFolderPath
	 *            - Folder path to create
	 * @param aIgnoreIfExists
	 *            - Doesnot throw exception if folder laready exists
	 * @throws Exception
	 */
	public static final void createDirectory(String aFolderPath, boolean aIgnoreIfExists) throws Exception {

		File file = new File(aFolderPath);
		boolean exists = file.exists();

		if (exists && !aIgnoreIfExists) {
			throw new Exception ("Directory <" + aFolderPath + "> already exists.");
		}

		if (!exists) {
			boolean success = file.mkdirs();
			if (!success) {
				throw new Exception ("Error creating directory <" + aFolderPath + ">");
			}
		}

	}

	// -------------------------------------------------------------------------
	/**
	 * Creates a empty file. If file already exists and overwrite flag is true,
	 * then delete the existing file and create an empty file.
	 * 
	 * @param aFilepath
	 *        - File path to create
	 * @param aOverWrite
	 *        - Overwrite the existing file if exists.
	 * @throws IOException
	 *         Throws exception if not able to create empty file.
	 * @throws Exception
	 */
	public static void createEmptyFile ( String aFilepath, boolean aOverWrite )
			throws Exception {

		File file = new File( aFilepath );
		if ( file.isDirectory() ) {
			throw new Exception(
					"Path specified is directory. Please specify valid filePath." );
		}

		// If parent directory doesn't exists, then create directory
		if ( file.getParentFile() != null && !file.getParentFile().exists() ) {
			file.getParentFile().mkdirs();
		}

		boolean fileCreated;
		try {
			fileCreated = file.createNewFile();
		}
		catch ( IOException e ) {
			throw new Exception( "Error creating file. Error:" + e.getMessage() );
		}

		// File is not created, that means file already exists.
		// If overwrite is true, then delete and recreate the file.
		if ( !fileCreated && aOverWrite ) {
			FileUtils.deleteFile( aFilepath );
			createEmptyFile( aFilepath, false );
		}
	}


	// -------------------------------------------------------------------------
	/**
	 * Append String to a file.
	 * 
	 * @param aFilepath
	 * @param aData
	 * @throws IOException
	 */
	public static void appendStringToFile ( String aFilepath, String aData,
			boolean aOverWrite ) throws IOException {

		// TODO: Check if file exists
		// If file does not exists, throw error.

		// Call writeStringToFile with append = true
	}


	// -------------------------------------------------------------------------
	/**
	 * @param aFilepath
	 *        - File path to read
	 * @param aIgnoreIfFileDoesnotExists
	 *        - If true, return null if file doesn't exists. If false throw
	 *        exception if file doesn't exists.
	 * @param aStartLineNumber
	 *        - Line number from where to start reading. Index start with 1. 1 or
	 *        less then 1 to read the file from start.
	 * @param aEndLineNumber
	 *        - Line number till that to read the file. -1 indicate the read till
	 *        the end. If end line number is less than start line number, then
	 *        returns null. If file has lesser lines as EndLineNumber, then reads
	 *        till the end.
	 * @return
	 */
	public static final String read ( String aFilepath,
			boolean aIgnoreIfFileDoesnotExists, int aStartLineNumber,
			int aEndLineNumber ) {
		StringBuilder sb = new StringBuilder();
		// if file doesn't exists
		// aIgnoreIfFileDoesnotExists is false throw exception
		// else return null;

		return sb.toString();
	}


//	// -------------------------------------------------------------------------
//	public static final boolean fileExists ( String aFilepath ) {
//		return FFileUtils.isFileExists( aFilepath );
//	}


	// -------------------------------------------------------------------------
	public static final int size ( String aFilepath,
			boolean aIgnoreIfFileDoesnotExists, String aUnit ) {
		// if file doesn't exists
		// aIgnoreIfFileDoesnotExists is false throw exception
		// else return 0;

		// Returns the file size in bytes.

		return 0;
	}


	// -------------------------------------------------------------------------
	public static final int numberOfLines ( String aFilepath,
			boolean aIgnoreIfFileDoesnotExists ) {
		// if file doesn't exists
		// aIgnoreIfFileDoesnotExists is false throw exception
		// else return 0;

		// Returns the number of lines in a file.

		return 0;
	}


	// -------------------------------------------------------------------------
	public static final boolean copy ( String aSourceFilepath,
			String aDestinationFilepath, boolean aOverwriteFile )
			throws Exception {

		// If source file does not exists, throw exception
		if ( !isFileExists( aSourceFilepath ) ) {
			throw new Exception( "Source file <" + aSourceFilepath
					+ "> doesnot exits." );
		}

		// Check if destination file exists
		boolean destinationFileExists = isFileExists( aDestinationFilepath );

		if ( destinationFileExists && !aOverwriteFile ) {
			// If destination file exists and overwrite is false throw exception
			throw new Exception( "Destination file <" + aDestinationFilepath
					+ "> already exists." );
		}

		File inFile = null;
		File outFile = null;

		FileChannel inChannel = null;
		FileChannel outChannel = null;

		try {

			inFile = new File( aSourceFilepath );
			outFile = new File( aDestinationFilepath );

			if ( !outFile.exists() ) {
				outFile.createNewFile();
			}

			inChannel = new FileInputStream( inFile ).getChannel();
			outChannel = new FileOutputStream( outFile ).getChannel();

			inChannel.transferTo( 0, inChannel.size(), outChannel );
		}
		catch ( IOException e ) {
			throw new Exception( e.getMessage() );
		}
		finally {
			if ( inChannel != null )
				try {
					inChannel.close();
				}
				catch ( IOException e ) {
					e.printStackTrace();
				}
			if ( outChannel != null )
				try {
					outChannel.close();
				}
				catch ( IOException e ) {
					e.printStackTrace();
				}
		}
		return true;
	}


	// -------------------------------------------------------------------------
	/**
	 * Move file from one location to another location.
	 * 
	 * @param aSourceFilepath
	 * @param aDestinationFilepath
	 * @return true if success otherwise false
	 * @throws Exception
	 * @throws IOException
	 */
	public static boolean moveFile ( String aSourceFilepath,
			String aDestinationFilepath, boolean aOverwriteFile )
			throws Exception {

		// If source file does not exists, throw exception
		if ( !isFileExists( aSourceFilepath ) ) {
			throw new Exception( "Source file <" + aSourceFilepath
					+ "> doesnot exits." );
		}

		// Check if destination file exists
		boolean destinationFileExists = isFileExists( aDestinationFilepath );

		if ( destinationFileExists && !aOverwriteFile ) {
			// If destination file exists and overwrite is false throw exception
			throw new Exception( "Destination file <" + aDestinationFilepath
					+ "> already exists." );
		}

		// File (or directory) to be moved
		File sourceFile = new File( aSourceFilepath );

		// Destination directory
		File destinationFile = new File( aDestinationFilepath );

		// Move file to new directory
		return sourceFile.renameTo( destinationFile );
	}


	// -------------------------------------------------------------------------
	/**
	 * Deletes the specified file. Returns true if file deleted successfully
	 * otherwise false. Returns false if file missing on local storage. Only file
	 * should be specified to delete. Throws exception if directory is specified
	 * instead of file.
	 * 
	 * @param aFileFullPath
	 * @return true if success otherwise false
	 */
	public static boolean deleteFile ( String aFileFullPath )
			throws Exception {

		if ( !isFileExists( aFileFullPath ) ) {
			throw new Exception( "File doesn't exists to delete." );
		}

		File tmpFile = new File( aFileFullPath );
		if ( tmpFile.isDirectory() ) {
			throw new Exception( "File specified is directory." );
		}
		return tmpFile.delete();
	}


	// -------------------------------------------------------------------------
	public static boolean deleteDirectory ( File aDirectory, boolean aRecursively )
			throws Exception {
		if ( !aDirectory.isDirectory() ) {
			throw new Exception( "Specified path <" + aDirectory
					+ "> is not directory" );
		}

		if ( !aRecursively ) {
			return aDirectory.delete();
		}
		List<File> directoriesToDelete = new ArrayList<File>();
		for ( File file : aDirectory.listFiles() ) {
			if ( file.isFile() ) {
				file.delete();
				continue;
			}
			else {
				directoriesToDelete.add( file );
			}
		}

		for ( File file : directoriesToDelete ) {
			deleteDirectory( file, aRecursively );
			// TODO:
			// If deleteDirectoryReturn false, return false.
		}

		return aDirectory.delete();
	}


	/**
	 * Reads the file content and returns as String to the caller
	 * 
	 * @param aFilePath
	 * @return the content of the file as String
	 * @throws Exception 
	 */
	public static String readFileAsString ( String aFilePath )
			throws Exception {
		
		BufferedInputStream f = null;
		File testFile = new File(aFilePath);
		if(testFile.exists()) {
			f = new BufferedInputStream( new FileInputStream( testFile ) );	
		} else {
			//See if file exists in classpath
			try {
				f = new BufferedInputStream( ClassPathUtils.getInputStream(aFilePath));
			} catch (IOException ioEx) {
				throw new Exception("File <" + aFilePath + "> does not exists to read", ioEx);
			}
		}
		
		
//		byte[] buffer = new byte[(int) testFile.length()];
		
		try {
			return IOUtils.toString(f, Charset.forName("UTF-8"));
		} catch (IOException ex) {
			throw new Exception("Failed to read file <" + aFilePath + ">. Error:" + ex.getMessage());
		}
		finally {
			if ( f != null )
				try {
					f.close();
				}
				catch ( IOException e ) {
					e.printStackTrace();
				}
		}
//		try {
//			return new String( buffer, "UTF-8" );
//		}
//		catch ( UnsupportedEncodingException e ) {
//			throw new Exception( "Failed to read file <" + aFilePath
//					+ ">. Error:" + e.getMessage() );
//		}
	}
	
	
	// -------------------------------------------------------------------------
	/**
	 * Writes String to a file.
	 * 
	 * @param aFilepath
	 * @param aData
	 * @param aAppendData
	 * @throws IOException
	 */
	public static void writeStringToFile(String aFilepath, String aData, boolean aAppendData) throws Exception {

		FileWriter fileWriter = null;
		try {

			fileWriter = new FileWriter(aFilepath, aAppendData);
			BufferedWriter bw = new BufferedWriter(fileWriter);
			bw.write(aData);
			bw.flush();
			bw.close();
		} catch (IOException e) {
			throw new Exception("Error writing to file <" + aFilepath + ">. Error:" + e.getMessage());
		} finally {
			if (fileWriter != null) {
				try {
					fileWriter.close();
				} catch (IOException ex) {
				}
			}
		}
	}
	
	// -------------------------------------------------------------------------
	/**
	 * Writes a byte array to a file.
	 * 
	 * @param aFileName
	 * @param aData
	 * @throws IOException
	 * @throws Exception 
	 * @throws RuntimeException
	 */
	public static void writeBytesToFile ( String aFileName, byte[] aData ,  boolean aAppendData)
			throws Exception {
		File file = new File( aFileName );
		if ( !file.exists() ) {
			createEmptyFile( aFileName, true );
		}
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream( aFileName,  aAppendData);
			fos.write( aData );
		}
		catch ( FileNotFoundException ex ) {
			throw new RuntimeException( "File [" + aFileName
					+ "] not found to write data.", ex );
		}
		catch ( IOException ioe ) {
			throw new RuntimeException( "IO Exception while writting to file ["
					+ aFileName + "] ", ioe );
		}
		finally {
			if ( fos != null ) {
				try {
					fos.close();
				}
				catch ( IOException ex ) {
				}
			}
		}
	}


	// -------------------------------------------------------------------------
	/**
	 * Return list of files/directory under specified directory. If specified path
	 * is not a directory, then returns null.
	 * 
	 * @param aPath
	 * @return List of files/directories under specified directory
	 */
	public static List<File> getDirectoryListing ( String aPath ) {

		// List<String> filenameList = null;

		File tmpFile = new File( aPath );

		File[] files = tmpFile.listFiles();
		return Arrays.asList( files );

		// if(files != null && files.length > 0) {
		// filenameList = new ArrayList<String>();
		// for(File file : files) {
		// if(file.isFile()){
		// filenameList.add(file.getAbsolutePath());
		// }
		// }
		// }
		//
		// return filenameList;
	}


	public static boolean isFileExists ( String aFilename ) {
		File tmpFile = new File( aFilename );
		return tmpFile.exists();
	}
	
	// -------------------------------------------------------------------------
	/**
	 * Returns true if and only if the file denoted by this abstract pathname exists and is a directory; false otherwise
	 * 
	 * @param aPath
	 * @return
	 */
	public static boolean isDirectory(String aPath) {
		File tmpFile = null;
		tmpFile = new File(aPath);
		return tmpFile.isDirectory();
	}
	
	//--------------------------------------------------------------------------
	public static String getParentPath ( 
		String aFilepath 
	) throws Exception {
		File file = new File( aFilepath );
		String parentPath = file.getParent();
		if(parentPath == null) {
			throw new Exception("Parent path not found for file <" + aFilepath + ">.");
		}
		return parentPath;
	}
	
	/**
	 * This method takes the absolute file path and returns the file name with
	 * extension
	 * 
	 * @param aAbsoluteFilePath
	 * @return
	 */
	public static String getFilename(String aAbsoluteFilePath) {
		return FilenameUtils.getName(aAbsoluteFilePath);
	}
	
	/**
	 * This method takes the absolute file path and returns the file name without
	 * extension
	 * 
	 * @param aAbsoluteFilePath
	 * @return
	 */
	public static String getFilenameWithoutExt (String aAbsoluteFilePath) {
		return FilenameUtils.getBaseName(aAbsoluteFilePath);
	}
	
	public static String getExtension (String aFilename) {
		return FilenameUtils.getExtension( aFilename );
	}
	
	/**
	 * Returns list of files for directory. If directory is empty, then return empty list.
	 * Returns null of specified file is not directory.
	 * 
	 * @param aFile
	 * @return
	 * @throws Exception
	 */
	public static List<File> list ( File aFile, FilenameFilter aFilenameFilter ) throws Exception {
		if ( !aFile.exists() ) {
			throw new Exception( "File doesn't exists with path <"
					+ aFile.getAbsolutePath() + ">" );
		}

		List<File> files = null;
		if ( aFile.isDirectory() ) {
			File[] fileArray = aFile.listFiles(aFilenameFilter);
			files = new ArrayList<>();
			if ( fileArray != null ) {
				for ( File file : fileArray ) {
					files.add( file );
				}
			}
		}
		return files;
	}
	/**
	 * Returns list of files for directory. If directory is empty, then return empty list.
	 * Returns null of specified file is not directory. Returns the list in sorted order.
	 * 
	 * @param aFile - Directory/Folder to list
	 * @param aFilenameFilter - Filename filter to filter the files from list
	 * @param aSortOrder - Sort order of files. Options are NAME, LAST_MODIFY
	 * @return
	 * @throws Exception
	 */
	public static List<File> list ( File aFile, FilenameFilter aFilenameFilter, final String aSortOrder) throws Exception {
		if ( !aFile.exists() ) {
			throw new Exception( "File doesn't exists with path <"
					+ aFile.getAbsolutePath() + ">" );
		}

		List<File> files = null;
		if ( aFile.isDirectory() ) {
			File[] fileArray = aFile.listFiles(aFilenameFilter);
			files = new ArrayList<>();
			if ( fileArray != null ) {
				for ( File file : fileArray ) {
					files.add( file );
				}
			}
		}
		
		Comparator<File> comparator = new Comparator<File> () {
			@Override
			public int compare(File aFile1, File aFile2) {
				switch(aSortOrder) {
				case SORT_BY_NAME:
					return aFile1.getName().compareTo(aFile2.getName());
				case SORT_BY_LAST_MODIFY:
					if(aFile1.lastModified() >= aFile1.lastModified()) {
						return 1;
					} else {
						return -1;
					}
				default:
					return 0;
				}
			}
		};
		
		Collections.sort(files, comparator);
		return files;
	}
	
	public static boolean isFilenameValid (String aFilename) {
		return VALID_FILE_NAME_PATERN.matcher(aFilename).matches();
	}
}
