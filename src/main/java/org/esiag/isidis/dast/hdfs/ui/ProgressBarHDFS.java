package org.esiag.isidis.dast.hdfs.ui;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.ProgressBar;
import org.esiag.isidis.dast.hdfs.log.Log;

public class ProgressBarHDFS extends ApplicationWindow {
	private ProgressBar progressBar;
	private String title;
	private String message;
	
	public ProgressBarHDFS(String title, String message) {
		super(null);
		this.title = title;
		this.message = message;
		Log.init("configuration/log4j.properties", "log/log.txt");
	}
	
	protected Control createContents(Composite parent) {
		getShell().setText("[DAST v2.0] "+title);
		getShell().setSize(300, 100);
		getShell().setMaximized(false);
		progressBar = new ProgressBar(getShell(), SWT.HORIZONTAL);
		progressBar.setMinimum(0);
		progressBar.setMaximum(100);
		return parent;
	}

	public void showProgressBar() {
		Display.getDefault().asyncExec(new Runnable() {
		    public void run() {
				setBlockOnOpen(true);
				open();
				Display.getCurrent().dispose();
		    }
		});
	}
	
	public void setPercentage(final int percentage) {
		Display.getDefault().asyncExec(new Runnable() {
		    public void run() {
				progressBar.setSelection(percentage);
		    }
		});
	}
}
