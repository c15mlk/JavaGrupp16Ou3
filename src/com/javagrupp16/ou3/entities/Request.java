package com.javagrupp16.ou3.entities;

import com.javagrupp16.ou3.Event;
import com.javagrupp16.ou3.Network;
import com.javagrupp16.ou3.Position;

import java.util.Stack;
import java.util.UUID;

/**
 * Created by Marcus on 2016-05-17.
 **/
public class Request extends Moveable {

    private Event info = null;
    private Position eventPosition = null, targetNeighbour;
    private Stack<Position> stack = new Stack<Position>();

    private int maxSteps;


    private UUID eventUUID;
    private Node sourceNode;

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
        this.targetNeighbour = network.randomItem(node.getNeighbours()).getPosition();
    }

    @Override
    public void move() {
        if(info != null){
            Position p = stack.pop();
            setPosition(p);
            if(getPosition().equals(sourceNode.getPosition())){
                sourceNode.receiveEvent(this);
                setComplete(true);
            }
            return;
        }
        if(eventPosition == null) {
            if (network.hasNode(getPosition())) {
                Node targetNode = network.getNode(getPosition());
                if (targetNode.routingMap.containsKey(eventUUID)) {
                    eventPosition = targetNode.routingMap.get(eventUUID);
                    moveTowards(eventPosition);
                } else {
                    targetNeighbour = network.randomItem(targetNode.getNeighbours()).getPosition();
                    moveTowards(targetNeighbour);
                }
            } else {
                moveTowards(targetNeighbour);
            }
        }else{
            moveTowards(eventPosition);
        }
        if (network.hasNode(getPosition())) {
            Node targetNode = network.getNode(getPosition());
            if(targetNode.eventsMap.containsKey(eventUUID)) {
                this.info = targetNode.eventsMap.get(eventUUID);
                setSteps(0);
                return;
            }
        }
        stack.add(getPosition());
    }

    public Event getInfo(){
        return info;
    }
}
