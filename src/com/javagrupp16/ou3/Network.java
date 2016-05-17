package com.javagrupp16.ou3;

import com.javagrupp16.ou3.entities.Node;

import java.util.*;

/**
 * Created by Marcus on 2016-05-17.
 */
public class Network {

    private Map<Position, Node> nodes = new HashMap<Position, Node>();
    private List<UUID> eventIDList = new ArrayList<UUID>();
    private int height, width, agentProb, eventProb, numberOfTicks;

    public Network(int height, int width, int agentProb, int eventProb){
        this.height = height;
        this.width = width;
        this.agentProb = agentProb;
        this.eventProb = eventProb;
        for(int x = 0 ; x < width ; x++){
            for(int y = 0 ; y < height ; y++){

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

    public void timeTick(){
        for(Node n : nodes.values()){

        }
    }

    public int getTime(){
        return numberOfTicks;
    }
}
