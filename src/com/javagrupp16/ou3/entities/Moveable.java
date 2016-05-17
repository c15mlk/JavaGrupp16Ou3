package com.javagrupp16.ou3.entities;

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

    public void moveTowards(Position position){
        int xDiff = getPosition().getX() - position.getX();
        int yDiff = getPosition().getY() - position.getY();

        Position p = getPosition();

        if(xDiff > 0){ //This position is more to the right
            p = getPosition().addX(-1);
        }else if(xDiff < 0){ //This position is more to the left
            p = getPosition().addX(1);
        }else if(yDiff > 0){ //This position is above
            p = getPosition().addY(-1);
        }else if(xDiff < 0){ //This position is below
            p = getPosition().addY(1);
        }

        setPosition(p);
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
