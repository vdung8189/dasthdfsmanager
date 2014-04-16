package org.esiag.isidis.hdfs.remoteIterator;

import java.io.IOException;

import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.RemoteIterator;
import org.esiag.isidis.dast.hdfs.api.IDistributedFileManager;

public class DastRemoteIterator implements RemoteIterator<byte[]>{
	/*
	 * 
	 */
	private IDistributedFileManager dfm = null;
	private FileSystem hdfs = null;
	
	/*
	 * Default constructor 
	 */

	public DastRemoteIterator(IDistributedFileManager dfm){
		//this.dfm = dfm;
		//TODO get the file System
	
	}
	
	@Override
	public boolean hasNext() throws IOException {
		return false;
	}

	@Override
	public byte[] next() throws IOException {
		return null;
	}

}
