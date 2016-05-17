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
    private Stack stack = new Stack();

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
    }

    @Override
    public void move() {

    }

    public Event getInfo(){
        return info;
    }
}
