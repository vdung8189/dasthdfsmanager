package org.esiag.isidis.dast.hdfs.api;


public interface ISmallFileManager extends IDistributedFileManager {

	/**
	 * Using already implemented method copyFromLocal in FileSystem for small file 
	 * which is less than 10MB
	 * @param srcFile
	 * @param dstFile
	 */
	public void uploadSmallFileToHDFS(String srcFile, String dstFile);
	/**
	 * Using already implemented method copyToLocal in FileSystem for small file 
	 * which is less than 10MB
	 * @param srcFile
	 * @param dstFile
	 */
	public void downloadSmallFileFromHDFS(String srcFile, String dstFile);
	
}
