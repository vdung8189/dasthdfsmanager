package org.esiag.isidis.dast.hdfs.apiImplentations;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ISmallFileManagerImplTest {
	
	ISmallFileManagerImpl sfm = null;
	
	@Before
	public void setUp() throws Exception {
		sfm = new ISmallFileManagerImpl();
	}

	@After
	public void tearDown() throws Exception {
		sfm = null;
	}

	@Test
	public void testUploadSmallFileToHDFS() {
//
//		sfm.downloadSmallFileFromHDFS(srcFile, destFile);
	}

	@Test
	public void testDownloadSmallFileFromHDFS() {
	//TODO	
	}

}
