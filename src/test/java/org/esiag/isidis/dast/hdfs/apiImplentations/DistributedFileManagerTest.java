package org.esiag.isidis.dast.hdfs.apiImplentations;

import static org.junit.Assert.*;

import java.io.IOException;

import org.apache.hadoop.fs.Path;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DistributedFileManagerTest {
	
	DistributedFileManager dfm = null;
	/**
	 *  run before every test
	 * @throws Exception
	 */
	
	@Before
	public void setUp() throws Exception {
		 dfm = new DistributedFileManager();
	}

	/**
	 * run after very test
	 * @throws Exception
	 */
	
	@After
	public void tearDown() throws Exception {
		dfm = null;
	}
	
	
	/**
	 * Nominal case  with  a valid path dir
	 * @throws IOException
	 */
	@Test
	public void mkdirsWithValidDirName() throws IOException {
		//init
		Path pathValid = new Path("hdfs://localhost:8020/junit/testMkdirsWithValidDirName");
		//verification
		assertTrue(dfm.fs.mkdirs(pathValid));
	}

	@Test
	public void deleteValidPath() throws IOException{
		//init
		Path pathValid = new Path("hdfs://localhost:8020/junit/testMkdirsWithValidDirName");
		//verification
		assertTrue(dfm.fs.mkdirs(pathValid));
		assertTrue(dfm.fs.delete(pathValid, true));
		
	}

	/**
	 * Error case with invalid path to delete
	 * 
	 * @throws IOException
	 */
	@Test
	public void deleteInvalidPath() throws IOException{
		Path pathValid = new Path("invalid path");
		assertFalse(dfm.fs.delete(pathValid, true));
		
	}

}
