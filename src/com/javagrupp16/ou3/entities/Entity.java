package com.javagrupp16.ou3.entities;

import com.javagrupp16.ou3.Network;
import com.javagrupp16.ou3.Position;

/**
 * Created by Marcus on 2016-05-17.
 **/

public class Entity {
    protected Network network;
    private Position position;

    public Entity(Network network, Position position){
        this.network = network;
        this.position = position;
    }

    public Position getPosition(){
        return position;
    }

    public void setPosition(Position position){
        if(position.equals(getPosition())){
            throw new IllegalArgumentException("Attempting to move to same position");
        }
        int xDiff = Math.abs(getPosition().getX() - position.getX());
        int yDiff = Math.abs(getPosition().getY() - position.getY());
        if(xDiff > 10 || yDiff > 10)
            throw new IllegalArgumentException("xDiff " + xDiff + " yDiff " + yDiff);

        this.position = position;
    }

}
