package com.javagrupp16.ou3.entities;

import com.javagrupp16.ou3.Event;
import com.javagrupp16.ou3.Network;
import com.javagrupp16.ou3.Position;
import com.javagrupp16.ou3.Randoms;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Stack;
import java.util.UUID;

/**
 * Created by Marcus on 2016-05-17.
 **/
public class Request extends Moveable {


    private Event info = null;
    private Position eventPosition = null, targetNeighbour;
    private Deque<Position> stack = new ArrayDeque<Position>();

    private int maxSteps;


    private UUID eventUUID;
    private Node sourceNode;

    /*Debug variables*/
    private static final boolean DEBUG = true;
    /*Debugga endast en Request gör det enklare att läsa*/
    private static Request debugTarget;


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
            this.targetNeighbour = Randoms.randomItem(sourceNode.getNeighbours()).getPosition();
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
        if(eventPosition == null) {
            if (network.hasNode(getPosition())) {
                Node targetNode = network.getNode(getPosition());
                if (targetNode.routingMap.containsKey(eventUUID)) {
                    eventPosition = targetNode.routingMap.get(eventUUID);
                    moveTowards(eventPosition);
                    if(DEBUG && debugTarget == this){
                        System.out.println("Request found location of event " + eventPosition.toString());
                    }
                } else if(getPosition().equals(targetNeighbour)) {
                    targetNeighbour = Randoms.randomItem(targetNode.getNeighbours()).getPosition();
                    moveTowards(targetNeighbour);
                    if(DEBUG && debugTarget == this){
                        System.out.println("*****");
                        System.out.println("Request changed target neighbour to " + targetNeighbour.toString());
                        System.out.println("*****");
                    }
                } else {
                    moveTowards(targetNeighbour);
                }
            } else {
                moveTowards(targetNeighbour);
            }
        }else{
            moveTowards(eventPosition);
        }

        if(DEBUG && debugTarget == this){
            if(oldPos.getX() == getPosition().getX() && oldPos.getY() == getPosition().getY()){
                System.out.println("Request didn't move at all!!");
            }else {
                System.out.println("Request moved to " + getPosition().toString());
            }
            if(eventPosition != null){
                System.out.println("Request's goal is " + eventPosition.toString());
            }else{
                System.out.println("Request's goal is " + targetNeighbour.toString());
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
        setSteps(getSteps()+1);
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
