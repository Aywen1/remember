package fr.nicolas.remember;

import java.awt.Desktop;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JTextArea;

import fr.nicolas.remember.components.NBackground;
import fr.nicolas.remember.components.NLabel;

public class NewFileFrame extends JFrame implements KeyListener {

	private NBackground bg;
	private NLabel labelTitle;
	private JTextArea textAreaTitle;
	private String category;
	private NLabel labelName;

	public NewFileFrame(Point loc, String category, NLabel labelName) {
		this.category = category;
		this.labelName = labelName;

		this.setTitle("Remember - Nouvelle fiche");
		this.setSize(440, 95);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setIconImage(new ImageIcon("res/icon.png").getImage());
		this.setLocation(loc);

		init();

		this.setVisible(true);

		textAreaTitle.requestFocus(true);
	}

	private void init() {
		bg = new NBackground();

		labelTitle = new NLabel("Titre de la fiche:", 14, true);
		textAreaTitle = new JTextArea();
		textAreaTitle.setFont(new Font("Calibri", Font.PLAIN, 20));

		this.setContentPane(bg);

		bg.add(labelTitle);
		bg.add(textAreaTitle);

		labelTitle.setBounds(4, 2, 382, 20);
		textAreaTitle.setBounds(4, 30, 426, 26);

		textAreaTitle.addKeyListener(this);
	}

	// KeyListener

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			if (textAreaTitle.getText() != null && textAreaTitle.getText() != "") {
				try {
					Files.copy(Paths.get("Remember/Modele.odt"),
							Paths.get("Remember/" + category + "/_En cours/" + textAreaTitle.getText() + ".odt"));
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}

			try {
				Desktop.getDesktop()
						.open(new File("Remember/" + category + "/_En cours/" + textAreaTitle.getText() + ".odt"));
			} catch (IOException e2) {
				e2.printStackTrace();
			}

			labelName.setText(textAreaTitle.getText());

			this.dispose();
		}
	}

	public void keyReleased(KeyEvent e) {
	}

	public void keyTyped(KeyEvent e) {
	}

}
