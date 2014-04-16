package org.esiag.isidis.dast.hdfs.ui;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;

public class MonTraitementInconnu implements IRunnableWithProgress {

  private static final int NB_ITERATION = 100;

  public void run(IProgressMonitor monitor) throws InvocationTargetException, 
                                                   InterruptedException {
    monitor.beginTask("Lancement des traitements", IProgressMonitor.UNKNOWN);
    for (int nb = 0; nb < NB_ITERATION && !monitor.isCanceled(); nb++) {
      Thread.sleep(100);
    }
    monitor.done();
    if (monitor.isCanceled())
      throw new InterruptedException("Les traitements ont été interrompus");
  }
}