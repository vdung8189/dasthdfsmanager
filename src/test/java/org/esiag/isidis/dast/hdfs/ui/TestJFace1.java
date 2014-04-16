package org.esiag.isidis.dast.hdfs.ui;

import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;

/*
 * Test JFace & SWT Maven component
 */
public class TestJFace1 extends ApplicationWindow {

	public TestJFace1(){
		super(null);
	}
	
	public void run(){
		/*
		 *  Etape 1 : Appel à la méthode setBlockOnOpen(true);
		 *  
		 *  Boolean permet de préciser si la méthode doit utiliser ou non la boucle de traitement
		 *  des évènements
		 */
		setBlockOnOpen(true);
		/*
		 * Etape 2 : appel à la méthode open()
		 *  
		 *  Assurer l'initialisation et le traitement des évènements
		 */
		open();
		/*
		 * Etape 3 : libération des ressources de l'objet Display courant est nécesaire
		 * car elle n'est pas réalisé pour la méthode open
		 */
		
		Display.getCurrent().dispose();

	}
	
	protected Control createContents(Composite parent){
		Label label = new Label(parent, SWT.CENTER);
		label.setText("Bonjour");	
		return (label);
		
	}
	
	public static void main(String[] args) {
		new TestJFace1().run(); 
	}
	


}
