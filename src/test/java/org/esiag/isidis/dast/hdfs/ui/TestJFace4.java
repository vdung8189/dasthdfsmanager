package org.esiag.isidis.dast.hdfs.ui;

import org.eclipse.jface.dialogs.*;
import org.eclipse.jface.window.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.events.*;

public class TestJFace4 extends ApplicationWindow {

  public TestJFace4() {
    super(null);
  }

  public void run() {
    setBlockOnOpen(true);
    open();
    Display.getCurrent().dispose();
  }

  protected Control createContents(Composite parent) {

    Button boutonAfficher = new Button(parent, SWT.PUSH);
    boutonAfficher.setText("Afficher");
    final Shell shell = parent.getShell();

    boutonAfficher.addSelectionListener(new SelectionAdapter() {
      public void widgetSelected(SelectionEvent event) {
        int reponse = 0;

        InputDialog inputDialog = new InputDialog(Display.getCurrent().getActiveShell(), 
		   "Titre de la boite de dialogue", 
		   "Saisissez la valeur", "test", null);
        reponse = inputDialog.open();

        if (reponse == Window.OK) {
          System.out.println("Valeur saisie = " + inputDialog.getValue());
        } else {
          System.out.println("Operation annulée");
        }
      }
    });
    return boutonAfficher;
  }

  public static void main(String[] args) {
    new TestJFace4().run();
  }
}