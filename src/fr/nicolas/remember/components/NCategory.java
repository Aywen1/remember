package fr.nicolas.remember.components;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import fr.nicolas.remember.MainFrame;
import fr.nicolas.remember.NewFileFrame;

public class NCategory extends JPanel {

	private NLabel labelName, labelTitle;
	private BufferedImage icon;
	private NButton buttonDate, buttonArchive;
	private Rectangle category;
	private boolean isHovered = false;
	private MainFrame mainFrame;

	public NCategory(String name, int y, boolean isToday, MainFrame mainFrame) {
		this.mainFrame = mainFrame;

		category = new Rectangle(0, y + 26, 440, 40);
		this.setLayout(null);

		File file = new File("Remember/" + name + "/_En cours");
		String[] listFiles = file.list();
		if (listFiles != null && listFiles.length != 0) {
			labelTitle = new NLabel(listFiles[0].replace(".odt", ""), 15, true);
		} else {
			labelTitle = new NLabel("Aucun chapitre commencé ...", 15, true);
		}

		labelName = new NLabel(name, 18);
		this.add(labelName);
		labelName.setBounds(40, 0, 200, 23);

		this.add(labelTitle);
		labelTitle.setBounds(40, 22, 400, 15);

		try {
			icon = ImageIO.read(new File("res/icons/" + name + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (isToday) {
			buttonDate = new NButton("Date", new Rectangle(418, y + 28, 16, 14));
			this.add(buttonDate);
			buttonDate.setBounds(415, 1, 16, 16);
		} else {
			buttonDate = new NButton("Date", new Rectangle(0, 0, 0, 0));
		}

		buttonArchive = new NButton("Archive", new Rectangle(418, y + 50, 16, 14));
		this.add(buttonArchive);
		buttonArchive.setBounds(415, 23, 16, 16);
	}

	public void mouseClick(Point mouseLocation, boolean leftClick) {
		if (leftClick) {
			if (category.contains(mouseLocation)) {
				if (buttonDate.mouseClick(mouseLocation)) {
					this.remove(buttonDate);
				} else if (buttonArchive.mouseClick(mouseLocation)) {
					if (labelTitle.getText() != "Aucun chapitre commencé ...") {
						try {
							Files.copy(
									Paths.get("Remember/" + labelName.getText() + "/_En cours/" + labelTitle.getText()
											+ ".odt"),
									Paths.get("Remember/" + labelName.getText() + "/" + labelTitle.getText() + ".odt"));
						} catch (IOException e1) {
							e1.printStackTrace();
						}

						new File("Remember/" + labelName.getText() + "/_En cours/" + labelTitle.getText() + ".odt")
								.delete();

						labelTitle.setText("Aucun chapitre commencé ...");

						repaint();
					}
				} else {
					if (labelTitle.getText() != "Aucun chapitre commencé ...") {

						try {
							Desktop.getDesktop().open(new File(
									"Remember/" + labelName.getText() + "/_En cours/" + labelTitle.getText() + ".odt"));
						} catch (IOException e) {
							e.printStackTrace();
						}
						
					} else {
						if (new File("Remember/Modele.odt").exists()) {
							new NewFileFrame(
									new Point(mainFrame.getLocation().x,
											mainFrame.getLocation().y + mainFrame.getHeight() / 4),
									labelName.getText(), labelTitle);
						}
					}
				}
			}
		} else {
			if (category.contains(mouseLocation)) {
				try {
					Desktop.getDesktop().browse(new File("Remember/" + labelName.getText()).toURI());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void mouseMove(Point mouseLocation) {
		if (category.contains(mouseLocation)) {
			isHovered = true;
		} else {
			isHovered = false;
		}

		if (buttonDate != null) {
			buttonDate.mouseMove(mouseLocation);
		}
		buttonArchive.mouseMove(mouseLocation);
	}

	public void paintComponent(Graphics g) {
		g.setColor(new Color(17, 17, 17));
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		g.drawImage(icon, 6, 7, 25, 25, null);

		if (isHovered) {
			g.setColor(new Color(100, 100, 100));
			g.drawLine(0, 0, 0, getWidth());
		}
	}

}
