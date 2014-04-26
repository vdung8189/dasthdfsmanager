package org.esiag.isidis.dast.hdfs.ui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import org.esiag.isidis.dast.hdfs.log.Log;

public class ProgressBarHDFSSwing extends JFrame {
	private JProgressBar progressBar = new JProgressBar();
	private JButton okButton = new JButton();
	private JLabel labelMessage;
	private String title;
	private String message;
	
	public ProgressBarHDFSSwing(String title, String message) {
		this.title = title;
		this.message = message;
		Log.init("configuration/log4j.properties", "log/log.txt");
		init();
	}
	
	private void init() {
		this.setTitle("[DAST v2.0] "+title);
		setPreferredSize(new Dimension(700, 150));
		setResizable(false);
		setLayout(new GridLayout(3,1));
		
		labelMessage = new JLabel(message);
		
		progressBar.setMinimum(0);
		progressBar.setMaximum(100);
		progressBar.setPreferredSize(new Dimension(650,30));
		
		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();
		JPanel panel3 = new JPanel();
		
		okButton.setText("OK");
		okButton.setEnabled(false);
		okButton.addActionListener(new ActionListener() { 
			  public void actionPerformed(ActionEvent e) {
				  getThis().close();
			  }
			  }
		);
		panel1.add(labelMessage);
		panel2.add(progressBar);
		panel3.add(okButton);
		
		getContentPane().add(panel1);
		getContentPane().add(panel2);
		getContentPane().add(panel3);
	}

	public ProgressBarHDFSSwing getThis() {
		return this;
	}
	
	public void close() {
		setVisible(false);
	}
	
	public void showProgressBar() {
		pack();
		setVisible(true);
	}
	
	public void setPercentage(int percentage) {
		progressBar.setValue(percentage);
	}
	
	public void finish(String message) {
		labelMessage.setText(message);
		progressBar.setValue(100);
		okButton.setEnabled(true);
	}
}
