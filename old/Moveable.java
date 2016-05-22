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

    public Moveable(Network network, Position position){
        super(network, position);
    }

    public abstract void move();


    private Deque<Position> targetPath = new ArrayDeque<>();
    private boolean flip = false;

    public synchronized void buildPathTo(BiValue<Direction, Position> biValue){
        Direction dir = biValue.getKey();
        targetPath = new ArrayDeque<>();
        Position p = getPosition();
        boolean flip = false;

        int counter = 0;
        while(!p.equals(biValue.getValue())) {
            p = p.clone();
            if(Math.abs(dir.getXDiff()) == 1 && Math.abs(dir.getYDiff()) == 1){
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
            targetPath.addLast(p);
            //System.out.println("Path: " + p);
            counter++;
            if(counter >= 1000){
                System.out.println("BuildPathTo While stuck");
            }
        }
    }

    public boolean walkPath(BiValue<Direction, Position> biValue){
        if(getPosition().equals(biValue.getValue())){
            return false;
        }
        Direction dir = biValue.getKey();
        Position p = getPosition().clone();
        if(Math.abs(dir.getXDiff()) == 1 && Math.abs(dir.getYDiff()) == 1){
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

        if(!targetPath.isEmpty()){
            Position p = targetPath.pop();
            setPosition(p);
            return true;
        }
        return false;
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
