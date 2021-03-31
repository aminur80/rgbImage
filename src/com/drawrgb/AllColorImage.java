package com.drawrgb;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import javax.swing.JComponent;
import javax.swing.JFrame;

/**
 * 
 * @author aminur
 *
 */

public class AllColorImage extends JComponent {

	private static final long serialVersionUID = 1L;

	private BufferedImage image;

	private static int NUM_OF_COLORS = 32;

	private static int WIDTH = 256;

	private static int HEIGHT = 128;

	public void initialize() {

		List<Color> colors = new ArrayList<Color>();

		colors = createAllColorsOnce();

		// define 2D area 256x128 to draw RGB image
		final Color[][] pixels = new Color[WIDTH][HEIGHT];

		int colorIndex = 0;

		Point pointToColor;

		// loop through x, y within the area
		for (int y = 0; y < HEIGHT; y++) {

			for (int x = 0; x < WIDTH; x++) {

				if (pixels[x][y] != null)

					continue;

				pointToColor = new Point(x, y);

				// color the current point and all its neighbors
				pixels[x][y] = colors.get(colorIndex);

				colorIndex++;

				// loop through neighboring pixels
				for (Point p : getNeighborOfPoint(pointToColor)) {

					if (pixels[p.x][p.y] == null) {

						pixels[p.x][p.y] = colors.get(colorIndex);

						colorIndex++;

					}

				}

			}
		}

		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);

		for (int x = 0; x < WIDTH; x++) {

			for (int y = 0; y < HEIGHT; y++) {

				if (pixels[x][y] != null) {

					image.setRGB(x, y, pixels[x][y].getRGB());

				}

			}

		}

	}

	
	public void paint(Graphics g) {

		if (image == null) {

			initialize();

		}

		g.drawImage(image, 0, 0, this);

	}

	
	public void setBounds(int x, int y, int width, int height) {

		super.setBounds(x, y, width, height);

		initialize();

	}

	/**
	 * Create every color once using RGB value
	 * 
	 * @return list of RGB values
	 */

	public static List<Color> createAllColorsOnce() {

		List<Color> allColors = new ArrayList<Color>();

		for (int r = 0; r < NUM_OF_COLORS; r++) {

			for (int g = 0; g < NUM_OF_COLORS; g++) {

				for (int b = 0; b < NUM_OF_COLORS; b++) {

					allColors.add(new Color((int) (r * 255 / (NUM_OF_COLORS - 1)),
							(int) (g * 255 / (NUM_OF_COLORS - 1)), (int) (b * 255 / (NUM_OF_COLORS - 1))));
				}
			}
		}

		return allColors;
	}

	/**
	 * Retrieve all the neighbors of a pixel
	 * 
	 * @param p Point for which to find neighbors
	 * @return A set of points
	 */

	public static TreeSet<Point> getNeighborOfPoint(Point p) {

		TreeSet<Point> neighbors = new TreeSet<Point>(new CustomPointComparator());

		for (int xx = -1; xx <= 1; xx++) {

			// check if the x coordinate is outside the area
			if (p.x + xx < 0 || p.x + xx == WIDTH)

				continue;

			for (int yy = -1; yy <= 1; yy++) {

				// check if y coordinate is outside the area or x, y value point to the same pixel
				if ((xx == 0 && yy == 0) || p.y + yy < 0 || p.y + yy == HEIGHT)

					continue;

				if (isOnSpace(p.x + xx, p.y + yy)) {

					neighbors.add(new Point(p.x + xx, p.y + yy));

				}

			}

		}

		return neighbors;

	}

	/**
	 * Validate the x, y coordinate if it is within the area
	 * 
	 * @param x x coordinate
	 * @param y y coordinate
	 * @return true/false
	 */
	public static boolean isOnSpace(int x, int y) {

		return x >= 0 && y >= 0 && x < WIDTH && y < HEIGHT;

	}
	
	/**
	 * main program
	 * 
	 * @param args
	 */

	public static void main(String[] args) {

		JFrame frame = new JFrame("RGB Image");

		frame.add(new AllColorImage());

		frame.setSize(WIDTH, HEIGHT);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.setVisible(true);

	}

}
