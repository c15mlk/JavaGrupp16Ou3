package com.javagrupp16.ou3.entities;

import com.javagrupp16.ou3.*;

import java.util.ArrayDeque;
import java.util.Map;
import java.util.UUID;

/**
 * Request.java
 * Represents a request sent by a Node.
 * Searches for the event-id given during construction.
 * Created by Grupp 16 on 2016-05-17.
 **/
public class Request extends Moveable {


	private Event info = null;
	private ArrayDeque<Position> stack = new ArrayDeque<>();

	private int maxSteps;
	private final UUID eventUUID;
	private final Node sourceNode;

	private boolean onPath = false;

	/**
	 * Constructs a Request with the given parameters.
	 * @param uuid the event-id of the event it searches for.
	 * @param node the node that sent it.
     * @param maxSteps the maximum number of steps it can take.
     */
	public Request(UUID uuid, Node node, int maxSteps) {
		super(node.getPosition());
		this.maxSteps = maxSteps;
		this.eventUUID = uuid;
		this.sourceNode = node;
		stack.addFirst(getPosition());
	}

	/**
	 * Moves to a random neighbour if no path to event is found.
	 * If the path to the event is found then it walk according to that path.
	 * When it reaches the event it will start backtracking with this information.
	 * @param network the network that the request moves in.
	 */
	@Override
	public void move(Network network) {

		/*If we have info on the event
		 *We want to go back the way we walked to get the info.*/
		if (info != null) {
			if (!stack.isEmpty()) {
				Position p = stack.pop();
				walkTo(p);
				if (getPosition().equals(sourceNode.getPosition())) {
					sourceNode.receiveEvent(info.toString(), getSteps(), network.getTime());
					setComplete(true);
					sourceNode.expectedInfo.remove(eventUUID);
				}
			}
			return;
		}

		/*Assert that the request is on a node*/
		if (network.hasNode(getPosition())) {
			Node targetNode = network.getNode(this);
			/*if it is has a path to the event.*/
			Map<UUID, Path> routingMap = targetNode.getRoutingMap();
			if (routingMap.containsKey(eventUUID)) {
				/*Start moving according to that path*/
				onPath = true;
				walkTo(routingMap.get(eventUUID).getNextDest());

			} else {
				if (onPath) {
					throw new IllegalStateException("Found path but still walking towards neighbours");
				}
				/*Select a new random neighbour*/
				Direction dir = Randoms.randomItem(targetNode.getNeighbours());
				walkTo(dir.toPosition(getPosition(), 10, 10));
			}

			/*If we stand on a node and the node contains info on the event.*/
			/*Store the info and reset steps and return.*/
			targetNode = network.getNode(this);
			if (targetNode.eventsMap.containsKey(eventUUID)) {
				this.info = targetNode.eventsMap.get(eventUUID);
				return;
			}
		}



		/*Add how we walked to the stack*/
		stack.addFirst(getPosition());

	}

	/**
	 * Returns true if it's steps exceeds the max steps and it's not on a path to the event.
	 * Or when it is marked for removal because it has returned with the information.
	 * @return boolean representing if it's complete.
     */
	@Override
	public boolean isComplete() {
		if (getSteps() >= maxSteps && !onPath) {
			return true;
		} else {
			return super.isComplete();
		}
	}

}

