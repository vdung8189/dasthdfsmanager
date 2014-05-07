package org.esiag.isidis.dast.hdfs.remoteIterator;

import java.io.IOException;
import java.io.InputStream;

import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.RemoteIterator;
import org.esiag.isidis.dast.hdfs.utils.MyHDFSClient;

public abstract class RemoteIteratorAbstract<E> implements RemoteIterator<E>{
	
	protected final long BLOCK_SIZE_IN_BYTE = 10 * 1024L;
	protected FileSystem fs = null;
	protected long numberOfBlocks = 0;
	protected long fileSize = 0;
	protected long lastBlockSize = 0;
	protected long actualPosition = 0;
	protected Path filePath= null;
	protected InputStream input = null;
	protected byte[] bytes = null;
	
	public RemoteIteratorAbstract(){	
		fs = MyHDFSClient.getInstance().getFileSystem();
	}
	/**
	 * Calcule number of block that a given file has to be divided
	 */
	public void calculNumberOfBlocks(){
		//lastBlockSize = fileSizwe modulo BLOCK_SIZE_IN_BYTE
		this.lastBlockSize = fileSize % BLOCK_SIZE_IN_BYTE;
		//calculate number of block if a file is divided to block of 10KBytes
		this.numberOfBlocks = fileSize/ BLOCK_SIZE_IN_BYTE;
		System.out.println("Number of Blocks " + numberOfBlocks);
		// if the file size if not a multiple of 10KB
		if (lastBlockSize != 0){
			// then the number of blocks increase of 1 block
			this.numberOfBlocks++;
		}
	}
	/**
	 *  Getter of number of blocks
	 * @return numberOfBlocks
	 */
	public long getNumberOfBlocks(){
		return (numberOfBlocks); 
	}
	
	 /**
	   * Returns <tt>true</tt> if the iteration has more elements.
	   *
	   * @return <tt>true</tt> if the iterator has more elements.
	   * @throws IOException if any IO error occurs
	   */
	
	public boolean hasNext() throws IOException{
		//if the actual position is less than the number of block then return true else return false
		if (actualPosition < numberOfBlocks){
			//System.out.println("At " + actualPosition +" hasNext : true ");
			return true;	
		}else{
			//System.out.println("At " + actualPosition +" hasNext : false");
			return false;
		}
	}
	
	public long getFileSize(){
		return this.fileSize;
		
	}
}
