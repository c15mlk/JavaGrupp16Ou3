import java.util.*;


/**
 * Network.java
 * Simulates a network.
 * Created by Grupp 16 on 2016-05-17.
 **/
public class Network {

	private Map<Position, Node> nodes;
	private List<UUID> eventIDList;
	private int numberOfTicks, counter;
	private double agentProb, eventProb;

	/**
	 * Constructor that initializes a network of nodes, it also sets the
	 * probability of the creation of an agent and an event.
	 * @param height Determines the height of the network.
	 * @param width Determines the width of the network.
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
				Node node = new Node(p);
				nodes.put(p, node);
			}
		}

		for (Node n : nodes.values()) {
			for (Direction d : Direction.values()) {
				Position p = d.toPosition(n.getPosition(), 10, 10);
				if (hasNode(p)) {
					n.addNeighbourIn(d);
				}
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

	/**
	 * Gets the node a moveable stands on.
	 * @param moveable an agent or request that has the position of a node
     * @return a node in the network.
     */
	public Node getNode(Moveable moveable) {
		if (nodes.containsKey(moveable.getPosition()))
			return nodes.get(moveable.getPosition());
		return null;
	}

	/**
	 * Returns the agent probability of the network.
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
						if (randomNode.requestEvent(Randoms.randomItem(eventIDList), getTime())) {
							break;
						}
					}
				}
			}
			counter = 0;
		}

		for (final Node n : nodes.values()) {
			n.timeTick(this);
			if (Randoms.chanceOf(eventProb)) {
				UUID uuid = UUID.randomUUID();
				eventIDList.add(uuid);
				n.detectEvent(uuid, getTime(), getAgentProb());
			}
		}
		counter++;
		numberOfTicks++;
	}

	/**
	 * gets the time of the Network. the number of tics the network has
	 * worked for.
	 * @return an int that contains the number of tics.
     */
	public int getTime() {
		return numberOfTicks;
	}

	/**
	 * gets the eventIDList of the Network.
	 * @return a list containing the event IDs.
     */
	public List<UUID> getEventIDList() {
		return eventIDList;
	}

}
