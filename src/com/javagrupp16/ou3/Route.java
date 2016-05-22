package com.javagrupp16.ou3;

import java.util.ArrayDeque;

/**
 * Created by Marcus on 2016-05-19.
 */
public class Route {
    private ArrayDeque<Position> stack = new ArrayDeque<>();

    public Route(ArrayDeque<Position> from){
        this.stack = from.clone();
    }

    public Route(){

    }

    public ArrayDeque<Position> fromPosition(Position position){
        if(stack.contains(position)){
            ArrayDeque<Position> path = stack.clone();
            for(int i = 0 ; i < path.size() ; i++){
                if(path.pop().equals(position)){
                    break;
                }
            }
            return path;
        }
        return stack.clone();
    }

    /*public ArrayDeque<Position> getStack(){
        return stack;
    }*/

    public Route clone(){
        return new Route(stack);
    }

    public void add(Position p){
        stack.addFirst(p);
    }

    public int size(){
        return stack.size();
    }
}
