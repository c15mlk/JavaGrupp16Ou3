package com.javagrupp16.ou3;

import com.javagrupp16.ou3.entities.Node;

import java.util.*;

public class Network {

	private Map<Position, Node> nodes;
	private List<UUID> eventIDList;
	private int numberOfTicks, counter;
	private double agentProb, eventProb;

	public static final int AGENT_MAXSTEPS = 50;
	public static final int REQUEST_MAXSTEPS = 45;

	/**
	 * Constructor that initializes a network of nodes, it also sets the
	 * probability of the creation of an agent and an event.
	 * @param height Determines the height of the network.
	 * @param width Determines the height of the network.
	 * @param agentProb Determines the chance that an agent is created.
	 * @param eventProb Determines the chance that an event is created.
     */
	public Network(int height, int width, double agentProb, double eventProb) {
		nodes = new HashMap<>(height * width);
		eventIDList = new ArrayList<>(height * width);
		this.agentProb = agentProb;
		this.eventProb = eventProb;
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				Position p = new Position(x * 10, y * 10);
				Node node = new Node(this, p);
				nodes.put(p, node);
			}
		}

		for (Node n : nodes.values()) {
			for (Direction d : Direction.values()) {
				n.addNeighbourAt(d);
			}
		}
	}

	/**
	 * Method that checks there is a node on a given position.
	 * @param position the position to be checked.
	 * @return boolean: true if there is a node there otherwise false.
     */
	public boolean hasNode(Position position) {
		return nodes.containsKey(position);
	}

	public Node getNode(Position position) {
		if (nodes.containsKey(position))
			return nodes.get(position);
		return null;
	}

	/**
	 * methods that gets the probality for the creation of an agent.
	 * @return double that has the probability of an agent creation.
     */
	public double getAgentProb() {
		return agentProb;
	}

	/**
	 * Method that advances the time of the network.
	 */
	public void timeTick() {
		if (counter >= 400) {
			for (int i = 0; i < 4; i++) {
				if (!eventIDList.isEmpty()) {
					Node randomNode = Randoms.randomItem(new ArrayList<>(nodes.values()));
					 /*Prevents nodes that already have information on a
					  event asking for information on that event*/
					for (int j = 0; j < eventIDList.size(); j++) {
						if (randomNode.requestEvent(Randoms.randomItem(eventIDList))) {
							break;
						}
					}
				}
			}
			counter = 0;
		}

		for (final Node n : nodes.values()) {
			n.timeTick();
			if (Randoms.chanceOf(eventProb)) {
				UUID uuid = UUID.randomUUID();
				eventIDList.add(uuid);
				n.detectEvent(uuid);
			}
		}
		counter++;
		numberOfTicks++;
	}

	public int getTime() {
		return numberOfTicks;
	}


	public List<UUID> getEventIDList() {
		return eventIDList;
	}

}
