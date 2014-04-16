package org.esiag.isidis.dast.hdfs.api;

import org.eclipse.swt.widgets.Display;
import org.esiag.isidis.dast.hdfs.ui.HDFSExplorer;

public class HDFSLauncher {
	public static void main(String[] args) {
		HDFSExplorer hdfsExplorer = new HDFSExplorer();
		hdfsExplorer.setBlockOnOpen(true);
		hdfsExplorer.open();
		Display.getCurrent().dispose();
	}
}
