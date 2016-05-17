package com.javagrupp16.ou3;

import com.javagrupp16.ou3.entities.Moveable;

import java.util.UUID;

/**
 * Created by Marcus on 2016-05-17.
 **/
public class Event extends Moveable{

    private int time;
    private UUID id;
    private Position position;

    public Event(){
        super(network, position);
    }

    public UUID getId(){
    return id;
    }

    public int getTime(){
    return time;
    }

    public Position getPosition(){
    return position;
    }

    @Override
    public void move() {
    }
}
