package org.esiag.isidis.dast.hdfs.apiImplentations;

import java.io.*;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.RemoteIterator;
import org.esiag.isidis.dast.hdfs.api.IDistributedFileManager;
import org.esiag.isidis.dast.hdfs.log.Log;
//import org.esiag.isidis.dast.hdfs.utils.ConfigurationReader;


/***
 * HDFS Client API. (Hadoop Distributed File System)
 * @author NGUYEN Viet Dung vietdung.nguyen@orange.fr
 */
public class DistributedFileManager implements IDistributedFileManager {
	 /**
     *  Get the file system for the configuration of the program.
     * @param ip
     * @param port
     */  
	/*public synchronized FileSystem getFileSystem(ConfigurationReader conf) {
		FileSystem hdfs = null;
		String url = ConfigurationReader.loadconfig().getConnString();
		Configuration config = new Configuration();
		config.set("fs.default.name", url);
		try {
			hdfs = FileSystem.get(config);
		} catch (Exception e) {
			Log.logger.error(" getFileSystem failed :"+e.toString());
		}
		return hdfs;
	}
*/
	/**
     * Create HDSF File
     *  
     * @param hdfs 
     * @param path 
     * @param data 
     */  
	public synchronized  void createHDFSFile(FileSystem hdfs, String newFilepath, String data) {  
		Path dstPath = new Path(newFilepath);  
		try {  	
		     FSDataOutputStream os = hdfs.create(dstPath);  
		     os.writeUTF(data);  
		     os.close();
		     Log.logger.info("write data to " + newFilepath + " successed. ");  
		} catch (Exception e) {  
			 Log.logger.error("write data to " + newFilepath + " failed."+e.toString());  
		}  
	}  
	
	 /** 
	  * Create a new directory in HDFS File System
	  *  
	  * @param hdfs 
	  * @param dirName 
	  */  
	public synchronized  void mkdirs(FileSystem hdfs, String dirName) {
    	try {
    		Path src = new Path(dirName);
    		boolean succes = hdfs.mkdirs(src);  
	         if (succes) {  
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
	public synchronized  void deleteHDFSFile(FileSystem hdfs, String fileToDelete) {  
        Path pathToDelete = new Path(fileToDelete); 
        try {
        	if (hdfs.delete(pathToDelete, true)) {
        		Log.logger.info("delete HDFS file " + pathToDelete + " successed. "); 
        	} else{
        		 Log.logger.error("delete HDFS file " + pathToDelete + " failed. "); 
        	}
        } catch (Exception e) {  
        	Log.logger.error("delete HDFS file " + pathToDelete + " failed :"+e.toString());
     	}
	 }  
	
	 /**
     * Upload a local file to HDFS
     *   
     * @param hdfs 
     * @param srcFile 
     * @param dstFile 
     */     
	public synchronized  void uploadFileToHDFS(FileSystem hdfs, String srcFile,
			String dstFile) throws IOException {
		Path srcPath = new Path(srcFile);
		Path dstPath = new Path(dstFile);
	    try {  
		 hdfs.copyFromLocalFile(false, true, srcPath, dstPath); 
		 Log.logger.info("upload " + srcFile + " to  " + dstFile + " successed. ");  
	 } catch (Exception e) {  
		 Log.logger.error("upload " + srcFile + " to  " + dstFile + " failed :"+e.toString());   
		 }  

		//InputStream in = new BufferedInputStream(new FileInputStream(srcFile));

	}    
	
	/** 
	* Download a file from HDFS to local system
	*  
	* @param hdfs 
	* @param localPath 
	* @param remotePath 
	*/  
	public synchronized  void downloadFileFromHDFS(FileSystem fs, String localPath,  
	String remotePath) {    
	        Path dstPath = new Path(remotePath);  
	        Path srcPath = new Path(localPath);  
	        try {  
	            fs.copyToLocalFile(false, dstPath, srcPath);  
	            Log.logger.info("download from " + remotePath + " to  " + localPath  
	                    + " successed. ");  
	        } catch (Exception e) {  
	        	Log.logger.error("download from " + remotePath + " to  " + localPath + " failed :"+e.toString());  
	        }  
	    }  
	
	public synchronized  void listFile(FileSystem hdfs, String path) {
		
		Path dst;
		
		if (null == path || "".equals(path)) {
			dst = new Path(path);
		} else {
			dst = new Path(path);
		}
		try {
			String relativePath = "";
			FileStatus[] fList = hdfs.listStatus(dst);
			for (FileStatus f : fList) {
				if (null != f) {
					relativePath = new StringBuffer().append(
							f.getPath().getParent()).append("/").append(
							f.getPath().getName()).toString();
					if (f.isDirectory()) {
						listFile(hdfs, relativePath);
					} else {
						Log.logger.info(convertSize(f.getLen()) + "/t/t"
								+ relativePath);
					}
				}
			}
		} catch (Exception e) {
			Log.logger.error("list files of " + path + " failed :"+e.toString());
		} finally {
		}
	}
	
	/**
	 * Convert size from byte to kilobyte, mégabyte or gigabyte
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

	@Override
	public RemoteIterator<byte[]> readFile(String fileLocation)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RemoteIterator<Void> writeFile(File file, String StringFileLocation)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}
