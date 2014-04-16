package org.esiag.isidis.dast.hdfs.ui;

import org.eclipse.jface.action.Action;
/**
 * Refresh table and tree of HDFSExplorer
 * 
 * @author Viet Dung NGUYEN
 *
 */
public class RefreshAction extends Action {
	private MyActionGroup actionGroup;
	
	public RefreshAction(MyActionGroup actionGroup) {
		this.actionGroup = actionGroup;
		setText("Rafraîchir");
	}
	
	public void run() {
		actionGroup.refresh();
	}
}