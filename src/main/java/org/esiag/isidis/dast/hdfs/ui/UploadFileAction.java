package org.esiag.isidis.dast.hdfs.ui;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.RemoteIterator;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.esiag.isidis.dast.hdfs.api.IDistributedFileManager;
import org.esiag.isidis.dast.hdfs.api.ISmallFileManager;
import org.esiag.isidis.dast.hdfs.remoteIterator.RemoteIteratorAbstract;
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
	private long fileSize = 0;
	
	public UploadFileAction(MyActionGroup actionGroup,IDistributedFileManager dfm) {
		this.actionGroup = actionGroup;
		this.dfm = dfm;
		setText("Uploader un fichier");
	}
	
	public void run() {
		
		//Open a windows to chose file to upload
	    FileDialog dialog =
	        new FileDialog(Display.getCurrent().getActiveShell(), SWT.OPEN
	            | SWT.MULTI);
	    
	    dialog.setText("Chosissez un fichier");
	    dialog.open();
	    
	    List<File> files = new ArrayList<File>();
	    
	    for (String fname : dialog.getFileNames())
	      files.add(new File(dialog.getFilterPath() + File.separator + fname));
	    
	    for (File localFile : files) {
              Object selection = actionGroup.getTableSelection();
			
			
			FileStatus fileOnHDFS = (selection == null?
					(FileStatus) actionGroup.getTreeSelection()
					:(FileStatus) actionGroup.getTableSelection());
  
			fileSize = localFile.length();
			if (fileSize <= IDistributedFileManager.MAXI_SMALL_FILE){
				ISmallFileManager smallDFM = dfm.dealWithSmallFile();
				smallDFM.uploadSmallFileToHDFS(localFile.toString(),fileOnHDFS.getPath().toString());
			}else{
				
				// Deal with big file
				UploadFileActionThread t = new UploadFileActionThread(localFile, fileOnHDFS);
				t.start();
			}
        }
	    actionGroup.refresh();
	}
	
	
	public class UploadFileActionThread extends Thread{
		private RemoteIterator<Void> remoteIterator = null;
		private String fileName;
		private String folderName;
		
		private long numberOfBlocks = 0;
		private long actualPosition = 0;
		private long percent = 0;
		
		public UploadFileActionThread(File localFile, FileStatus fileOnHDFS){
			fileName = localFile.getName();
			folderName = fileOnHDFS.getPath().toString();
			String fileDest = fileOnHDFS.getPath().toString() + "/" + localFile.getName();
			remoteIterator = dfm.dealWithBigFile().writeFile(localFile, fileDest);
			numberOfBlocks =((RemoteIteratorAbstract<Void>) remoteIterator).getNumberOfBlocks();
		}
		
		@Override
		public void run(){
			try {
				ProgressBarHDFSSwing progressBar = new ProgressBarHDFSSwing("Téléchargement","<html>Téléchargement du fichier<br>"+fileName+" vers "+folderName + "<html>");
				progressBar.showProgressBar();
				
				//write 10kb each until the end of file
				while(remoteIterator.hasNext()){
					remoteIterator.next();
					percent = actualPosition * 100 / numberOfBlocks;
					progressBar.setPercentage((int) percent);
					actualPosition++;
				}
				
				progressBar.finish("Le fichier " + fileName + " a été téléchargé.");
			} catch (IOException e) {		
				e.printStackTrace();
			}
		}
	}
}