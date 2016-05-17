package com.javagrupp16.ou3.entities;

import com.javagrupp16.ou3.Network;
import com.javagrupp16.ou3.Position;

/**
 * Created by Marcus on 2016-05-17.
 **/
public class Agent extends Moveable {

    private int maxSteps;

    public Agent(Network network, Position position, int maxSteps){
        super(network, position);
        this.maxSteps = maxSteps;
    }

    @Override
    public void move() {

    }

    public void synchronizeNode(Node node){

    }
}
