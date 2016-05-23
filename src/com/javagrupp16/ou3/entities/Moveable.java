package com.javagrupp16.ou3.entities;

import com.javagrupp16.ou3.BiValue;
import com.javagrupp16.ou3.Direction;
import com.javagrupp16.ou3.Network;
import com.javagrupp16.ou3.Position;

/**
 * Created by Marcus on 2016-05-17.
 **/
public abstract class Moveable extends Entity {

    private int steps;
    private boolean complete = false;

    public Moveable(Network network, Position position){
        super(network, position);
    }

    public abstract void move();


    public boolean walkTowards(Direction dir){
        Position target = dir.toPosition(getPosition(),10,10);

        if(getPosition().equals(target)){
            System.out.println("Didn't move");
            return false;
        }
        setPosition(target);
        steps++;
        return true;
    }

    public boolean walkTo(Position position){
        if(getPosition().equals(position)){
            System.out.println("Didn't move");
            return false;
        }
        setPosition(position);
        steps++;
        return true;
    }

    public int getSteps(){
        return steps;
    }

    public boolean isComplete(){
        return complete;
    }

    public void setComplete(boolean b){
        this.complete = b;
    }

}
