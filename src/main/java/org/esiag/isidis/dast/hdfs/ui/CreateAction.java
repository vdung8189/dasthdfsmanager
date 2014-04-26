package org.esiag.isidis.dast.hdfs.ui;

import java.io.IOException;

import org.apache.hadoop.fs.FileStatus;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.swt.widgets.Display;
import org.esiag.isidis.dast.hdfs.api.IDistributedFileManager;
import org.esiag.isidis.dast.hdfs.utils.MyHDFSClient;
//test
/**
 * Create a directory
 * @author NGUYEN Viet Dung
 * @category ActionHandler
 */
public class CreateAction extends Action {
	private MyActionGroup actionGroup;
	private IDistributedFileManager dfm;
	
	public CreateAction(MyActionGroup actionGroup,IDistributedFileManager dfm) {
		this.actionGroup = actionGroup;
		this.dfm = dfm;
		setText("Créer un dossier");
	}
	
	public void run() {
		Object selection = actionGroup.getTableSelection();
		FileStatus f = (selection == null?
				(FileStatus) actionGroup.getTreeSelection():
				(FileStatus) actionGroup.getTableSelection());
		InputDialog dialog =
		          new InputDialog(Display.getCurrent().getActiveShell(),
		              "Créer un dossier",
		              "Saissisez le nom du dossier",
		              "",
		              null);
		if (dialog.open() == InputDialog.OK) {
			try {
				dfm.mkdirs(f.getPath().toString()+"/"+dialog.getValue());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		actionGroup.refresh();
	}
}