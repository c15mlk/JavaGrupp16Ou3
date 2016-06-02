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
	private final Map<Moveable, Moveable> moveableMap = new HashMap<>();
	private final ArrayDeque<Runnable> runnableQue = new ArrayDeque<Runnable>();
	protected final Map<UUID, Integer> expectedInfo = new HashMap<>();

	/**
	 * Constructs a Node.
	 * @param position the node's position
	 */
	public Node(Position position) {
		super(position);
	}

	/**
	 * The node detects a event with the given UUID.
	 * There's a chance of a agent emerging with the event.
	 * @param uuid the event's uuid
     */
	public void detectEvent(UUID uuid, int time, double agentProb) {
		Event event = new Event(uuid, getPosition(), time);
		eventsMap.put(uuid, event);
		if (Randoms.chanceOf(agentProb)) {
			Agent agent = new Agent(NetworkMain.AGENT_MAX_STEPS, this, uuid);
			moveableMap.put(agent, agent);
		}
	}

	/**
	 * Sends a request for the given Event-ID if the node doesn't have information on the event.
	 * @param uuid the Event-ID
	 * @return boolean representing if this node already has information on this event.
     */
	public boolean requestEvent(UUID uuid, int time) {
		if (eventsMap.containsKey(uuid)) {
			return false;
		}
		final Request request = new Request(uuid, this, NetworkMain.REQUEST_MAX_STEPS);
		if (expectedInfo.containsKey(uuid)) {
			expectedInfo.remove(uuid);
		} else {
			expectedInfo.put(uuid, time);
		}
		runnableQue.add(new Runnable() {
			@Override
			public void run() {
				moveableMap.put(request, request);
			}
		});
		return true;
	}

	/**
	 * Updates all the {@link Moveable} objects that belong to this node.
	 * Also checks so if the requests have exceeded a certain number of steps
	 * and not returned with the information
	 * and attempts to send a new Request for that event.
	 * Also executes a single special action such as receiving information,
	 * sending a request.
	 */
	public void timeTick(Network network) {
		Deque<Moveable> removeQue = new ArrayDeque<>();
		for (final Moveable moveable : moveableMap.values()) {
			if (moveable.isComplete()) {
				removeQue.add(moveable);
			} else {
				moveable.move(network);
			}
		}
		while (!removeQue.isEmpty()) {
			Moveable moveable = removeQue.pop();
			moveableMap.remove(moveable);
		}
		for (Map.Entry<UUID, Integer> entry : expectedInfo.entrySet()) {
			if ((network.getTime() - entry.getValue()) > NetworkMain.REQUEST_MAX_STEPS * 8) {
				requestEvent(entry.getKey(), network.getTime());
			}
		}
		if (!runnableQue.isEmpty()) {
			runnableQue.pop().run();
		}
	}

	/**
	 * Receives information from a request returning with the information.
	 * Marks the request for removal.
     */
	public void receiveEvent(final String info, final int steps, final int time) {
		runnableQue.add(new Runnable() {
			@Override
			public void run() {
				System.out.println(time + ": Request returned with information in " + steps + " steps from position " + getPosition());
				System.out.println(time + ": " + info);
			}
		});

	}


	public Map<UUID, Path> getRoutingMap() {
		return routingMap;
	}

	public List<Direction> getNeighbours() {
		return neighbours;
	}

	/**
	 * Used at Network.init() for adding neighbours for the node.
	 * @param direction a direction a neighbour is in.
     */
	public void addNeighbourIn(Direction direction) {
		neighbours.add(direction);
	}
}
