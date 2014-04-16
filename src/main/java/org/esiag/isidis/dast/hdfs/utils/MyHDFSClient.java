package org.esiag.isidis.dast.hdfs.utils;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
/**
 * 
 * @author vietdung.nguyen@orange.fr
 * 
 *
 */
public class MyHDFSClient{
	private static MyHDFSClient myHDFSClient = null;
	private FileSystem fs = null;
	
	private MyHDFSClient(){

		Configuration conf = new Configuration();
		conf.addResource(new Path("./configuration/core-site.xml"));
		try {		
			fs = FileSystem.get(conf);		
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	}
		
		
	
	public FileSystem getFs() {
		return fs;
	}



	/*
	 * get File System
	 */
	public final static MyHDFSClient getInstance() {
		if (myHDFSClient == null){
			myHDFSClient = new MyHDFSClient();
		}
		return myHDFSClient;
		
	}
}
