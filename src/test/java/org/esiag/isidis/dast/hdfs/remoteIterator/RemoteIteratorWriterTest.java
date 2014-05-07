package org.esiag.isidis.dast.hdfs.remoteIterator;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.apache.hadoop.fs.Path;
import org.esiag.isidis.dast.hdfs.remoteIterator.RemoteIteratorWriter;

public class RemoteIteratorWriterTest {
	
	File localFile = null;
	RemoteIteratorWriter riw = null;
	String destFile = null;
	@Before
	public void setUp() throws Exception {
		//init
		String HOME = System.getenv("HOME");
		System.out.println(HOME);
		localFile = new File(HOME + "/temps/200706hpd.txt");
		destFile = "hdfs://localhost:8020/junit/R3/200706hpd.txt"; 
		riw = new RemoteIteratorWriter(localFile, destFile);
	}

	@After
	public void tearDown() throws Exception {
	}
	
	/**
	 * Nominal case
	 * 
	 * init : calculate size of localfile "/home/vietdung/temps/200706hpd.txt"
	 * transition : using next() in order to write block by block from local disk to hdfs
	 * result : the size of the copied hdfs file is identical to the local file
	 * @throws IOException 
	 */
	@Test
	public void testNext() throws IOException {
		//init
		long localFileSize = localFile.length();
		//transition
		while (riw.hasNext()){
			riw.next();
		}
		Path destfilePath = new Path(destFile);
		long hdfsCopiedFileSize = riw.fs.getFileStatus(destfilePath).getLen();
		//verification
		assertEquals(localFileSize,hdfsCopiedFileSize);
	}
	
	/**
	 * Nominal case
	 * init: calculate number of blocks if we divide the file size into block of 10KB 
	 * 			and stock the result into expectedResult
	 * transition: calculate number of block using the methode calculNumberOfBlocks()
	 * result: true if we obtain the same number of blocks 
	 */
	@Test
	public void testCalculNumberOfBlocks() {
		//init
		long expectedResult = 0;
		long fileSize = riw.getFileSize();
		
		expectedResult = fileSize / riw.BLOCK_SIZE_IN_BYTE;
		
		if (fileSize % riw.BLOCK_SIZE_IN_BYTE != 0){
			expectedResult++;
		}
		//transition
		long actualResult = riw.getNumberOfBlocks();
		//verification
		assertEquals(expectedResult, actualResult);
	}
	

}
