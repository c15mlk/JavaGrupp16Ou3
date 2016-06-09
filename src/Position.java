/**
 * A class that is the position of another object.
 * Created by Grupp 16 on 2016-05-17.
 **/
public class Position {

	private final int x;
	private final int y;

	/**
	 * Creates a position and sets its x and y value.
	 * @param x value for x position
	 * @param y value for y position
	 */
	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * gets the x value of a position.
	 * @return an int containing the x value.
     */
	public int getX() {
		return this.x;
	}

	/**
	 * gets the y value of a position.
	 * @return an int containing the y value.
	 */

	public int getY() {
		return this.y;
	}

	/**
	 * Method that compares its own position to a given position and checks if
	 * they are the same.
	 * @param o the position to be checked with.
     * @return true if they are the same position otherwise false.
     */
	@Override
	public boolean equals(Object o) {
		if (o instanceof Position) {
			Position p = (Position) o;
			return x == p.x && y == p.y;
		}
		return false;
	}

	/**
	 * method that creates a hashcode to a position.
	 * @return an int containing the hashcode.
     */
	@Override
	public int hashCode() {
		int result = x;
		result = 31 * result + y;
		return result;
	}

	/**
	 * method that creates a string of a position.
	 * @return string of a position.
     */
	@Override
	public String toString() {
		return "x: " + x + " y:" + y;
	}
}
