package org.esiag.isidis.dast.hdfs.ui;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;

public class TestJFace2 extends ApplicationWindow{

	public TestJFace2(){
		super(null);
	}
	
	
	public void run(){
		
		this.setBlockOnOpen(true);
		this.open();
		Display.getCurrent().dispose();
	}
	
	protected Control createContents(Composite parent){
		Button boutonAfficher = new Button(parent, SWT.PUSH);
		boutonAfficher.setText("Afficher");
		boutonAfficher.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event){
				Status status = new Status(IStatus.ERROR, "plugin", 0, "Raison de l'erreur", null);
				ErrorDialog.openError(Display.getCurrent().getActiveShell(), "Erreur", "Mon message d'erreur", status);
			}
		});
		
		return boutonAfficher;
		
	}
	
	public static void main(String[] args) {
	
		new TestJFace2().run();
	}

}
