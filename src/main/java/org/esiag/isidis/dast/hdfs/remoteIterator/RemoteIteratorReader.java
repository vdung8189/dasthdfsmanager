package org.esiag.isidis.dast.hdfs.remoteIterator;

import java.io.IOException;
import java.util.NoSuchElementException;

import org.apache.hadoop.fs.Path;


public class RemoteIteratorReader extends RemoteIteratorAbstract<byte[]> {

	/**
	 * Default constructor
	 * @param fileLocation
	 */
	public RemoteIteratorReader(String fileLocation)
	{
		super();
		
		this.filePath = new Path(fileLocation);
		
		try {
			this.input = fs.open(filePath);
		} catch (IllegalArgumentException | IOException e) {
			e.printStackTrace();
		}
	
		try {
			this.fileSize = fs.getFileStatus(filePath).getLen();
			System.out.println(fileSize);
			//calculate number of blocks
			this.calculNumberOfBlocks();
			System.out.println();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	  /**
	   * Returns the next element in the iteration.
	   *
	   * @return the next element in the iteration.
	   * @throws NoSuchElementException iteration has no more elements.
	   * @throws IOException if any IO error occurs
	   */

	@Override
	public byte[] next() throws IOException {
		//10KB
		int blockSizeInByte = (int) BLOCK_SIZE_IN_BYTE;
		
		// if we get the lastBlock then we instantiate an table of byte correspond to its size
		if ((lastBlockSize != 0) && (this.actualPosition == numberOfBlocks-1)) 
			blockSizeInByte = (int) lastBlockSize;
		
		this.bytes = new byte[blockSizeInByte];
		input.read(bytes, 0, blockSizeInByte);
		
		// arrive at the last block
		if (actualPosition == (numberOfBlocks - 1)){
			input.close();
		}
			
		//increment 1 each call to this function
			
			actualPosition++;
			
		return bytes;
	}


}
