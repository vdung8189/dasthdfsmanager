package org.esiag.isidis.dast.hdfs.utils;

import java.io.IOException;
import java.security.PrivilegedAction;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.security.UserGroupInformation;
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
		/*UserGroupInformation ugi = UserGroupInformation.createRemoteUser("vietdung");
		ugi.doAs(new PrivilegedAction<Void>() {

			@Override
			public Void run() {
				
				Path path = new Path("./configuration/core-site.xml");
				//System.out.println(path.toString());
				conf = new Configuration();
				
				conf.addResource(path);
				System.out.println(conf.getStringCollection("fs.defaultFS"));
				try {	
					
					fs = FileSystem.get(conf);
					
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				return null;
			}
		});*/
	
		Path path = new Path("./configuration/core-site.xml");
		//System.out.println(path.toString());
		conf = new Configuration();
		
		conf.addResource(path);
		System.out.println(conf.getStringCollection("fs.defaultFS"));
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
