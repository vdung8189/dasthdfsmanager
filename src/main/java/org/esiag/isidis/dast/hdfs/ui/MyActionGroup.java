package org.esiag.isidis.dast.hdfs.ui;


import org.apache.hadoop.fs.FileStatus;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.actions.ActionGroup;


import org.esiag.isidis.dast.hdfs.api.IDistributedFileManager;
//import org.esiag.isidis.dast.hdfs.utils.ConfigurationReader;

/**
 * The Class aims to handle all the action of the GUI.
 * @author NGUYEN Viet Dung
 * @category EventHandler
 */
public class MyActionGroup extends ActionGroup {
	private TreeViewer treeViewer;
	private TableViewer table;
	private IDistributedFileManager dfm;

	/**
	 * Constructor of the group that is called by the constructor of the GUI HDFSExplorer
	 * @param treeViewer	The main tree of the program that is even used in the GUI HDFSExplorer.
	 * @param tableViewer	The table that shows all the files in the selected node of the tree.
	 */
	public MyActionGroup(TreeViewer treeViewer,TableViewer tableViewer, IDistributedFileManager dfm) {
		this.treeViewer = treeViewer;
		this.table=tableViewer;
		this.dfm = dfm;
	}
	
	public void refresh()
	{
		treeViewer.refresh();
		table.refresh();
	}
	
	/*public ConfigurationReader getConf()
	{	return conf;
	}
	*/
	/**
	 * <p>The method creates the Event Handle for the MyActionGroup.</p>
	 * At first, the method adds 6 menu Items for the menu that is used for both the tree and the table.
	 */
	public void fillContextMenu(IMenuManager mgr) {
		//Create the menu and add menu items.
		MenuManager menuManager = (MenuManager) mgr;
			menuManager.add(new DownloadAction(this, dfm));
			menuManager.add(new CreateAction(this,dfm));
			menuManager.add(new UploadFileAction(this,dfm));
			//menuManager.add(new UploadFolderAction(this,dfm));
			menuManager.add(new RefreshAction(this));
			menuManager.add(new DeleteAction(this, dfm));
	
		//Add the menu to the tree.
			Tree tree = treeViewer.getTree();
			Menu treeMenu = menuManager.createContextMenu(tree);
			tree.setMenu(treeMenu);
	
		//Add the menu to the table.
			Table tab = table.getTable();
			Menu tableMenu = menuManager.createContextMenu(tab);
			tab.setMenu(tableMenu);
		
		//Set action listener to the tree and the table.
			//Listen to select action to the tree.
				treeViewer.addSelectionChangedListener(new SelectAction());
			//Listen to double click action to the table.
				table.addDoubleClickListener(new SelectAction());
		}
	
	/**
	 * Set action listener to the interface.
	 * The action can be launched is :
	 * - double click on the table
	 * - select node in the tree.
	 * @author NGUYEN Viet Dung
	 *
	 */
	private class SelectAction implements ISelectionChangedListener, IDoubleClickListener{
		public void selectionChanged(SelectionChangedEvent event) {
			table.setInput(getTreeSelection());
		}

		public void doubleClick(DoubleClickEvent event) {
			Object selection = getTableSelection();
			if (selection == null)
				return;
			FileStatus file = (FileStatus) selection;
			if (file.isDirectory()) {
				table.setInput(selection);
			}
		}
	}
	
	/**
	 * The class handles Download Action.
	 * @author NGUYEN Viet Dung
	 * @category ActionHandler
	 */
	
	
	
	public class NewAction extends Action {

		public NewAction( ){
			super();
		    setText("Nouveau");
		    this.setAccelerator( SWT.ALT + SWT.SHIFT + 'N');
		    setToolTipText("");
		    setImageDescriptor(ImageDescriptor.createFromFile(NewAction.class,"icons\\new.gif"));
		}
	}
	
	public Object getTreeSelection() {
		IStructuredSelection selection = (IStructuredSelection) treeViewer
				.getSelection();
		if (selection.size() != 1)
			return null;
		return selection.getFirstElement();
	}
	
	public Object getTableSelection() {
		IStructuredSelection selection = (IStructuredSelection) table
				.getSelection();
		if (selection.size() != 1)
			return null;
		return selection.getFirstElement();
	}
}
