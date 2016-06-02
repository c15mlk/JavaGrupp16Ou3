package com.javagrupp16.ou3.entities;

import com.javagrupp16.ou3.Network;
import com.javagrupp16.ou3.Path;
import com.javagrupp16.ou3.Position;
import com.javagrupp16.ou3.Randoms;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

/**
 * Class that inherits from moveable and that represents and agent moving in
 * an network.
 * Created by Grupp 16 on 2016-05-17.
 **/
public class Agent extends Moveable {

	private int maxSteps;
	private Map<Position, Boolean> visitedNodeMap = new HashMap<>();
	private Map<UUID, Integer> routingMap = new HashMap<>();

	/**
	 * Constructor that initializes an agent.
	 * @param maxSteps Maximum number of steps an agent can go.
	 * @param node the node were the agent is created.
     * @param eventID the unique id of the event that happened.
     */
	public Agent(int maxSteps, Node node, UUID eventID) {
		super(node.getPosition());
		this.maxSteps = maxSteps;
		routingMap.put(eventID, 0);
	}

	/**
	 * Method that the agent uses to move with.
	 */
	@Override
	public void move(Network network) {

		Node currentNode = network.getNode(this);


		Position oldPos = getPosition();

		Position pos = null;
		for (int i = 0; i < 8; i++) {
			pos = Randoms.randomItem(currentNode.getNeighbours()).
					toPosition(getPosition(), 10, 10);
			if (!visitedNodeMap.containsKey(pos)) {
				break;
			}
		}

		visitedNodeMap.put(getPosition(), true);
		/*Walk towards our target*/
		walkTo(pos);

		synchronizeNode(network.getNode(this), oldPos);
	}

	/**
	 * Method that synchronises the info of an agent and a node.
	 * @param node the node the agent syncs with.
	 * @param nextDest nest position to the event. meaning the position the
	 *                 agent came from.
     */
	public void synchronizeNode(Node node, Position nextDest) {

		for (Entry<UUID, Integer> entry : routingMap.entrySet()) {
			routingMap.put(entry.getKey(), entry.getValue() + 1);
			Path path = new Path(entry.getKey(), nextDest, entry.getValue());
			if (!node.getRoutingMap().containsKey(entry.getKey())) {
				node.getRoutingMap().put(entry.getKey(), path);
			} else {
				Path p = node.getRoutingMap().get(entry.getKey());
				if (path.getStepsToEvent() < p.getStepsToEvent()) {
					node.getRoutingMap().put(entry.getKey(), path);
				}
			}
		}


		Map<UUID, Path> nodeRouting = node.getRoutingMap();
		if (!nodeRouting.isEmpty()) {
			/*Sync nodes routing into this agent*/
			for (Entry<UUID, Path> entry : nodeRouting.entrySet()) {
				if (!routingMap.containsKey(entry.getKey())) {
					routingMap.put(entry.getKey(),
							entry.getValue().getStepsToEvent());
				}
			}
		}

	}

	@Override
	public boolean isComplete() {
		if (getSteps() >= maxSteps) {
			return true;
		}
		return false;
	}
}