package org.esiag.isidis.dast.hdfs.remoteIterator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.NoSuchElementException;

import org.apache.hadoop.fs.Path;

public class RemoteIteratorWriter extends RemoteIteratorAbstract<Void>{

	
	private OutputStream output = null;
	
	/**
	 * Default Constructor 
	 * @param fileSrc file from local disk
	 * @param fileDest file in HDFS 
	 * @throws FileNotFoundException 
	 */
	public RemoteIteratorWriter(File fileSrc, String fileDest) throws FileNotFoundException{
		super();
		this.filePath = new Path(fileDest);
		this.input = new FileInputStream(fileSrc);
		
		try {
			output = fs.create(filePath, true);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.fileSize = fileSrc.length();
		//calcul Number of block
		this.calculNumberOfBlocks();
	}
	
	  /**
	   * Returns the next element in the iteration.
	   *
	   * @return the next element in the iteration.
	   * @throws NoSuchElementException iteration has no more elements.
	   * @throws IOException if any IO error occurs
	   */
	@Override
	public Void next() throws IOException {
		
		int blockSizeInByte = (int) BLOCK_SIZE_IN_BYTE;
		
		if ((lastBlockSize != 0) && (actualPosition == (numberOfBlocks-1) )) 
			blockSizeInByte = (int) lastBlockSize;
		
		bytes = new byte[blockSizeInByte];
		
		input.read(bytes, 0, blockSizeInByte);
		
		output.write(bytes);
		
		
		if (actualPosition == (numberOfBlocks - 1) )
		{
			input.close();
			output.close();
		}
		
		actualPosition++;
		
		return null;
	}

}
