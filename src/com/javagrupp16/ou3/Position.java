package com.javagrupp16.ou3;

/**
 * Created by Marcus on 2016-05-17.
 **/
public class Position {

    private final int x;
    private final int y;

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

    @Override
    public boolean equals(Object o){
        if(o instanceof Position) {
            Position p = (Position) o;
            return x == p.x && y == p.y;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }

    @Override
    public String toString(){
        return "x: " + x + " y:" + y;
    }
}
