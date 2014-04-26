package org.esiag.isidis.dast.hdfs.ui;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.*;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.*;
import org.eclipse.jface.viewers.Viewer;
import org.esiag.isidis.dast.hdfs.api.IDistributedFileManager;
import org.esiag.isidis.dast.hdfs.apiImplentations.DistributedFileManager;
import org.esiag.isidis.dast.hdfs.log.Log;
import org.esiag.isidis.dast.hdfs.utils.MyHDFSClient;

/**
 * The graphic interface allows the user to interact with the HDFS.
 * 
 * @author NGUYEN Viet Dung
 * @category GUI
 */
public class HDFSExplorer extends ApplicationWindow {
	private SashForm sash = null ;
	private TreeViewer tree = null;
	private TableViewer table = null;
	private MyActionGroup actionGroup = null ;
	private IDistributedFileManager dfm = null; 
	private FileSystem fs = null;
	private ProgressBar progressBar = null; 

	public HDFSExplorer() {
		super(null);
		addMenuBar();
		Log.init("configuration/log4j.properties", "log/log.txt");
	}
	
	/**
	 * Create the GUI (like as InitializeComponents of Swing)
	 */
	protected Control createContents(Composite parent) {
		//Set title for the main windows
		getShell().setText("[DAST v2.0]  Interface Hadoop Manager");
		// set default size
		getShell().setSize(965, 684);
		getShell().setMaximized(false);
		// used to make the UI resizable
		sash = new SashForm(parent, SWT.SMOOTH);
		sash.setLayoutData(new GridData(GridData.FILL_BOTH));
		tree = new TreeViewer(sash);
		table = new TableViewer(sash);
		progressBar = new ProgressBar(sash, SWT.VERTICAL);
		progressBar.setMinimum(0);
		progressBar.setMaximum(100);
		sash.setWeights(new int[] { 25, 73, 2});
		
		init();
		
		return parent;
	}


	public void init() {
		// Pass tree and table to actionGroup. 
		//This will set listener to actions done within the UI
		dfm = new DistributedFileManager();
		
		fs = MyHDFSClient.getInstance().getFileSystem();
		actionGroup = new MyActionGroup(tree,table,dfm);
		actionGroup.fillContextMenu(new MenuManager());
	
		//Set data to the tree
		tree.setContentProvider(new FileTreeContentProvider());
		//Set label to the free
		tree.setLabelProvider(new FileTreeLabelProvider());
		//Set path root of HDFS
		Path root = new Path("/");
		tree.setInput(root);
		//Set nom for each Table Column
		new TableColumn(table.getTable(), SWT.LEFT).setText("Nom");
		new TableColumn(table.getTable(), SWT.LEFT).setText("Type");
		new TableColumn(table.getTable(), SWT.LEFT).setText("Taille");
		new TableColumn(table.getTable(), SWT.LEFT).setText("Date de modification");
		
		// Set default width for column
		for (int i = 0; i < table.getTable().getColumnCount(); i++) {
			table.getTable().getColumn(i).setWidth(164);
		}
		
		table.getTable().setHeaderVisible(true);
		table.getTable().setLinesVisible(true);
		table.setContentProvider(new FileTableContentProvider());
		table.setLabelProvider(new FileTableLabelProvider());
		table.setSorter(new FileSorter());
	}
	
	public void setPercentage(final int value) {
		Display.getDefault().asyncExec(new Runnable() {
		    public void run() {
				progressBar.setSelection(value);
		    }
		});
		//progressBar.setSelection(value);
	}
	
	public void showMessage(final String title, final String message) {
		Display.getDefault().asyncExec(new Runnable() {
		    public void run() {
				MessageDialog.openInformation(getShell(), title, message);
		    }
		});
	}

	/**
	 * The class is in charge of loading content for the tree. For exemple : Get the selected node, get all the childs, get Parent.
	 * 
	 * @author NGUYEN Viet Dung
	 * @category TreeHandler
	 */
	private class FileTreeContentProvider implements ITreeContentProvider {
		
		public Object[] getChildren(Object element)  {
			FileStatus f=(FileStatus)element;
			Path path=new Path(f.getPath().toString());
			return getElements(path);
		}

		public Object[] getElements(Object element) {
			Path path = (Path) element;
			try {
				FileStatus[] fList = fs.listStatus(path);
				List<FileStatus> rootFolders = new ArrayList<FileStatus>();
				for (FileStatus f : fList) {  
	                if (null != f) {  
	                	if (f.isDirectory()) { 
	                		rootFolders.add(f);
	                	}
	                }
				}
				return rootFolders.toArray();
				}catch (IOException e) {
			
					e.printStackTrace();
				}
			return null;
		}

		public boolean hasChildren(Object element) {
			Object[] obj = getChildren(element);
			return obj == null ? false : obj.length > 0;
		}

		public Object getParent(Object element) {
			return null;
		}

		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}

		@Override
		public void dispose() {
		}
	}

	/**
	 * This class in charge of providing label for the directory arborescence
	 *  
	 * @author Viet Dung NGUYEN
	 * @category TreeHandler
	 */
	private class FileTreeLabelProvider implements ILabelProvider {
		public String getText(Object element) {
			return ((FileStatus) element).getPath().getName();
		}

		public void addListener(ILabelProviderListener listener) {
		}


		public boolean isLabelProperty(Object element, String property) {
			return false;
		}

		public void removeListener(ILabelProviderListener listener) {
		}

		@Override
		public Image getImage(Object arg0) {
			return null;
		}
		
		@Override
		public void dispose() {
			
		}
	}

	/**
	 * This class is in charge of providing content for table
	 * 
	 * @author Viet Dung NGUYEN
	 * 
	 * @category TableHandler
	 */
	private class FileTableContentProvider implements IStructuredContentProvider {

		public void dispose() {
		}

		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}

		public Object[] getElements(Object inputElement) {
			FileStatus f = (FileStatus) inputElement;
			Path path=new Path(f.getPath().toString());
			try {
				FileStatus[] fList = fs.listStatus(path);
							return fList;
				}catch (IOException e) {
				
					e.printStackTrace();
				}
			return null;
		}

	}

	/**
	 * This class is in charge of providing label for table
	 * 
	 * @author Viet Dung NGUYEN
	 * @category TableHandler
	 */
	private class FileTableLabelProvider implements ITableLabelProvider {

		public String getColumnText(Object element, int columnIndex) {
			FileStatus f = (FileStatus) element;
			if (columnIndex == 0)
				return f.getPath().getName();
			else if (columnIndex == 1) {
			
				if (f.isDirectory())
					return "répertoire";
				else
					return "fichier";
			} else if (columnIndex == 2) {
				if (f.isDirectory())
					return "";
				else
					return dfm.convertSize(f.getLen());
			} else if (columnIndex == 3) {
				// French format for date
				return new SimpleDateFormat("dd-MM-yyyy HH:mm").format(new Date(f.getModificationTime()));
			}
			return null;
		}

		public void addListener(ILabelProviderListener listener) {
			
		}

		public void dispose() {
			
		}

		public boolean isLabelProperty(Object element, String property) {
			return false;
		}

		public void removeListener(ILabelProviderListener listener) {
			
		}

		@Override
		public Image getColumnImage(Object arg0, int arg1) {
			return null;
		}
	}

	/**
	 * File Sorter aims to sort node of the file.
	 * 
	 * @author NGUYEN Viet Dung
	 */
	private class FileSorter extends ViewerSorter {
		public int category(Object element) {
			return ( ( (FileStatus) element).isDirectory() ) ? 0 : 1;
		}
	}
}
