package org.esiag.isidis.dast.hdfs.api;

import java.io.IOException;

//import org.esiag.isidis.dast.hdfs.utils.ConfigurationReader;

public interface IDistributedFileManager {
	
	/**
	 * The maximum size of a file which is considered as small (less than 10MB)
	 */
	public final static long MAXI_SMALL_FILE = 10485760;

	/**
	 * get the bigFileManager instance to deal with big file 
	 * @return the bigFileManager instance
	 */
	
	public IBigFileManager dealWithBigFile();

	/**
	 * get the smallFileManager instance to deal with small file 
	 * @return the bigFileManager instance
	 */

	public ISmallFileManager dealWithSmallFile();
	//public void createHDFSFile(String newFilepath, String data);

	 /** 
	  * Create a new directory in HDFS File System
	  *  
	  * @param name of the directory 
	  */  

	public void mkdirs(String dirName) throws IOException;
	/**
	 * Delete file from the Hadoop Distributed File System
	 * 
	 * @param fileToDelete the string name of the file to delete
	 */
	public void deleteHDFSFile(String fileToDelete) throws IOException;

	/**
	 *  This method show all files located in the given path  
	 *  
	 * @param path where you want to list files
	 */
	public void listFile( String path);
	/**
	 * 
	 *Convert form byte to KB or MB or GB
	 *
	 * @param size
	 * @return the size converted into KB/MB/GB in String type
	 */
	public String convertSize(long size);
	
}
