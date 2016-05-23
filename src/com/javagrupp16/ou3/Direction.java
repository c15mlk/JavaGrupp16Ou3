package com.javagrupp16.ou3;

/**
 * Direction.java
 * Enum representing Directions such as up/down left/right
 * and the combined versions of those.
 * Created by Grupp 16 on 2016-05-19.
 */
public enum Direction {
	UP(0, 1), DOWN(0, -1), RIGHT(1, 0),
	LEFT(-1, 0), BOTTOM_RIGHT(1, -1),
	BOTTOM_LEFT(-1, -1), TOP_RIGHT(1, 1),
	TOP_LEFT(-1, 1);


	private int xDiff, yDiff;

	Direction(int x, int y) {
		this.xDiff = x;
		this.yDiff = y;
	}

	/**
	 * Translates a direction to a position from a source position and with vector values
	 * @param from source position
	 * @param xMod vector for X
	 * @param yMod vector for Y
     * @return the position translated from this direction
     */
	public Position toPosition(Position from, int xMod, int yMod) {
		return new Position(from.getX() + (xDiff * xMod), from.getY() + (yDiff * yMod));
	}

}
