package com.javagrupp16.ou3.entities;

import com.javagrupp16.ou3.Network;
import com.javagrupp16.ou3.Position;

import java.util.Map;

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

	public abstract void move(Network network);

	/**
	 * A method used to walk to a position
	 * @param position the position to go to.
     */
	public void walkTo(Position position) {
		setPosition(position);
		steps++;
	}

	public int getSteps() {
		return steps;
	}

	public boolean isComplete() {
		return complete;
	}

	public void setComplete(boolean b) {
		this.complete = b;
	}

}
