package org.esiag.isidis.dast.hdfs.ui;

import java.io.File;

import org.apache.hadoop.fs.FileStatus;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.esiag.isidis.dast.hdfs.api.IDistributedFileManager;
import org.esiag.isidis.dast.hdfs.utils.MyHDFSClient;
/**
 * Download to local file system
 * @author Viet Dung NGUYEN
 *
 */
public class DownloadAction extends Action {
	private MyActionGroup actionGroup;
	private IDistributedFileManager dfm;
	/**
	 * Create the object and set the text to be shown in the GUI.
	 */
	public DownloadAction(MyActionGroup actionGroup,IDistributedFileManager dfm) {
		this.actionGroup = actionGroup;
		this.dfm = dfm;
		setText("Télécharger en local");
	}
	
	/**
	 * The action to be launched.
	 */
	@Override
	public void run() {
		//Show the secondary dialog (UI) allows user to choose the folder to put the downloaded file.
		DirectoryDialog dialog =new DirectoryDialog(Display.getCurrent().getActiveShell());
		    dialog.setText("Choisir un dossier");
		    String directory = dialog.open();
		    
		//If user clicks Cancel
	    if (directory == null) {
	        return;
	    } else {
	    	//Get the path
		    final File localDirectory = new File(directory);
		    
		    //Check if the path exists
		    if (!localDirectory.exists()) {
		    	localDirectory.mkdirs();
		    }

		    //Check if the paths is a directory
		    if (!localDirectory.isDirectory()) {
		    	MessageDialog.openError(null, "Erreur", "Ne pas sauvegarder dans un fichier : \"" + localDirectory + "\"");
		    	return;
		    }
		    
		    //The action to be done (download file)
			try {
				Object selection = actionGroup.getTableSelection();
				FileStatus fileToDownload = (selection == null?
						(FileStatus) actionGroup.getTreeSelection():
						(FileStatus) actionGroup.getTableSelection()
						);
				dfm.downloadFileFromHDFS(MyHDFSClient.getInstance().getFs(), localDirectory.toString(), fileToDownload.getPath().toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
	    }
	}
}
