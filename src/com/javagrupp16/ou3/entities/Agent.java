package com.javagrupp16.ou3.entities;

import com.javagrupp16.ou3.*;

import java.util.*;
import java.util.Map.Entry;

/**
 * Created by Marcus on 2016-05-17.
 **/
public class Agent extends Moveable {

    private int maxSteps;
    private Map<Position, Boolean> visitedNodeMap = new HashMap<>();
    private Map<UUID, Route> routingMap = new HashMap<>();
    private Node sourceNode;
    private Position targetNeighbour = null;

    /*Debug variables*/
    private static final boolean DEBUG = true;
    /*Debugga endast en Agent gör det enklare att läsa*/
    private static Agent debugTarget;

    public Agent(Network network,int maxSteps, Node node, UUID eventID){
        super(network, node.getPosition());
        this.maxSteps = maxSteps;
        this.sourceNode = node;
        Route route = new Route();
        route.add(node.getPosition());
        routingMap.put(eventID, route);

        if(DEBUG && debugTarget == null){
            debugTarget = this;
            System.out.println("Agent spawned at " + getPosition().toString());
        }
    }

    @Override
    public void move() {
        Position oldPos = getPosition();

        if(targetNeighbour == null){
            this.targetNeighbour = Randoms.randomItem(sourceNode.getNeighbours()).getPosition();
        }

        //Random move towards unvisisted Node similar to Request

        if(getPosition().equals(targetNeighbour)) {
            visitedNodeMap.put(targetNeighbour, true);
            Node targetNode = network.getNode(targetNeighbour);
            synchronizeNode(targetNode);
            for(int i = 0 ; i < 8 ; i++){
                targetNeighbour = Randoms.randomItem(targetNode.getNeighbours()).getPosition();
                if(!visitedNodeMap.containsKey(targetNeighbour)){
                    break;
                }
            }

            moveTowards(targetNeighbour);
            setSteps(getSteps()+1);
            if (DEBUG && debugTarget == this) {
                System.out.println("*****");
                System.out.println("Agent changed target neighbour to " + targetNeighbour.toString());
                System.out.println("*****");
            }
        }else{
            moveTowards(targetNeighbour);
        }

        if(DEBUG && debugTarget == this){
            if(oldPos.getX() == getPosition().getX() && oldPos.getY() == getPosition().getY()){
                System.out.println("Agent didn't move at all!!");
            }else {
                System.out.println("Agent moved to " + getPosition().toString());
            }
            System.out.println("Agent's goal is " + targetNeighbour.toString());
        }

        //Update all the routes in Routing map.

        for(Route route : routingMap.values()){
            int size = route.size();
            route.add(getPosition());
            assert(size != route.size());
        }
    }

    public void synchronizeNode(Node node){
       /*Sync agents routing into the node*/
       for(Entry<UUID, Route> entry : routingMap.entrySet()){
           if(!node.routingMap.containsKey(entry.getKey())) {
               node.routingMap.put(entry.getKey(), entry.getValue().fromPosition(node.getPosition()));
           }else {
               Deque<Position> path = entry.getValue().fromPosition(node.getPosition());
               if(path.size() < node.routingMap.get(entry.getKey()).size()){
                   node.routingMap.put(entry.getKey(), path);
               }
           }
       }

       /*Sync nodes routing into this agent*/
       for(Entry<UUID, Deque<Position>> entry : node.routingMap.entrySet()){
           if(!routingMap.containsKey(entry.getKey())) {
               routingMap.put(entry.getKey(), new Route(entry.getValue()));
           }else {
               Route route = routingMap.get(entry.getKey());
               if(entry.getValue().size() < route.size())
                   routingMap.put(entry.getKey(), new Route(entry.getValue()));
           }
       }
    }

    @Override
    public boolean isComplete(){
        if(getSteps() >= maxSteps){
            return true;
        }
        return false;
    }

}
