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


    private UUID eventUUID;
    private Node sourceNode;

    /*Debug variables*/
    private static final boolean DEBUG = true;
    /*Debugga endast en Request gör det enklare att läsa*/
    protected static Request debugTarget;


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

        if(DEBUG && debugTarget == null){
            debugTarget = this;
            System.out.println("Request spawned at " + getPosition().toString());
        }
    }

    @Override
    public void move() {

        Position oldPos = getPosition();

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
                if (DEBUG && debugTarget == this) {
                    System.out.println("Request is backtracking to " + getPosition().toString());
                }
                if (getPosition().equals(sourceNode.getPosition())) {
                    if (DEBUG && debugTarget == this) {
                        System.out.println("Request returned with information");
                    }
                    sourceNode.receiveEvent(this);
                    setComplete(true);
                }
            }
            return;
        }

        /*If we don't know where the event happened
         *  Check if the request is on a node
         *      if it is has a path to the event.
         *      Else check if we stand on our target neighbour and select a new random neighbour.
         *      Else move towards our target neighbour.
         *  Else move towards our target neighbour.
         *Else if we know where the event happened
         *move towards the event.
         */
        if(pathToEvent == null) {
            if (network.hasNode(getPosition())) {
                Node targetNode = network.getNode(getPosition());
                if (targetNode.routingMap.containsKey(eventUUID)) {
                    pathToEvent = targetNode.routingMap.get(eventUUID).fromPosition(getPosition());
                    Position p = pathToEvent.pop();
                    if (p.equals(getPosition())) {
                        p = pathToEvent.pop();
                    }
                    setPosition(p);
                    if(DEBUG && debugTarget == this){
                        System.out.println("Request found path to event");
                    }
                } else if(!walkPath(targetNeighbour)){
                    targetNeighbour = Randoms.randomItem(targetNode.getNeighbours());
                    walkPath(targetNeighbour);
                    setSteps(getSteps() + 1);
                    if(DEBUG && debugTarget == this){
                        System.out.println("Request changed target neighbour to " + targetNeighbour.getValue().toString());
                    }
                }
            } else {
                walkPath(targetNeighbour);
            }
        }else{
            if (network.hasNode(getPosition())) {
                Node targetNode = network.getNode(getPosition());
                ArrayDeque<Position> path = targetNode.routingMap.get(eventUUID).fromPosition(getPosition());
                if(path != null && path.size() < pathToEvent.size()){
                    if(DEBUG && debugTarget == this) {
                        System.out.println("Request changed path to event since it found a better path." +
                                (pathToEvent.size() - path.size()) + " difference.");
                    }
                    pathToEvent = path;
                }
            }
            if(!pathToEvent.isEmpty()) {
                Position p = pathToEvent.pop();
                if (p.equals(getPosition())) {
                    p = pathToEvent.pop();
                }
                setPosition(p);
            }
        }

        if(DEBUG && debugTarget == this){
            if(oldPos.getX() == getPosition().getX() && oldPos.getY() == getPosition().getY()){
                System.out.println("Request didn't move at all!!");
            }else {
                System.out.println("Request moved to " + getPosition().toString());
            }
            if(pathToEvent != null){
                System.out.println("Request's is following the path to event: " + pathToEvent.size());
            }else{
                System.out.println("Request's goal is " + targetNeighbour.getValue().toString());
            }
        }

        /*If we stand on a node and the node contains info on the event.
         *Store the info and reset steps and return.
         */
        if (network.hasNode(getPosition())) {
            Node targetNode = network.getNode(getPosition());
            if(targetNode.eventsMap.containsKey(eventUUID)) {
                if(DEBUG && debugTarget == this){
                    System.out.println("Request found information on the event.");
                }
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

