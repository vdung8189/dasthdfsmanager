package org.esiag.isidis.dast.hdfs.api;

import java.io.File;
import java.io.IOException;

import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.RemoteIterator;
//import org.esiag.isidis.dast.hdfs.utils.ConfigurationReader;

public interface IDistributedFileManager {

	//public FileSystem getFileSystem(ConfigurationReader conf);
	public void createHDFSFile(FileSystem hdfs, String newFilepath, String data);
	public void mkdirs(FileSystem hdfs, String dirName);
	public void deleteHDFSFile(FileSystem hdfs, String fileToDelete);
	/*
	 * write from local file to HDFS FSDataOutputStream(String FSdestinationPath)
	 */
	public void uploadFileToHDFS(FileSystem hdfs, String srcFile,
			String dstFile) throws IOException;
	
	
	/*
	 *  with progress 
	 */
	
	/*
	 * write from hdfs filesystem to local FSDataInputStream(String FSSourcePath)
	 */
	public void downloadFileFromHDFS(FileSystem fs, String localPath,  
			String remotePath);
	/*
	 * 
	 */
	
	public void listFile(FileSystem hdfs, String path);
	/*
	 * Convert form byte to KB or MB or GB
	 */
	public String convertSize(long size);
	
	public RemoteIterator<byte[]> readFile(String fileLocation) throws Exception;
	public RemoteIterator<Void> writeFile(File file, String StringFileLocation) throws Exception;
}
