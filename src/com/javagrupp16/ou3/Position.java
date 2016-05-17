package com.javagrupp16.ou3;

/**
 * Created by Marcus on 2016-05-17.
 */
public class Position {

    private int x;
    private int y;

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
    public boolean equals(Object o){
        return true;
    }
}
