/**
 * Moveable.java
 * An abstract class representing a moveable entity.
 * Created by Grupp 16 on 2016-05-17.
 **/
public abstract class Moveable extends Entity {

	private int steps;
	private boolean complete = false;

	/**
	 * Constructs a Moveable
	 * @param position the moveable's starting position
     */
	public Moveable(Position position) {
		super(position);
	}

	/**
	 * Abstract method for moving.
	 * @param network the network that the object will move in.
     */
	public abstract void move(Network network);

	/**
	 * A method used to walk to a position
	 * @param position the position to go to.
     */
	public void walkTo(Position position) {
		setPosition(position);
		steps++;
	}

	/**
	 * Returns the amount of steps taken.
	 * @return integer
     */
	public int getSteps() {
		return steps;
	}

	/**
	 * checks if a moveable is complete.
	 * @return boolean: true or false.
     */
	public boolean isComplete() {
		return complete;
	}

	/**
	 * sets the variable complete as true or false.
	 * @param b containing a boolean: true or false.
     */
	public void setComplete(boolean b) {
		this.complete = b;
	}

}
