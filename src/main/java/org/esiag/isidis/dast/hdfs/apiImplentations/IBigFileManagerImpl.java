package org.esiag.isidis.dast.hdfs.apiImplentations;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.RemoteIterator;
import org.esiag.isidis.dast.hdfs.api.IBigFileManager;
import org.esiag.isidis.dast.hdfs.remoteIterator.RemoteIteratorReader;
import org.esiag.isidis.dast.hdfs.remoteIterator.RemoteIteratorWriter;
/**
 * 
 * @author Viet Dung NGUYEN
 *
 */
public class IBigFileManagerImpl extends DistributedFileManager implements IBigFileManager {
	
	private RemoteIterator<byte[]> rmr = null;
	private RemoteIterator<Void> rmw = null;
	
	public IBigFileManagerImpl(){
		super();
	}

	@Override
	public RemoteIterator<byte[]> readFile(String fileLocation) {
		
		//RemoteIterator<byte[]> remoteIterator = null;
		
		rmr = new RemoteIteratorReader(fileLocation);
	
		return rmr;
	}
	/**
	 * 
	 */
	@Override
	public RemoteIterator<Void> writeFile(File file,String destinationFileLocation) {
		
		//RemoteIterator<Void> remoteIterator = null;
		try {
			rmw = new RemoteIteratorWriter(file, destinationFileLocation);
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		}
		return rmw;
	}

//	@Override
//	public int getReadStatus() {
//		int percent = 0;
//		
//		return percent;
//	}
//	public int getWriteStatus(){
//		int percent = 0;
//		
//		return percent;
//		
//	}

}
