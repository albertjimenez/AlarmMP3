package main;

import javax.swing.SwingUtilities;

import view.View;

public class Principal {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				new View().createGUI();

			}
		});

	}

}
