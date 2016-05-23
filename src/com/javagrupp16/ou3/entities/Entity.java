package com.javagrupp16.ou3.entities;

import com.javagrupp16.ou3.Network;
import com.javagrupp16.ou3.Position;

/**
 * Class that represents an entity.
 * Created by Grupp 16 on 2016-05-17.
 **/

public class Entity {
	protected Network network;
	private Position position;

	/**
	 * Constructor that initializes an entity in the network at a given
	 * position.
	 * @param network the network the entity exists in.
	 * @param position the position the will be created entity at.
     */
	public Entity(Network network, Position position) {
		this.network = network;
		this.position = position;
	}

	public Position getPosition() {
		return position;
	}

	/**
	 * Method that sets the position of an entity.
	 * @param position the position the entity will be set to.
     */
	public void setPosition(Position position) {
		if (position.equals(getPosition())) {
			throw new IllegalStateException("Attempting to move to same position");
		}
		int xDiff = Math.abs(getPosition().getX() - position.getX());
		int yDiff = Math.abs(getPosition().getY() - position.getY());
		if (xDiff > 10 || yDiff > 10)
			throw new IllegalStateException("xDiff " + xDiff + " yDiff " + yDiff);

		this.position = position;
	}

}
