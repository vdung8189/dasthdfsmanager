package org.esiag.isidis.dast.hdfs.ui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.RemoteIterator;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;
import org.esiag.isidis.dast.hdfs.api.IBigFileManager;
import org.esiag.isidis.dast.hdfs.api.IDistributedFileManager;
import org.esiag.isidis.dast.hdfs.api.ISmallFileManager;
import org.esiag.isidis.dast.hdfs.remoteIterator.RemoteIteratorAbstract;


/**
 * Download to local file system
 * @author Viet Dung NGUYEN
 *
 */
public class DownloadAction extends Action {
	private MyActionGroup actionGroup = null;
	private IDistributedFileManager dfm = null;
	private File localDirectory = null;
	private Shell activeShell = null;
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
		activeShell = Display.getCurrent().getActiveShell();
		//Show the secondary dialog (UI) allows user to choose the folder to put the downloaded file.
		DirectoryDialog dialog = new DirectoryDialog(activeShell);
		    dialog.setText("Choisir un dossier");
		    
		   String directory = dialog.open();
		    
		//If user clicks Cancel
	    if (directory == null) {
	        return;
	    } else {
	    	//Get the path
		    localDirectory = new File(directory);
		    
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
						//if selection is null then either
						(FileStatus) actionGroup.getTreeSelection():
						//or
						(FileStatus) actionGroup.getTableSelection()
						);
				
				if (fileToDownload.getLen() <= IDistributedFileManager.MAXI_SMALL_FILE){
					//Small file 
					ISmallFileManager smallDFM =  dfm.dealWithSmallFile();			
					smallDFM.downloadSmallFileFromHDFS(fileToDownload.getPath().toString(), localDirectory.toString());
					
				} else{
					ThreadReadFile t = new ThreadReadFile(fileToDownload);
					t.start();
				}
				
					
			} catch (Exception e) {
				e.printStackTrace();
			}
	    }
	}
	
	public class ThreadReadFile extends Thread{
		private RemoteIterator<byte[]> remoteIterator = null;
		private FileOutputStream fos = null;
		private String fileName;

		private long numberOfBlocks = 0;
		private long actualPosition = 0;
		private long percent = 0;
		
		public ThreadReadFile(FileStatus fileToDownload){	
			fileName = fileToDownload.getPath().getName();
			IBigFileManager bigDFM = dfm.dealWithBigFile();
			remoteIterator = bigDFM.readFile(fileToDownload.getPath().toString());
			numberOfBlocks =((RemoteIteratorAbstract<byte[]>) remoteIterator).getNumberOfBlocks();
		}
			
		@Override
		public void run() {
			//TODO progress bar
			try {	
				ProgressBarHDFSSwing progressBar = new ProgressBarHDFSSwing("Téléchargement","Téléchargement du fichier "+fileName);
				progressBar.showProgressBar();
				//actionGroup.getExplorer().setPercentage(0);
				//write 10kb each until the end of file
				fos = new FileOutputStream(new File(localDirectory.getAbsolutePath() + "/" + fileName));
				while(remoteIterator.hasNext()){
					fos.write(remoteIterator.next());
					percent = actualPosition * 100 / numberOfBlocks;
					progressBar.setPercentage((int) percent);
					actualPosition++;
				}
				fos.close();
				progressBar.finish("Le fichier " + fileName + " a été téléchargé.");
				//actionGroup.getExplorer().showMessage("Téléchargement","Le fichier " + fileName + " a été téléchargé." );
			} catch (IOException e) {		
				e.printStackTrace();
			}	
		}

	
	}
}
