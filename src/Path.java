
/**
 * A class that holds the path towards and event as well as the unique id of it.
 * Created by Grupp 16 on 2016-05-23.
 */
public class Path {

	private final Position nextDest;
	private final int stepsToEvent;

	/**
	 * Constructor that initializes a path.
	 * @param nextDest the next position towards an event.
	 * @param stepsToEvent then number of steps toward an event.
     */
	public Path(Position nextDest, int stepsToEvent) {
		this.nextDest = nextDest;
		this.stepsToEvent = stepsToEvent;
	}

	/**
	 * Gets the amount of steps needed to the event.
	 * @return an int containing the nr of steps to an Event.
     */
	public int getStepsToEvent() {
		return stepsToEvent;
	}

	/**
	 * Gets the next position to reach the event.
	 * @return a position that is the next destination on the path.
     */
	public Position getNextDest() {
		return nextDest;
	}
}
