package org.esiag.isidis.dast.hdfs.api;

import static org.junit.Assert.*;

import org.apache.hadoop.fs.FileSystem;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DistributedFileManagerTest {


	private FileSystem fs = null;
	
	@Before
	public void setUp() throws Exception {

		//fs = DistributedFileManager.getFileSystem(conf);
		
	}

	@After
	public void tearDown() throws Exception {

	}

	@Test
	public void testGetFileSystem() {
		//FileSystem fs1 = DistributedFileManager.getFileSystem(conf);
		//assertNotNull(fs1);	
	}

	@Test
	public void testCreateHDFSFile() {
		
	}

	@Test
	public void testMkdirs() {
		//DistributedFileManager.mkdirs(fs, "testMkdirs1");
		//DistributedFileManager.mkdirs(fs, "testMkdirs2");
	}

	@Test
	public void testDeleteHDFSFile() {
		//DistributedFileManager.deleteHDFSFile(fs, "testMkdirs1");
	}

	@Test
	public void testUploadFileToHDFS() {
//		try {
//			DistributedFileManager.uploadFileToHDFS(fs, "", "");
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

	@Test
	public void testDownload() {
		//fail("Not yet implemented");
	}

	@Test
	public void testListFile() {
		//fail("Not yet implemented");
	}

	@Test
	public void testConvertSize() {
		//fail("Not yet implemented");
	}

}
