package com.javagrupp16.ou3.entities;

import com.javagrupp16.ou3.Network;
import com.javagrupp16.ou3.Position;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;

/**
 * Created by Marcus on 2016-05-17.
 **/
public class Agent extends Moveable {

    private int maxSteps;
    private Map<Position, Boolean> visitedMap = new HashMap<>();
    private Map<UUID, Position> routingMap = new HashMap<>();


    public Agent(Network network, Position position, int maxSteps){
        super(network, position);
        this.maxSteps = maxSteps;
    }

    @Override
    public void move() {

    }

    public void synchronizeNode(Node node){
       /*Sync agents routing into the node*/
       for(Entry<UUID, Position> entry : routingMap.entrySet()){
            node.routingMap.putIfAbsent(entry.getKey(), entry.getValue());
       }

       /*Sync nodes routing into this agent*/
       for(Entry<UUID, Position> entry : node.routingMap.entrySet()){
           routingMap.putIfAbsent(entry.getKey(), entry.getValue());
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
