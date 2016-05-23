package com.javagrupp16.ou3;

import java.util.UUID;

/**
 * A class that holds the path towards and event as well as the unique id of it.
 * Created by Grupp 16 on 2016-05-23.
 */
public class Path {

	private final UUID eventUUID;
	private final Position nextDest;
	private final int stepsToEvent;

	/**
	 * Constructor that initializes a path.
	 * @param uuid an unique id of an event.
	 * @param nextDest the next position towards an event.
	 * @param stepsToEvent then number of steps toward an event.
     */
	public Path(UUID uuid, Position nextDest, int stepsToEvent) {
		this.eventUUID = uuid;
		this.nextDest = nextDest;
		this.stepsToEvent = stepsToEvent;
	}

	public int getStepsToEvent() {
		return stepsToEvent;
	}

	public Position getNextDest() {
		return nextDest;
	}
}
