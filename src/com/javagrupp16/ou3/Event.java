package com.javagrupp16.ou3;


import java.util.UUID;

/**
 * Created by Marcus on 2016-05-17.
 **/
public class Event {

    private int time;
    private UUID id;
    private Position position;

    public Event(UUID uuid, Position position, int time){
        id = uuid;
        this.position = position;
        this.time = time;
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

}
