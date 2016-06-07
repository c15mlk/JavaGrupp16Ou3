package com.javagrupp16.ou3;


import java.util.UUID;

/**
 * Class that represents an event.
 * Created by Grupp 16 on 2016-05-17.
 **/
public class Event {

	private int time;
	private UUID id;
	private Position position;

	/**
	 * Constructor that initializes an event.
	 * @param uuid an unique id to identify an event.
	 * @param position the position of an event.
     * @param time the time an event happened.
     */
	public Event(UUID uuid, Position position, int time) {
		id = uuid;
		this.position = position;
		this.time = time;
	}

	/**
	 * Method that gets the position of an event.
	 * @return the position of an event.
     */
	public Position getPosition() {
		return position;
	}

	/**
	 * creates a string of the information of an evenet
	 * @return String of event information.
     */
	@Override
	public String toString() {
		return "Time of Event: " + time + " Event-Location: " + position.toString() + " Event: " + id.toString();
	}

}
