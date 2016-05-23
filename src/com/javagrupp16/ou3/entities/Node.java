package com.javagrupp16.ou3.entities;

import com.javagrupp16.ou3.*;

import java.util.*;

/**
 * Node.java
 * Represents a node in the network.
 * Created by Grupp 16 on 2016-05-17.
 **/
public class Node extends Entity {

	private final List<Direction> neighbours = new ArrayList<>();
	protected final Map<UUID, Event> eventsMap = new HashMap<UUID, Event>();
	private Map<UUID, Path> routingMap = new HashMap<>();
	private final Map<Moveable, Moveable> moveableList = new HashMap<>();
	private final ArrayDeque<Runnable> runnableQue = new ArrayDeque<Runnable>();
	protected final Map<UUID, Integer> expectedInfo = new HashMap<>();

	/**
	 * Constructs a Node.
	 * @param network the network it belongs to.
	 * @param position the node's position
	 */
	public Node(Network network, Position position) {
		super(network, position);
	}

	/**
	 * The node detects a event with the given UUID.
	 * There's a chance of a agent emerging with the event.
	 * @param uuid the event's uuid
     */
	public void detectEvent(UUID uuid) {
		Event event = new Event(uuid, getPosition(), network.getTime());
		eventsMap.put(uuid, event);
		if (Randoms.chanceOf(network.getAgentProb())) {
			Agent agent = new Agent(network, Network.AGENT_MAXSTEPS, this, uuid);
			moveableList.put(agent, agent);
		}
	}

	/**
	 * Sends a request for the given Event-ID if the node doesn't have information on the event.
	 * @param uuid the Event-ID
	 * @return boolean representing if this node already has information on this event.
     */
	public boolean requestEvent(UUID uuid) {
		if (eventsMap.containsKey(uuid)) {
			return false;
		}
		final Request request = new Request(network, getPosition(), uuid, this, Network.REQUEST_MAXSTEPS);
		if (expectedInfo.containsKey(uuid)) {
			expectedInfo.remove(uuid);
		} else {
			expectedInfo.put(uuid, network.getTime());
		}
		runnableQue.add(new Runnable() {
			@Override
			public void run() {
				moveableList.put(request, request);
			}
		});
		return true;
	}

	/**
	 * Updates all the {@link Moveable} objects that belong to this node.
	 * Also checks so if the requests have exceeded a certain number of steps
	 * and not returned with the information
	 * and attempts to send a new {@Link Request} for that event.
	 * Also executes a single special action such as receiving information,
	 * sending a request.
	 */
	public void timeTick() {
		Deque<Moveable> removeQue = new ArrayDeque<>();
		for (final Moveable moveable : moveableList.values()) {
			if (moveable.isComplete()) {
				removeQue.add(moveable);
			} else {
				moveable.move();
			}
		}
		while (!removeQue.isEmpty()) {
			Moveable moveable = removeQue.pop();
			if (moveable instanceof Request)
				((Request) moveable).onRemove();
			moveableList.remove(moveable);
		}
		for (Map.Entry<UUID, Integer> entry : expectedInfo.entrySet()) {
			if ((network.getTime() - entry.getValue()) > Network.REQUEST_MAXSTEPS * 8) {
				requestEvent(entry.getKey());
			}
		}
		if (!runnableQue.isEmpty()) {
			runnableQue.pop().run();
		}
	}

	/**
	 * Receives information from a request returning with the information.
	 * Marks the request for removal.
	 * @param request the request
     */
	public void receiveEvent(final Request request) {
		runnableQue.add(new Runnable() {
			@Override
			public void run() {
				System.out.println(network.getTime() + ": Request returned with information in " + request.getSteps() + " steps from position " + request.getPosition());
				System.out.println(network.getTime() + ": " + request.getInfo().toString());
			}
		});
		request.setComplete(true);
	}


	public Map<UUID, Path> getRoutingMap() {
		return routingMap;
	}

	public List<Direction> getNeighbours() {
		return neighbours;
	}

	/**
	 * Used at Network.init() for checking and adding neighbours for the node.
	 * @param direction a direction a neighbour may be at.
     */
	public void addNeighbourAt(Direction direction) {
		Position p = direction.toPosition(getPosition(), 10, 10);
		if (network.hasNode(p)) {
			neighbours.add(direction);
		}
	}
}
