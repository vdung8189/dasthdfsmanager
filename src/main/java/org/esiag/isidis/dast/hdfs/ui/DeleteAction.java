package org.esiag.isidis.dast.hdfs.ui;

import java.io.IOException;

import org.apache.hadoop.fs.FileStatus;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.esiag.isidis.dast.hdfs.api.IDistributedFileManager;
import org.esiag.isidis.dast.hdfs.utils.MyHDFSClient;

/**
 * Delete a file from HDFS.
 * @author NGUYEN Viet Dung
 * @category ActionHandler
 */
public class DeleteAction extends Action {
	private MyActionGroup actionGroup = null;
	private IDistributedFileManager dfm = null;
	private FileStatus fileToBeDeleted = null;
		/**
		 * Create the Action to 
		 */
		public DeleteAction(MyActionGroup actionGroup, IDistributedFileManager dfm) {
			setText("Supprimer");
			this.actionGroup = actionGroup;
			this.dfm = dfm;
		}
		
		/**
		 * Delete the file from HDFS.
		 */
		public void run() {
			//Create the message.
				StringBuffer msg = new StringBuffer();
			    	msg.append("Êtes vous sûr de la supression?\n ");
		    //Get the selected files to be deleted.
			    	Object selection = actionGroup.getTableSelection();
		    
			//Get the information of the selected file.
			fileToBeDeleted = (selection == null?
					(FileStatus) actionGroup.getTreeSelection():
					(FileStatus) actionGroup.getTableSelection()
				);
			
			//Finish the message
		    msg.append(fileToBeDeleted.getPath()).append("\n");
		    String messageTitle = "Suppression";
		    if (MessageDialog.openConfirm(null, messageTitle, msg.toString())) {
		    	//Delete the files from HDFS.
		    	//dfm.deleteHDFSFile(fileToBeDeleted.getPath().toString());
		    	ThreadDeleteFile t = new ThreadDeleteFile();
		    	t.start();
		    	
		    	actionGroup.refresh();
		    }
		}
		
		public class ThreadDeleteFile extends Thread{
			
			@Override
			public void run(){
				System.out.println("ThreadDeleteFile" + fileToBeDeleted.getPath().toString());
				try {
					dfm.deleteHDFSFile(fileToBeDeleted.getPath().toString());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}