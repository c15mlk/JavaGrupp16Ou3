package com.javagrupp16.ou3.entities;

import com.javagrupp16.ou3.*;

import java.util.ArrayDeque;
import java.util.UUID;

/**
 * Created by Marcus on 2016-05-17.
 **/
public class Request extends Moveable {


    private Event info = null;
    private BiValue<Direction,Position> targetNeighbour;
    private ArrayDeque<Position> stack = new ArrayDeque<Position>();
    private ArrayDeque<Position> pathToEvent = null;

    private int maxSteps;
    private final UUID eventUUID;
    private final Node sourceNode;


    /**
     *
     * @param network
     * @param position
     * @param uuid
     * @param node
     */
    public Request(Network network, Position position, UUID uuid, Node node, int maxSteps){
        super(network, position);
        this.maxSteps = maxSteps;
        this.eventUUID = uuid;
        this.sourceNode = node;
    }

    @Override
    public void move() {

        /*If we don't have a selected neighbour we want to select a random one to go to.
         *Probably only called at the first move() call.*/
        if(targetNeighbour == null){
            stack.addFirst(getPosition());
            this.targetNeighbour = Randoms.randomItem(sourceNode.getNeighbours());
        }

        /*If we have info on the event
         *We want to go back the way we walked to get the info.*/
        if(info != null){
            if(!stack.isEmpty()) {
                Position p = stack.pop();
                setPosition(p);
                if (getPosition().equals(sourceNode.getPosition())) {
                    sourceNode.receiveEvent(this);
                    setComplete(true);
                }
            }
            return;
        }

        /*If we don't know where the event happened*/
        if(pathToEvent == null) {
            /*Check if the request is on a node*/
            if (network.hasNode(getPosition())) {
                Node targetNode = network.getNode(getPosition());
                /*if it is has a path to the event.*/
                if (targetNode.routingMap.containsKey(eventUUID)) {
                    /*Start moving according to that path*/
                    pathToEvent = targetNode.routingMap.get(eventUUID).fromPosition(getPosition());
                    Position p = pathToEvent.pop();
                    if (p.equals(getPosition())) {
                        p = pathToEvent.pop();
                    }
                    setPosition(p);
                }
                /*Else it's likely we have reached our target neighbour*/
                else if(!walkPath(targetNeighbour)){
                    /*Select a new random neighbour*/
                    targetNeighbour = Randoms.randomItem(targetNode.getNeighbours());
                    walkPath(targetNeighbour);
                    setSteps(getSteps() + 1);
                }
            }
            /*Else we are not on a node*/
            else {
                /*Move towards our target neighbour*/
                walkPath(targetNeighbour);
            }
        }
        /*Then we have our path to the event*/
        else{
            /*Check if we stand on a node*/
            if (network.hasNode(getPosition())) {
                /*Check if the node knows a better path and change to that*/
                Node targetNode = network.getNode(getPosition());
                ArrayDeque<Position> path = targetNode.routingMap.get(eventUUID).fromPosition(getPosition());
                if(path != null && path.size() < pathToEvent.size()){
                    pathToEvent = path;
                }
            }
            /*Move according to our path*/
            if(!pathToEvent.isEmpty()) {
                Position p = pathToEvent.pop();
                if (p.equals(getPosition())) {
                    p = pathToEvent.pop();
                }
                setPosition(p);
            }
        }

        /*If we stand on a node and the node contains info on the event.*/
        /*Store the info and reset steps and return.*/
        if (network.hasNode(getPosition())) {
            Node targetNode = network.getNode(getPosition());
            if(targetNode.eventsMap.containsKey(eventUUID)) {
                this.info = targetNode.eventsMap.get(eventUUID);
                setSteps(0);
                return;
            }
        }

        /*Add how we walked to the stack and increase step counter by one.*/
        stack.addFirst(getPosition());

    }

    public Event getInfo(){
        return info;
    }

    @Override
    public boolean isComplete(){
        if(getSteps() >= maxSteps){
            return true;
        }else{
            return super.isComplete();
        }
    }

}

