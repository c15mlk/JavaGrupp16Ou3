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

        /*Check which direction to go*/
        int xDiff = getPosition().getX() - position.getX();
        int yDiff = getPosition().getY() - position.getY();

        Position xPos = getPosition().clone();
        double xDist = Double.MAX_VALUE;

        /*Create a x pos depending on which direction to go*/
        /*Check the distance between xPos the position to go to*/

        if(xDiff > 0){ //This position is more to the right
            xPos.setX(xPos.getX() - 1);
            xDist = distanceBetween(xPos,position);
        }else if(xDiff < 0){ //This position is more to the left
            xPos.setX(xPos.getX() + 1);
            xDist = distanceBetween(xPos,position);
        }

        Position yPos = getPosition().clone();
        double yDist = Double.MAX_VALUE;

        /*Create a y pos depending on which direction to go*/
        /*Check the distance between yPos the position to go to*/


        if(yDiff > 0){ //This position is above
            yPos.setY(yPos.getY() - 1);
            yDist = distanceBetween(yPos,position);
        }else if(xDiff < 0){ //This position is below
            yPos.setY(yPos.getY() + 1);
            yDist = distanceBetween(yPos,position);
        }

        /*Compare the distances and go to the closest position to the dest position*/

        if(xDist > yDist){
            setPosition(yPos);
        }else{
            setPosition(xPos);
        }
    }

    private double distanceBetween(Position from, Position to){
        double xs = Math.pow(from.getX() - to.getX(), 2);
        double ys = Math.pow(from.getY() - to.getY(), 2);
        double d = Math.sqrt(xs + ys);
        return d;
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
