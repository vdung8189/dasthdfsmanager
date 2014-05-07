package org.esiag.isidis.dast.hdfs.apiImplentations;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.RemoteIterator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class IBigFileManagerImplTest {
	private IBigFileManagerImpl bfm = null;
	private final long BLOCK_SIZE_IN_BYTE = 10 * 1024L;
	
	@Before
	public void setUp() throws Exception {
		bfm = new IBigFileManagerImpl();
	}

	@After
	public void tearDown() throws Exception {
		bfm = null;
	}

	@Test
	public void testReadFile() throws IOException {
		String fileLocation = "hdfs://localhost:8020/junit/R3/ubuntu-14.04-desktop-i386.iso";
		RemoteIterator<byte[]> rm = bfm.readFile(fileLocation);
		assertNotNull(rm);
	}

	@Test
	public void testWriteFile() {
		String HOME = System.getenv("HOME");
		
		String localFileString = HOME + "/temps/ubuntu-14.04-desktop-i386.iso";
		String hdfsFilePath = "hdfs://localhost:8020/junit/R3/ubuntu-14.04-desktop-i386.iso";
		File localFile = new File(localFileString);
		RemoteIterator<Void> rm = bfm.writeFile(localFile, hdfsFilePath);
		assertNotNull(rm);
	}

}
