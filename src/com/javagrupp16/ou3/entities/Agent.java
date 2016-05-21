package com.javagrupp16.ou3.entities;

import com.javagrupp16.ou3.*;

import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
    private static final boolean DEBUG = false;
    /*Debugga endast en Agent gör det enklare att läsa*/
    private static Agent debugTarget;

    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    public Agent(Network network,int maxSteps, Node node, UUID eventID){
        super(network, node.getPosition());
        this.maxSteps = maxSteps;
        this.sourceNode = node;
        Route route = new Route(network);
        route.add(node.getPosition());
        routingMap.put(eventID, route);

        if(DEBUG && debugTarget == null){
            debugTarget = this;
            System.out.println("Agent spawned at " + getPosition().toString());
        }
    }

    @Override
    public void move() {
        long time = System.currentTimeMillis();

        Position oldPos = getPosition();

        if(targetNeighbour == null){
            this.targetNeighbour = Randoms.randomItem(sourceNode.getNeighbours());
        }

        //Random move towards unvisisted Node similar to Request

        if(getPosition().equals(targetNeighbour.getValue())) {
            visitedNodeMap.put(targetNeighbour.getValue(), true);
            Node targetNode = network.getNode(targetNeighbour.getValue());
            synchronizeNode(targetNode);
            for(int i = 0 ; i < 8 ; i++){
                targetNeighbour = Randoms.randomItem(targetNode.getNeighbours());
                if(!visitedNodeMap.containsKey(targetNeighbour.getValue())){
                    break;
                }
            }

            setSteps(getSteps()+1);
            if (DEBUG && debugTarget == this) {
                System.out.println("Agent changed target neighbour to " + targetNeighbour.getValue().toString());
            }
        }

        walkPath(targetNeighbour);

        if(DEBUG && debugTarget == this){
            if(oldPos.getX() == getPosition().getX() && oldPos.getY() == getPosition().getY()){
                System.out.println("Agent didn't move at all!!");
            }else {
                System.out.println("Agent moved to " + getPosition().toString());
            }
            System.out.println("Agent's goal is " + targetNeighbour.getValue().toString());
        }

        //Update all the routes in Routing map.

        Position clone = getPosition().clone();
        for(Route route : routingMap.values()){
            route.add(clone);
        }

        long endTime = System.currentTimeMillis() - time;
        System.out.println("Agent step took " + endTime + " millis");
    }

    public synchronized void synchronizeNode(Node node){

       /*Sync agents routing into the node*/
       if(DEBUG && debugTarget == this) {
            System.out.println("Agent synchronized with " + node.getPosition());
       }

       List<BiValue<UUID, Deque<Position>>> list = new ArrayList<>();
       for(Entry<UUID, Route> entry : routingMap.entrySet()){
           if(!node.routingMap.containsKey(entry.getKey())) {
               list.add(new BiValue<>(entry.getKey(),entry.getValue().fromPosition(node.getPosition())));
           }else if(entry.getKey() != null && entry.getValue() != null) {
               int pathSize = entry.getValue().sizeFrom(node.getPosition());
               if(pathSize != -1 && pathSize < node.routingMap.get(entry.getKey()).size()){
                   Deque<Position> path = entry.getValue().fromPosition(node.getPosition());
                   if(path != null && !path.isEmpty()) {
                       list.add(new BiValue<>(entry.getKey(), path));
                   }
               }
           }
       }

       /*Sync nodes routing into this agent*/
       for(Entry<UUID, Deque<Position>> entry : node.routingMap.entrySet()) {
           if (!routingMap.containsKey(entry.getKey())) {
               routingMap.put(entry.getKey(), new Route(network, entry.getValue()));
           } else {
               Route route = routingMap.get(entry.getKey());
               if (entry.getValue().size() < route.size())
                   routingMap.put(entry.getKey(), new Route(network, entry.getValue()));
           }
       }

       for(BiValue<UUID, Deque<Position>> entry : list){
           node.routingMap.put(entry.getKey(), entry.getValue());
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
