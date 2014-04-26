package org.esiag.isidis.dast.hdfs.ui;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.RemoteIterator;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.esiag.isidis.dast.hdfs.api.IDistributedFileManager;
import org.esiag.isidis.dast.hdfs.api.ISmallFileManager;
import org.esiag.isidis.dast.hdfs.ui.UploadFileAction.UploadFileActionThread;
import org.esiag.isidis.dast.hdfs.utils.MyHDFSClient;
/**
 * Upload an folder to HDFS 
 * 
 * @author Viet Dung NGUYEN
 *
 */
public class UploadFolderAction extends Action {
	
	private RemoteIterator<Void> remoteIterator = null;
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
			    
			    for (File fileLocal : files) {
		              Object selection = actionGroup.getTableSelection();
         
					FileStatus f = (selection == null?
							(FileStatus) actionGroup.getTreeSelection():
								(FileStatus) actionGroup.getTableSelection());
					
				/*	long fileSize = f.getLen();

					if (fileSize <= IDistributedFileManager.MAXI_SMALL_FILE){
						ISmallFileManager smallDFM = dfm.dealWithSmallFile(); 
						smallDFM.uploadSmallFileToHDFS(fileLocal.toString(),f.getPath().toString());
					}else{*/
						//TODO Big file 
						String fileDest = f.getPath().toString() + "/" + fileLocal.getName();
						remoteIterator = dfm.dealWithBigFile().writeFile(fileLocal, fileDest);
						// Deal with big file
						UploadFolderActionThread t = new UploadFolderActionThread();
						t.start();
					//}
		         }
			 
		actionGroup.refresh();
	}
	
	public class UploadFolderActionThread extends Thread{
		public UploadFolderActionThread(){
			
		}
		
		@Override
		public void run(){
			try {
				while(remoteIterator.hasNext()){
					remoteIterator.next();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
}