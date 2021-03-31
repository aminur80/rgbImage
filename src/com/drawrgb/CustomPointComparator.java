package com.drawrgb;

import java.awt.Point;
import java.util.Comparator;

/**
 * Define a new comparator for Point input
 * 
 * @author aminur
 *
 */

class CustomPointComparator implements Comparator<Point> {

	public int compare(Point p1, Point p2) {

		int compareValue = Integer.bitCount(p1.x + p1.y) - Integer.bitCount(p1.x + p2.y);

		return compareValue < 0 ? -1 : compareValue == 0 ? 0 : 1;

	}
}
