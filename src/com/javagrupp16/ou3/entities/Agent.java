package com.javagrupp16.ou3.entities;

import com.javagrupp16.ou3.*;
import com.sun.org.apache.xpath.internal.SourceTree;

import java.lang.reflect.Array;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

/**
 * Created by Marcus on 2016-05-17.
 **/
public class Agent extends Moveable {

    private int maxSteps;
    private Map<Position, Boolean> visitedNodeMap = new HashMap<>();
    private Map<UUID, Integer> routingMap = new HashMap<>();
    private Node sourceNode;



    public Agent(Network network, int maxSteps, Node node, UUID eventID) {
        super(network, node.getPosition());
        this.maxSteps = maxSteps;
        this.sourceNode = node;
        routingMap.put(eventID,0);
    }

    @Override
    public void move() {

        Node currentNode = network.getNode(getPosition());


        Position oldPos = getPosition();

        Position pos = null;
        for (int i = 0; i < 8; i++) {
            pos = Randoms.randomItem(currentNode.getNeighbours()).toPosition(getPosition(),10,10);
            if (!visitedNodeMap.containsKey(pos)) {
                break;
            }
        }

        visitedNodeMap.put(getPosition(), true);
        /*Walk towards our target*/
        walkTo(pos);

        synchronizeNode(network.getNode(getPosition()),oldPos);
    }

    public void synchronizeNode(Node node, Position nextDest) {


        /*Map<UUID,Path> nodeRouting = node.getRoutingMap();
        if(!nodeRouting.isEmpty()) {
            /*Sync nodes routing into this agent
            for (Entry<UUID, Path> entry : nodeRouting.entrySet()) {
                if (!routingMap.containsKey(entry.getKey())) {
                    routingMap.put(entry.getKey(), entry.getValue().getStepsToEvent());
                } else {
                    Integer stepsToEvent = routingMap.get(entry.getKey());
                    if (entry.getValue().getStepsToEvent() < stepsToEvent)
                        routingMap.put(entry.getKey(), entry.getValue().getStepsToEvent());
                }
            }
        }*/

       // nodeRouting.clear();

        for(Entry<UUID, Integer> entry : routingMap.entrySet()){
            routingMap.put(entry.getKey(), entry.getValue() + 1);
        }
        for(Entry<UUID, Integer> entry : routingMap.entrySet()){
            Path path = new Path(entry.getKey(), nextDest, entry.getValue());
            node.getRoutingMap().put(entry.getKey(), path);
        }
    }

    public void debug(){
        //System.out.println("Agent ended: " + routingMap.size() + " routing size , " + getSteps() + " steps");
    }

    @Override
    public boolean isComplete() {
        if (getSteps() >= maxSteps) {
            return true;
        }
        return false;
    }
}