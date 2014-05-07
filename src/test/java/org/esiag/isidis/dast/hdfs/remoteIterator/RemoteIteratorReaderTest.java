package org.esiag.isidis.dast.hdfs.remoteIterator;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class RemoteIteratorReaderTest {
	
	private RemoteIteratorReader rir = null;
	private final String fileLocation = "hdfs://localhost:8020/junit/R3/200706hpd.txt";
	@Before
	public void setUp() throws Exception {
		rir = new RemoteIteratorReader(fileLocation);
		rir.calculNumberOfBlocks();
	}

	@After
	public void tearDown() throws Exception {
		rir = null;
	}
	/**
	 * Nominal case
	 * init: 
	 * transition: 
	 * result : if hasNext() return true then next() has to return a not null bytes
	 * @throws IOException 
	 */
	@Test
	public void testNext() throws IOException {

		while(rir.hasNext() == true)
		{
			assertNotNull(rir.next());
		}
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
		long fileSize = rir.getFileSize();
		
		expectedResult = fileSize / rir.BLOCK_SIZE_IN_BYTE;
		
		if (fileSize % rir.BLOCK_SIZE_IN_BYTE != 0){
			expectedResult++;
		}
		//transition
		long actualResult = rir.getNumberOfBlocks();
		//verification
		assertEquals(expectedResult, actualResult);
	}
	

}
