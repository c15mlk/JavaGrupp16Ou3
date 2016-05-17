package com.javagrupp16.ou3.entities;

import com.javagrupp16.ou3.Network;
import com.javagrupp16.ou3.Position;

/**
 * Created by Marcus on 2016-05-17.
 */
public abstract class Moveable extends Entity {

    private int steps;

    public Moveable(Network network, Position position){
        super(network, position);
    }

    public abstract void move();

    public void step(){
        steps++;
    }

    public int getSteps(){
        return steps;
    }

}