package org.esiag.isidis.dast.hdfs.api;

import java.io.File;

import org.apache.hadoop.fs.RemoteIterator;

public interface IBigFileManager extends IDistributedFileManager{
	/**
	 * Reading the file from HDFS with the path in param
	 * @param fileLocation the path in String of the file you want to read from HDFS
	 * @return RemoteIterator which can be used to iterate between file's parts
	 * 
	 */
	RemoteIterator<byte[]> readFile(String fileLocation);
	
	
	/**
	 * Writing a file into HDFS block by block
	 * @param file the local file which you want to upload into your HDFS.
	 * @param destinationFileLocation where the file will be written into HDFS
	 * @return RemoteIterator<Void> used to save all file's blocks
	 */
	RemoteIterator<Void> writeFile(File file, String destinationFileLocation);
			
}
