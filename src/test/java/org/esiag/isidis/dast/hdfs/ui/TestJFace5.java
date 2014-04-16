package org.esiag.isidis.dast.hdfs.ui;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class TestJFace5 extends ApplicationWindow {

  public TestJFace5() {
    super(null);
  }

  public void run() {
    setBlockOnOpen(true);
    open();
    Display.getCurrent().dispose();
  }

  protected Control createContents(Composite parent) {

    Composite composite = new Composite(parent, SWT.NONE);
    composite.setLayout(new RowLayout(SWT.VERTICAL));

    Button boutonExecuterD = new Button(composite, SWT.PUSH);
    boutonExecuterD.setText("Exécuter déterminé");
    final Shell shell = parent.getShell();

    boutonExecuterD.addSelectionListener(new SelectionAdapter() {
      public void widgetSelected(SelectionEvent event) {
        try {
          new ProgressMonitorDialog(shell).run(true, true, new MonTraitement());
        } catch (InvocationTargetException e) {
          MessageDialog.openError(shell, "Erreur", e.getMessage());
        } catch (InterruptedException e) {
          MessageDialog.openInformation(shell, "Interruption", e.getMessage());
        }
      }
    });

    Button boutonExecuterU = new Button(composite, SWT.PUSH);
    boutonExecuterU.setText("Exécuter indéterminé");

    boutonExecuterU.addSelectionListener(new SelectionAdapter() {
      public void widgetSelected(SelectionEvent event) {
        try {
          new ProgressMonitorDialog(shell).run(true, true, new MonTraitementInconnu());
        } catch (InvocationTargetException e) {
          MessageDialog.openError(shell, "Erreur", e.getMessage());
        } catch (InterruptedException e) {
          MessageDialog.openInformation(shell, "Interruption", e.getMessage());
        }
      }
    });

    return composite;
  }

  public static void main(String[] args) {
    new TestJFace5().run();
  }
}