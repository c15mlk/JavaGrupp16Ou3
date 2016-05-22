package com.javagrupp16.ou3.entities;

import com.javagrupp16.ou3.*;

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
    private Map<UUID, Route> routingMap = new HashMap<>();
    private Node sourceNode;
    private BiValue<Direction, Position> targetNeighbour = null;



    public Agent(Network network, int maxSteps, Node node, UUID eventID) {
        super(network, node.getPosition());
        this.maxSteps = maxSteps;
        this.sourceNode = node;
        Route route = new Route();
        route.add(node.getPosition());
        routingMap.put(eventID, route);
    }

    @Override
    public void move() {

        if (targetNeighbour == null) {
            this.targetNeighbour = Randoms.randomItem(sourceNode.getNeighbours());
        }

        /*If we stand on our target neighbour*/
        if (getPosition().equals(targetNeighbour.getValue())) {
            /*Synchronize and pick new target neighbour*/
            visitedNodeMap.put(targetNeighbour.getValue(), true);
            Node targetNode = network.getNode(targetNeighbour.getValue());
            synchronizeNode(targetNode);
            for (int i = 0; i < 8; i++) {
                targetNeighbour = Randoms.randomItem(targetNode.getNeighbours());
                if (!visitedNodeMap.containsKey(targetNeighbour.getValue())) {
                    break;
                }
            }
            setSteps(getSteps() + 1);
        }

        /*Walk towards our target*/
        walkPath(targetNeighbour);

        /*Update all the routes in Routing map.*/
        for (Route route : routingMap.values()) {
            route.add(getPosition());
        }
    }

    public void synchronizeNode(Node node) {

       /*Sync nodes routing into this agent*/
        for (Entry<UUID, Route> entry : node.routingMap.entrySet()) {
            if (!routingMap.containsKey(entry.getKey())) {
                routingMap.put(entry.getKey(), entry.getValue().clone());
            } else {
                Route route = routingMap.get(entry.getKey());
                if (entry.getValue().size() < route.size())
                    routingMap.put(entry.getKey(), entry.getValue().clone());
            }
        }

        node.routingMap = routingMap;
    }

    @Override
    public boolean isComplete() {
        if (getSteps() >= maxSteps) {
            return true;
        }
        return false;
    }
}