package com.javagrupp16.ou3;

/**
 * Created by Mirrepirre on 2016-05-19.
 */
public enum Direction {
    UP(0,1),DOWN(0,-1),RIGHT(1,0),
    LEFT(-1,0),BOTTOM_RIGHT(1,-1),
    BOTTOM_LEFT(-1,-1), TOP_RIGHT(1,1),
    TOP_LEFT(-1,1);


    private int xDiff,yDiff;

    private Direction(int x, int y){
        this.xDiff = x;
        this.yDiff = y;
    }

    public Position toPosition(Position from, int xMod, int yMod){
        return new Position(from.getX() + (xDiff * xMod), from.getY() + (yDiff * yMod));
    }

    public int getXDiff(){
        return xDiff;
    }

    public int getYDiff(){
       return yDiff;
    }
}
