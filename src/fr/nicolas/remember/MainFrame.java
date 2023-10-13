package fr.nicolas.remember;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import fr.nicolas.remember.components.NBackground;
import fr.nicolas.remember.components.NCategory;
import fr.nicolas.remember.components.NLabel;

public class MainFrame extends JFrame implements MouseListener, MouseMotionListener {

	private NBackground bg;
	private NLabel labelDate;
	private String[] categories = new String[] { "Histoire - Géo", "Philosophie", "Spé SVT", "Anglais", "Philosophie",
			"SVT", "Physique Chimie", "Anglais EURO", "Espagnol", "Mathématiques" };
	private String[] edtLundi = new String[] { "Anglais", "Histoire - Géo", "Physique Chimie", "SVT", "Espagnol" };
	private String[] edtMardi = new String[] { "Spé SVT", "Histoire - Géo", "Mathématiques", "Physique Chimie", "SVT" };
	private String[] edtMercredi = new String[] { "Mathématiques", "Philosophie", "Anglais EURO" };
	private String[] edtJeudi = new String[] { "Mathématiques", "Physique Chimie" };
	private String[] edtVendredi = new String[] { "Anglais EURO", "Anglais", "Espagnol", "Histoire - Géo",
			"Mathématiques", "Philosophie" };
	private int y = 30;
	private Point mouseLocation = new Point(0, 0);
	private ArrayList<NCategory> categoryList = new ArrayList<NCategory>();
	private Timer timer;

	public MainFrame() {
		String path = "Remember/";
		if (!(new File(path).exists())) {
			new File(path).mkdirs();

			for (int i = 0; i < categories.length; i++) {
				new File(path + categories[i]).mkdirs();
				new File(path + categories[i] + "/_En cours").mkdirs();
			}
		}

		this.setTitle("Remember");
		this.setSize(440, 529);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setIconImage(new ImageIcon("res/icon.png").getImage());

		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.setFocusable(true);

		init();
	}

	class clearTitleTask extends TimerTask {
		public void run() {
			String date = SimpleDateFormat.getDateInstance(SimpleDateFormat.FULL).format(new Date());
			char charDate[] = date.toCharArray();
			charDate[0] = Character.toUpperCase(charDate[0]);
			date = new String(charDate);

			labelDate.setText(date);

			timer.cancel();
		}
	}

	private void init() {
		bg = new NBackground();

		String date = SimpleDateFormat.getDateInstance(SimpleDateFormat.FULL).format(new Date());
		char charDate[] = date.toCharArray();
		charDate[0] = Character.toUpperCase(charDate[0]);
		date = new String(charDate);

		labelDate = new NLabel(date, 14, true);

		this.setContentPane(bg);

		bg.add(labelDate);

		bg.setBounds(0, 0, this.getWidth(), this.getHeight());
		labelDate.setBounds(4, 2, 382, 20);

		this.setVisible(true);

		String[] edtToday;
		String day = SimpleDateFormat.getDateInstance(SimpleDateFormat.FULL).format(new Date());
		day = day.split(" ")[0];

		if (day.equals("lundi")) {
			edtToday = edtLundi;
		} else if (day.equals("mardi")) {
			edtToday = edtMardi;
		} else if (day.equals("mercredi")) {
			edtToday = edtMercredi;
		} else if (day.equals("jeudi")) {
			edtToday = edtJeudi;
		} else if (day.equals("vendredi")) {
			edtToday = edtVendredi;
		} else {
			edtToday = null;
		}

		if (edtToday != null) {
			for (int i = 0; i < edtToday.length; i++) {
				newCategory(edtToday[i], true);
			}
		}

		for (int i = 0; i < categories.length; i++) {
			if (edtToday != null) {
				int isNotEqual = 0;
				for (int o = 0; o < edtToday.length; o++) {
					if (edtToday[o] != categories[i]) {
						isNotEqual++;
					}
				}
				if (isNotEqual == edtToday.length) {
					newCategory(categories[i], false);
				}
			} else {
				newCategory(categories[i], false);
			}
		}

		repaint();

	}

	private void newCategory(String name, boolean isToday) {
		NCategory category = new NCategory(name, y, isToday, this);
		bg.add(category);
		category.setBounds(0, y, this.getWidth(), 40);
		categoryList.add(category);
		y += 47;
	}

	// MouseListener

	public void mouseClicked(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			for (int i = 0; i < categoryList.size(); i++) {
				categoryList.get(i).mouseClick(mouseLocation, true);
			}

		} else if (e.getButton() == MouseEvent.BUTTON3) {
			for (int i = 0; i < categoryList.size(); i++) {
				categoryList.get(i).mouseClick(mouseLocation, false);
			}
		}

		bg.repaint();
	}

	// MouseMotionListener

	public void mouseDragged(MouseEvent e) {
		this.mouseLocation = e.getPoint();

		for (int i = 0; i < categoryList.size(); i++) {
			categoryList.get(i).mouseMove(mouseLocation);
		}

		bg.repaint();
	}

	public void mouseMoved(MouseEvent e) {
		this.mouseLocation = e.getPoint();

		for (int i = 0; i < categoryList.size(); i++) {
			categoryList.get(i).mouseMove(mouseLocation);
		}

		bg.repaint();
	}

}
