package com.javagrupp16.ou3.entities;

import com.javagrupp16.ou3.*;

import java.util.*;

/**
 * Created by Marcus on 2016-05-17.
 **/
public class Node extends Entity {

	private final List<Direction> neighbours = new ArrayList<>();
	protected final Map<UUID, Event> eventsMap = new HashMap<UUID, Event>();
	private Map<UUID, Path> routingMap = new HashMap<>();
	private final Map<Moveable, Moveable> moveableList = new HashMap<>();
	private final ArrayDeque<Runnable> runnableQue = new ArrayDeque<Runnable>();
	protected final Map<UUID, Integer> expectedInfo = new HashMap<>();

	public Node(Network network, Position position) {
		super(network, position);
	}

	public void detectEvent(UUID uuid) {
		Event event = new Event(uuid, getPosition(), network.getTime());
		eventsMap.put(uuid, event);
		if (Randoms.chanceOf(network.getAgentProb())) {
			Agent agent = new Agent(network, Network.AGENT_MAXSTEPS, this, uuid);
			moveableList.put(agent, agent);
		}
	}

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

	public void addNeighbourAt(Direction direction) {
		Position p = direction.toPosition(getPosition(), 10, 10);
		if (network.hasNode(p)) {
			neighbours.add(direction);
		}
	}
}
