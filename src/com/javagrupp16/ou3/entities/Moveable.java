package com.javagrupp16.ou3.entities;

import com.javagrupp16.ou3.BiValue;
import com.javagrupp16.ou3.Direction;
import com.javagrupp16.ou3.Network;
import com.javagrupp16.ou3.Position;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Created by Marcus on 2016-05-17.
 **/
public abstract class Moveable extends Entity {

    private int steps;
    private boolean complete = false;
    private boolean flip = false;

    public Moveable(Network network, Position position){
        super(network, position);
    }

    public abstract void move();


    public boolean walkPath(BiValue<Direction, Position> biValue){
        if(getPosition().equals(biValue.getValue())){
            return false;
        }

        Direction dir = biValue.getKey();
        Position p = getPosition().clone();

        if(dir.getXDiff() != 0 && dir.getYDiff() != 0){
            if(flip){
                p.setX(p.getX() + dir.getXDiff());
            }else{
                p.setY(p.getY() + dir.getYDiff());
            }
            flip = !flip;
        }else {
            p.setX(p.getX() + dir.getXDiff());
            p.setY(p.getY() + dir.getYDiff());
        }
        setPosition(p);
        return true;
    }

    public void setSteps(int i){
        this.steps = i;
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
