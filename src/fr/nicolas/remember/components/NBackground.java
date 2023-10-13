package fr.nicolas.remember.components;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class NBackground extends JPanel {

	public NBackground() {
		this.setLayout(null);
	}

	public void paintComponent(Graphics g) {
		g.setColor(new Color(25, 25, 25));
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		g.setColor(new Color(38, 38, 38));
		g.fillRect(0, 0, this.getWidth(), 22);
	}

}
