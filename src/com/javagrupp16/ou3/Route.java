package com.javagrupp16.ou3;

import java.util.*;

/**
 * Created by Marcus on 2016-05-19.
 */
public class Route {
    private Deque<Position> stack;

    public Route(Deque<Position> from){
        this.stack = from;
    }

    public Route(){
        this.stack = new ArrayDeque<>();
    }

    public Deque<Position> fromPosition(Position position){
        if(stack.contains(position)){
            Iterator<Position> iterator = stack.iterator();
            boolean foundPos = false;
            Deque<Position> path = new ArrayDeque<>();
            while(iterator.hasNext()){
                Position p = iterator.next();
                if(!foundPos && p.equals(position)){
                    foundPos = true;
                }else if(foundPos){
                    path.addFirst(p);
                }
            }
            return path.isEmpty() ? null : path;
        }
        return null;
    }

    public void add(Position p){
        stack.addFirst(p);
    }

    public int size(){
        return stack.size();
    }
}
