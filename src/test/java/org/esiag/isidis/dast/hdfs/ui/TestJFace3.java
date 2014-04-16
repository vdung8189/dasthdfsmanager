package org.esiag.isidis.dast.hdfs.ui;
import org.eclipse.jface.dialogs.*;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.events.*;

public class TestJFace3 extends ApplicationWindow{
	 public TestJFace3() {
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
		        boolean reponse = false;
		        MessageDialog.openInformation(shell, "Information", "Le message d'information");
		        MessageDialog.openWarning(shell, "Avertissement", "Le message d'avertissement");
		        MessageDialog.openError(shell, "Erreur", 
		          "Mon message d'erreur\n\nSeconde ligne du message");
		        reponse = MessageDialog.openConfirm(shell, "Confirmation", 
				    "Le message de la confirmation");
		        System.out.println("reponse a la confirmation = " + reponse);
		        reponse = MessageDialog.openQuestion(shell, "question", 
				    "Le message de la question");
		        System.out.println("reponse a la question = " + reponse);
		      }
		    });
		    return boutonAfficher;
		  }

		  public static void main(String[] args) {
		    new TestJFace3().run();
		  }
}
