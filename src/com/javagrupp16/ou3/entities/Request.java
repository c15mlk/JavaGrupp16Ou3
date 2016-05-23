package com.javagrupp16.ou3.entities;

import com.javagrupp16.ou3.*;
import com.sun.org.apache.xpath.internal.SourceTree;

import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Marcus on 2016-05-17.
 **/
public class Request extends Moveable {


    private Event info = null;
    private ArrayDeque<Position> stack = new ArrayDeque<>();

    private int maxSteps;
    private final UUID eventUUID;
    private final Node sourceNode;

    private boolean onPath = false;


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
        stack.addFirst(getPosition());
    }

    @Override
    public void move() {

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

        /*Assert that the request is on a node*/
        if (network.hasNode(getPosition())) {
            Node targetNode = network.getNode(getPosition());
            /*if it is has a path to the event.*/
            Map<UUID,Path> routingMap = targetNode.getRoutingMap();
            if (routingMap.containsKey(eventUUID)) {
                /*Start moving according to that path*/
                onPath = true;
                walkTo(routingMap.get(eventUUID).getNextDest());

            } else {
                if(onPath){
                    throw new IllegalStateException("Found path but still walking towards neighbours");
                }
                /*Select a new random neighbour*/
                Direction dir = Randoms.randomItem(targetNode.getNeighbours());
                walkTowards(dir);
            }

            /*If we stand on a node and the node contains info on the event.*/
            /*Store the info and reset steps and return.*/
            targetNode = network.getNode(getPosition());
            if(targetNode.eventsMap.containsKey(eventUUID)) {
                this.info = targetNode.eventsMap.get(eventUUID);
                return;
            }
        }



        /*Add how we walked to the stack*/
        stack.addFirst(getPosition());

    }

    public void onRemove(){
        sourceNode.expectedInfo.remove(eventUUID);
    }

    public Event getInfo(){
        return info;
    }

    @Override
    public boolean isComplete(){
        if(getSteps() >= maxSteps && !onPath){
            return true;
        }else{
            return super.isComplete();
        }
    }

}

