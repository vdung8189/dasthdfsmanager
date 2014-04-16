package org.esiag.isidis.dast.hdfs.ui;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.fs.FileStatus;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.esiag.isidis.dast.hdfs.api.IDistributedFileManager;
import org.esiag.isidis.dast.hdfs.utils.MyHDFSClient;
/**
 * Upload an folder to HDFS 
 * 
 * @author Viet Dung NGUYEN
 *
 */
public class UploadFolderAction extends Action {
	private MyActionGroup actionGroup;
	private IDistributedFileManager dfm;
	
	public UploadFolderAction(MyActionGroup actionGroup,IDistributedFileManager dfm ) {
		this.actionGroup = actionGroup;
		this.dfm = dfm;
		setText("Uploader un répertoire");
	}
	
	public void run() {
		 DirectoryDialog dialog =
			        new DirectoryDialog(Display.getCurrent().getActiveShell(), SWT.OPEN
			            | SWT.MULTI);
			    dialog.setText("Chosissez un répertoire");

			    String dirName = dialog.open();
			    final File dir = new File(dirName);
			    List<File> files = new ArrayList<File>();
			    files.add(dir);
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
		                    "Erreur.\n" + ioe);
		              }
		            }
			    actionGroup.refresh();
	}
}