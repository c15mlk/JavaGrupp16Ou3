package com.javagrupp16.ou3;

/**
 * Created by Marcus on 2016-05-17.
 **/
public class Position {

    private int x;
    private int y;

    /**
     * Creates a position and sets its x and y value.
     * @param x value for x position
     * @param y value for y position
     */
    public Position(int x, int y){
        this.x = x;
        this.y = y;
    }
    public int getX(){
        return this.x;
    }
    public int getY(){
        return this.y;
    }

    public void setX(int x){
        this.x = x;
    }
    public void setY(int y){
        this.y = y;
    }
    public boolean equals(Object o){
        if(o instanceof Position) {
            return true;
        }
        return true;
    }
}
