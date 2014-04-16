package org.esiag.isidis.dast.hdfs.ui;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.fs.FileStatus;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.esiag.isidis.dast.hdfs.api.IDistributedFileManager;
import org.esiag.isidis.dast.hdfs.utils.MyHDFSClient;
/**
 * Uploader un fichier au HDFS 
 * 
 * @author Viet Dung NGUYEN
 *
 */
public class UploadFileAction extends Action {
	private MyActionGroup actionGroup;
	private IDistributedFileManager dfm;
	
	public UploadFileAction(MyActionGroup actionGroup,IDistributedFileManager dfm) {
		this.actionGroup = actionGroup;
		this.dfm = dfm;
		setText("Uploader un fichier");
	}
	
	public void run() {
	    FileDialog dialog =
	        new FileDialog(Display.getCurrent().getActiveShell(), SWT.OPEN
	            | SWT.MULTI);
	    dialog.setText("Choisir un fichier");
	    dialog.open();
	    List<File> files = new ArrayList<File>();
	    for (String fname : dialog.getFileNames())
	      files.add(new File(dialog.getFilterPath() + File.separator + fname));
	    for (File file : files) {
              try {
            	Object selection = actionGroup.getTableSelection();
  				FileStatus f = (selection == null?
  						(FileStatus) actionGroup.getTreeSelection():
  							(FileStatus) actionGroup.getTableSelection());
                dfm.uploadFileToHDFS(MyHDFSClient.getInstance().getFs(), file.toString(),f.getPath().toString());
              } catch (IOException ioe) {
                ioe.printStackTrace();
                MessageDialog.openError(null,
                    "Erreur",
                    "Problème.\n" + ioe);
              }
            }
	    actionGroup.refresh();
	}
	}