package com.javagrupp16.ou3;

import java.util.UUID;

/**
 * Created by Marcus on 2016-05-23.
 */
public class Path {

    private final UUID eventUUID;
    private final Position nextDest;
    private final int stepsToEvent;

    public Path(UUID uuid, Position nextDest, int stepsToEvent){
        this.eventUUID = uuid;
        this.nextDest = nextDest;
        this.stepsToEvent = stepsToEvent;
    }

    public boolean compareSteps(int steps){
        return steps < stepsToEvent;
    }

    public int getStepsToEvent(){
        return stepsToEvent;
    }

    public Position getNextDest(){
        return nextDest;
    }
}
