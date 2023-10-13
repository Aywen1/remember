package fr.nicolas.remember.components;

import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class NButton extends JPanel {

	private BufferedImage icon, iconHovered;
	private Rectangle button;
	private boolean isHovered = false;

	public NButton(String iconName, Rectangle bounds) {
		button = bounds;
		try {
			icon = ImageIO.read(new File("res/icons/" + iconName + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (new File("res/icons/" + iconName + "Hovered.png").exists()) {
			try {
				iconHovered = ImageIO.read(new File("res/icons/" + iconName + "Hovered.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public boolean mouseClick(Point mouseLocation) {
		if (button.contains(mouseLocation)) {
			return true;
		} else {
			return false;
		}
	}

	public void mouseMove(Point mouseLocation) {
		if (button.contains(mouseLocation)) {
			if (iconHovered == null) {
				setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			} else {
				isHovered = true;
			}
		} else {
			if (iconHovered == null) {
				setCursor(Cursor.getDefaultCursor());
			} else {
				isHovered = false;
			}
		}
	}

	public void paintComponent(Graphics g) {
		if (isHovered) {
			g.drawImage(iconHovered, 0, 0, this.getWidth(), this.getHeight(), null);
		} else {
			g.drawImage(icon, 0, 0, this.getWidth(), this.getHeight(), null);
		}
	}

}
