package org.esiag.isidis.dast.hdfs.apiImplentations;
import org.apache.hadoop.fs.Path;
import org.esiag.isidis.dast.hdfs.api.ISmallFileManager;
import org.esiag.isidis.dast.hdfs.log.Log;

/**
 *@author Viet Dung NGUYEN 
 */

public class ISmallFileManagerImpl extends DistributedFileManager implements ISmallFileManager {

	public ISmallFileManagerImpl(){
		super();
	}
	

	 /**
	  *   
	  */
	public synchronized void uploadSmallFileToHDFS(String srcFile, String destFile) {
		try {
			fs.copyFromLocalFile(new Path(srcFile), new Path(destFile));
			Log.logger.info("upload " + srcFile + " to  " + destFile + " successed. ");  
		} catch (Exception e) {
			Log.logger.error("upload " + srcFile + " to  " + destFile + " failed :"+e.toString()); 
		}
		
		
	}

	@Override
	public synchronized void downloadSmallFileFromHDFS(String srcFile, String destFile)
	 {
		try {
			fs.copyToLocalFile(new Path(srcFile), new Path(destFile));
			  Log.logger.info("download from " + srcFile + " to  " + destFile  + " successed. ");  
		} catch (Exception e) {
			Log.logger.error("download from " + srcFile + " to  " + destFile + " failed :"+e.toString());}
		}

	

}
