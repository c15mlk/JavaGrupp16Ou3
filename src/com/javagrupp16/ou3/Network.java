package com.javagrupp16.ou3;

import com.javagrupp16.ou3.entities.Node;

import java.util.*;

/**
 * Created by Marcus on 2016-05-17.
 **/
public class Network {

    private Map<Position, Node> nodes = new HashMap<Position, Node>();
    private List<UUID> eventIDList = new ArrayList<UUID>();
    private int height, width, agentProb, eventProb, numberOfTicks;

    private final Random random = new Random();

    public Network(int height, int width, int agentProb, int eventProb){
        this.height = height;
        this.width = width;
        this.agentProb = agentProb;
        this.eventProb = eventProb;
        int multiplier = 10;
        for(int x = 0 ; x < width ; x++){
            for(int y = 0 ; y < height ; y++){
                Position p = new Position(x*10,y*10);
                Node node = new Node(this, p);
                nodes.put(p,node);
            }
        }
    }

    public boolean hasNode(Position position){
        return nodes.containsKey(position);
    }

    public Node getNode(Position position){
        if(nodes.containsKey(position))
            return nodes.get(position);
        return null;
    }

    public int getAgentProb(){
        return agentProb;
    }

    public void timeTick(){
        for(Node n : nodes.values()) {
            int rng = random.nextInt(100);
            if (eventProb > rng) {
                UUID uuid = UUID.randomUUID();
                eventIDList.add(uuid);
                //n.detectEvent(uuid)
            }
            //n.timeTick()
        }

    }

    public int getTime(){
        return numberOfTicks;
    }
}
