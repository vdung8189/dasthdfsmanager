package org.esiag.isidis.dast.hdfs.apiImplentations;


import java.io.IOException;

import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.esiag.isidis.dast.hdfs.api.IBigFileManager;
import org.esiag.isidis.dast.hdfs.api.IDistributedFileManager;
import org.esiag.isidis.dast.hdfs.api.ISmallFileManager;
import org.esiag.isidis.dast.hdfs.log.Log;
import org.esiag.isidis.dast.hdfs.utils.MyHDFSClient;
//import org.esiag.isidis.dast.hdfs.utils.ConfigurationReader;


/***
 * Implementation of IDistributedFileManager interface
 * @author Viet Dung NGUYEN
 */
public class DistributedFileManager implements IDistributedFileManager {
	
	protected FileSystem fs = null;
	/**
	 * bigFileManager instance
	 */
	private static IBigFileManager bigFileManager = null;
	
	/**
	 * smallFileManager instance
	 */
	private static ISmallFileManager smallFileManager = null;

	/**
	 * DistributedFileManager default constructor
	 */
	
	public DistributedFileManager(){
		fs = MyHDFSClient.getInstance().getFileSystem();
	}
	
	/**
	 * get the bigFileManager instance to deal with big file 
	 * @return the bigFileManager instance
	 */
	public IBigFileManager dealWithBigFile(){
		if(bigFileManager == null){
			bigFileManager = new IBigFileManagerImpl();
		}
		return bigFileManager;
	}
	

	/**
	 * get the smallFileManager instance to deal with small file 
	 * @return the bigFileManager instance
	 */
	public ISmallFileManager dealWithSmallFile(){
		if (smallFileManager == null){
			smallFileManager = new ISmallFileManagerImpl();
		}
		return smallFileManager;
	}
	
	 /** 
	  * Create a new directory in HDFS File System
	  *  
	  * @param hdfs 
	  * @param dirName 
	  */  
	public synchronized  void mkdirs(String dirName) throws IOException{
    	try {
	         if (fs.mkdirs(new Path(dirName))) {  
	        	 Log.logger.info("create directory " + dirName + " successed. ");  
	         } else {  
	        	 Log.logger.error("create directory " + dirName + " failed. ");  
	         }  
         } catch (Exception e) {  
        	 Log.logger.error("create directory " + dirName + " failed: "+e.toString());  
         }  
     }  
	
	/**
     * Delete a file from the HDFS
     *  
     * @param hdfs 
     * @param fileToDelete
     */  
	
	public synchronized  void deleteHDFSFile(String fileToDelete) throws IOException{  
		
        try {
        	if (fs.delete(new Path(fileToDelete), true)) {
        		Log.logger.info("delete HDFS file " + fileToDelete + " successed. "); 
        	} else{
        		 Log.logger.error("delete HDFS file " + fileToDelete + " failed. "); 
        	}
        } catch (Exception e) {  
        	Log.logger.error("delete HDFS file " + fileToDelete + " failed :"+e.toString());
     	}
	 }  

	
	
	/**
	 * List files
	 */
	public void listFile(String path) {

		Path dst = null;
		
		if (null == path || "".equals(path)) {
			dst = new Path(path);
		} else {
			dst = new Path(path);
		}
		
		try {
			String relativePath = "";
			
			FileStatus[] fList = fs.listStatus(dst);
			
			for (FileStatus f : fList) {
				if (null != f) {
					relativePath = new StringBuffer().append(
							f.getPath().getParent()).append("/").append(
							f.getPath().getName()).toString();
					if (f.isDirectory()) {
						listFile(relativePath);
					} else {
						Log.logger.info(convertSize(f.getLen()) + "/t/t"+ relativePath);
					}
				}
			}
		} catch (Exception e) {
			Log.logger.error("list files of " + path + " failed :"+e.toString());
		} finally {
		}
	}

	
	/**
	 * Convert size from byte to KB, MB, GB
	 * @param size
	 * @return result
	 */
	public synchronized  String convertSize(long size) {
		String result = String.valueOf(size);
		if (size < 1024 * 1024) {
			result = String.valueOf(size / 1024) + " KB";
		} else if (size >= 1024 * 1024 && size < 1024 * 1024 * 1024) {
			result = String.valueOf(size / 1024 / 1024) + " MB";
		} else if (size >= 1024 * 1024 * 1024) {
			result = String.valueOf(size / 1024 / 1024 / 1024) + " GB";
		} else {
			result = result + " B";
		}
		return result;
	}

}
