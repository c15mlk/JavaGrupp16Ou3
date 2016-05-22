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

    /*Debug variables*/
    private static final boolean DEBUG = true;
    /*Debugga endast en Agent gör det enklare att läsa*/
    private static Agent debugTarget;


    public Agent(Network network, int maxSteps, Node node, UUID eventID) {
        super(network, node.getPosition());
        this.maxSteps = maxSteps;
        this.sourceNode = node;
        Route route = new Route();
        route.add(node.getPosition());
        routingMap.put(eventID, route);

        if (DEBUG && debugTarget == null) {
            debugTarget = this;
            System.out.println("Agent spawned at " + getPosition().toString());
        }
    }

    @Override
    public void move() {


        Position oldPos = getPosition();

        if (targetNeighbour == null) {
            this.targetNeighbour = Randoms.randomItem(sourceNode.getNeighbours());
        }

        //Random move towards unvisisted Node similar to Request

        if (getPosition().equals(targetNeighbour.getValue())) {
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
            if (DEBUG && debugTarget == this) {
                System.out.println("Agent changed target neighbour to " + targetNeighbour.getValue().toString());
            }
        }

        walkPath(targetNeighbour);

        if (DEBUG && debugTarget == this) {
            if (oldPos.getX() == getPosition().getX() && oldPos.getY() == getPosition().getY()) {
                System.out.println("Agent didn't move at all!!");
            } else {
                System.out.println("Agent moved to " + getPosition().toString());
            }
            System.out.println("Agent's goal is " + targetNeighbour.getValue().toString());
        }

        //Update all the routes in Routing map.

        for (Route route : routingMap.values()) {
            route.add(getPosition());
        }
    }

    public void synchronizeNode(Node node) {

       /*Sync agents routing into the node*/
        if (DEBUG && debugTarget == this) {
            System.out.println("Agent synchronized with " + node.getPosition());
        }

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