import java.util.*;

/**
 * Node.java
 * Represents a node in the network.
 * Created by Grupp 16 on 2016-05-17.
 **/
public class Node extends Entity {

	private final List<Direction> neighbours = new ArrayList<>();
	final Map<UUID, Event> eventsMap = new HashMap<>();
	private Map<UUID, Path> routingMap = new HashMap<>();
	private final Map<Moveable, Moveable> moveableMap = new HashMap<>();
	private final ArrayDeque<Runnable> runnableQue = new ArrayDeque<>();
	final Map<UUID, Integer> expectedInfo = new HashMap<>();

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
	 * @param time the time of the event
	 * @param agentProb the probability that an agent is created.
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
	 * Creates a request for the given Event-ID if the node doesn't have information on the event.
	 * Schedules the request to be added to the node when timeTick() is called
	 * and it is first in the que.
	 * @param uuid the Event-ID
	 * @param time the time that the event happened.
	 * @return boolean representing if this node already has information on this event.
     */
	public boolean requestEvent(UUID uuid, int time) {
		if (eventsMap.containsKey(uuid)) {
			return false;
		}
		final Request request = new Request(uuid, this, NetworkMain.REQUEST_MAX_STEPS);
		expectedInfo.put(uuid, time);
		runnableQue.add(new Runnable() {
			@Override
			public void run() {
				moveableMap.put(request, request);
			}
		});
		return true;
	}

	/**
	 * Creates a new Request for the specified eventUUID.
	 * Only called by the node when it should resend requests.
	 * @param uuid
     */
	private void resendRequest(UUID uuid){
		if (eventsMap.containsKey(uuid)) {
			return;
		}
		final Request request = new Request(uuid, this, NetworkMain.REQUEST_MAX_STEPS);
		runnableQue.add(new Runnable() {
			@Override
			public void run() {
				moveableMap.put(request, request);
			}
		});
	}

	/**
	 * Receives information from a request returning with the information
	 * Schedules the information to be printed when timeTick() is called
	 * and it is first in the que.
	 * @param info the information that was received.
	 * @param steps nr of steps toward the event.
     * @param time the time of the request return.
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

	/**
	 * Updates all the {@link Moveable} objects that belong to this node.
	 * Also checks so if the requests have exceeded a certain number of steps
	 * and not returned with the information
	 * and attempts to send a new Request for that event.
	 * Also executes a single special action such as receiving information,
	 * sending a request.
	 * @param network the network that the node belongs to.
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

		Iterator<Map.Entry<UUID,Integer>> iterator = expectedInfo.entrySet().iterator();
		while(iterator.hasNext()) {
			Map.Entry<UUID, Integer> entry = iterator.next();
			if ((network.getTime() - entry.getValue()) > NetworkMain.REQUEST_MAX_STEPS * 8) {
				resendRequest(entry.getKey());
				iterator.remove();
			}
		}
		if (!runnableQue.isEmpty()) {
			runnableQue.pop().run();
		}
	}

	/**
	 * Gets the routing map of the node.
	 * @return a Map variable.
     */
	public Map<UUID, Path> getRoutingMap() {
		return routingMap;
	}

	/**
	 * Returns a list containing the directions neighbours exist in.
	 * @return a list variable.
     */
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
