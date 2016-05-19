package com.javagrupp16.ou3;

import com.javagrupp16.ou3.entities.Node;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

/**
 * Created by Marcus on 2016-05-17.
 **/
public class Network {

    private Map<Position, Node> nodes = new HashMap<>();
    private List<UUID> eventIDList = new ArrayList<>();
    private int height, width, numberOfTicks, counter;
    private double agentProb, eventProb;


    public static final int AGENT_MAXSTEPS = 50;
    public static final int REQUEST_MAXSTEPS = 45;

    public Network(int height, int width, double agentProb, double eventProb){
        this.height = height;
        this.width = width;
        this.agentProb = agentProb;
        this.eventProb = eventProb;
        for(int x = 0 ; x < width ; x++){
            for(int y = 0 ; y < height ; y++){
                Position p = new Position(x*10,y*10);
                Node node = new Node(this, p);
                nodes.put(p,node);
            }
        }

        for(Node n : nodes.values()){
            n.addNeighbourAt(0,10);
            n.addNeighbourAt(0,-10);
            n.addNeighbourAt(10,0);
            n.addNeighbourAt(-10,0);
            n.addNeighbourAt(10,10);
            n.addNeighbourAt(10,-10);
            n.addNeighbourAt(-10,10);
            n.addNeighbourAt(-10,-10);
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

    public double getAgentProb(){
        return agentProb;
    }

    public void timeTick(){
        for(Node n : nodes.values()) {
            if (Randoms.chanceOf(eventProb)) {
                UUID uuid = UUID.randomUUID();
                eventIDList.add(uuid);
                n.detectEvent(uuid);
            }
            n.timeTick();
        }

        if(counter == 60){
            for(int i = 0 ; i < 1 ; i++){ //TODO change 1 to 4 again
                Node randomNode = Randoms.randomItem(new ArrayList<Node>(nodes.values()));
                /*Prevents nodes that already have information on a event asking for information on that event*/
                while(!randomNode.requestEvent(Randoms.randomItem(eventIDList)));
            }
            //counter = 0;
        }
        counter++;
        numberOfTicks++;
    }

    public int getTime(){
        return numberOfTicks;
    }

}
