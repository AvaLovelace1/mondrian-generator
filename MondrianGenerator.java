/*
 * ~ Mondrian Generator ~
 * by: Ava Pun
 * 
 * A program to do my art homework for me.
 * 
 * Generates abstract modern art in the style of Piet Mondrian, following these general rules:
 * 
 * Every piece consists of only two elements: lines and coloured panes.
 * The colours used are red, yellow, blue, grey, black, or white.
 * The lines are black, straight, and fairly thin.
 * The lines go vertical or horizontal.
 * A single vertical line and a single horizontal line run right across the canvas, edge to edge, intersecting somewhere.
 * The other lines either cross one another, or meet at T-junctions; there are no naked L-corners.
 * Each pane is completely and uniformly filled with a single colour or white (the same colour may fill adjacent panes).
 * Colours can only occur bounded by the black lines or by the picture's edge.
 */

package mondriangenerator;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MondrianGenerator extends JFrame {

	/**
	 * Variables
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	static int canvasHeight = 750, canvasWidth = 750;

	public static List<Rectangle> createRectangles(int width, int height) {

		// array of rectangles
		List<Rectangle> rs = new ArrayList<Rectangle>();
		rs.add(0, new Rectangle(0, 0, width, height));
		double size;

		// make initial horizontal & vertical lines
		size = getRandom();
		splitRectangleHor(rs, 0, size);
		size = getRandom();
		splitRectangleVer(rs, 0, size);
		splitRectangleVer(rs, 3, size);

		// split random rectangles into two
		for (int j = 1; j < rs.size(); j++) {
			size = getRandom();
			// 1/3 chance of splitting any given rectangle
			if ((int) (Math.random() * 3 + 1) == 3) {
				// 1/2 chance of splitting horizontally or vertically
				if ((int) (Math.random() * 2 + 1) == 1) {
					splitRectangleHor(rs, j, size);
				} else {
					splitRectangleVer(rs, j, size);
				}
			}
		}

		return rs;
	}

	public static Rectangle splitHorBottom(Rectangle r, double size) {
		// Splits a rectangle horizontally, returns bottom part
		Rectangle r2 = new Rectangle(r.x, r.y, r.width, r.height);
		r2.y = (int) Math.round(r2.y + (r2.height * (1 - size)));
		r2.height = (int) Math.round(r2.height * size);
		return r2;
	}

	public static Rectangle splitHorTop(Rectangle r, double size) {
		// Splits a rectangle horizontally, returns top part
		r.height = (int) Math.round(r.height * (1 - size));
		return r;
	}

	public static Rectangle splitVerRight(Rectangle r, double size) {
		Rectangle r2 = new Rectangle(r.x, r.y, r.width, r.height);
		r2.x = (int) Math.round(r2.x + (r2.width * (1 - size)));
		r2.width = (int) Math.round(r2.width * size);
		return r2;
	}

	public static Rectangle splitVerLeft(Rectangle r, double size) {
		// Same as above except vertically
		r.width = (int) Math.round(r.width * (1 - size));
		return r;
	}

	public static void splitRectangleHor(List<Rectangle> rs, int index, double size) {
		rs.add(splitHorBottom(rs.get(index), size));
		rs.add(index, splitHorTop(rs.get(index), size));
	}

	public static void splitRectangleVer(List<Rectangle> rs, int index, double size) {
		rs.add(splitVerRight(rs.get(index), size));
		rs.add(index, splitVerLeft(rs.get(index), size));
	}

	public static double getRandom() {
		double rand = Math.random() * 2 / 3 + 0.1;
		return rand;
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MondrianGenerator frame = new MondrianGenerator();
					frame.setVisible(true);
					frame.setTitle("Mondrian Generator");
					frame.setResizable(false);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MondrianGenerator() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, canvasWidth + 120, canvasHeight + 25);
		contentPane = new Canvas();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JButton btnGenerate = new JButton("Generate!");
		btnGenerate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Refreshes screen
				contentPane.repaint();
			}
		});
		getContentPane().add(btnGenerate, BorderLayout.EAST);
	}

	class Canvas extends JPanel {

		private static final long serialVersionUID = 1L;

		public Canvas() {
			setBorder(BorderFactory.createLineBorder(Color.black));
		}

		public Dimension getPreferredSize() {
			return new Dimension(canvasWidth, canvasHeight);
		}

		public void paintComponent(Graphics g) {

			Graphics2D g2 = (Graphics2D) g;
			super.paintComponent(g2);

			List<Rectangle> rs = createRectangles(canvasWidth, canvasHeight);
			int strokeWidth = 10;
			g2.setStroke(new BasicStroke(strokeWidth));
			
			// set colours
			Color white = new Color(250, 250, 250);
			Color black = new Color(10, 15, 20);
			Color red = new Color(220, 45, 60);
			Color yellow = new Color(250, 225, 65);
			Color blue = new Color(50, 80, 195);

			// create ART!!! <3
			for (int i = 0; i < rs.size(); i++) {
				Color colour;
				int rand = (int) (Math.random() * 10 + 1);
				if (rand == 1) {
					colour = black;
				} else if (rand == 2) {
					colour = red;
				} else if (rand == 3) {
					colour = yellow;
				} else if (rand == 4) {
					colour = blue;
				} else {
					colour = white;
				}
				g2.setColor(colour);
				g2.fill(rs.get(i));
				g2.setColor(Color.BLACK);
				g2.draw(rs.get(i));
			}
			
			// draw border
			g2.fillRect(0, 0, canvasWidth, strokeWidth);
			g2.fillRect(0, canvasHeight - strokeWidth, canvasWidth, strokeWidth);
			g2.fillRect(0, 0, strokeWidth, canvasWidth);
			g2.fillRect(canvasWidth - strokeWidth, 0, strokeWidth, canvasWidth);
			
		}
	}

}
