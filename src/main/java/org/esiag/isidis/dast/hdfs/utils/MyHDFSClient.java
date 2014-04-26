package org.esiag.isidis.dast.hdfs.utils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hdfs.DFSClient;
import org.esiag.isidis.dast.hdfs.log.Log;
/**
 * Give the FileSystem instance in order to manipulate file in the hdfs
 * @author vietdung.nguyen@orange.fr
 * 
 *
 */
public class MyHDFSClient{
	private static MyHDFSClient myHDFSClient = null;
	private FileSystem fs = null;
	private Configuration conf = null;
	
	
	private MyHDFSClient(){

		conf = new Configuration();
		conf.addResource(new Path("./configuration/core-site.xml"));
		
		try {		
			fs = FileSystem.get(conf);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public FileSystem getFileSystem() {
		return fs;
	}

	/**
	 * get File System
	 * @return MyHDFSClient instance
	 */
	public final static MyHDFSClient getInstance() {
		if (myHDFSClient == null){
			myHDFSClient = new MyHDFSClient();
		}
		return myHDFSClient;
	}
}
