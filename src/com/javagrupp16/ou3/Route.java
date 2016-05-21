package com.javagrupp16.ou3;

import java.util.*;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by Marcus on 2016-05-19.
 */
public class Route {
    private Deque<Position> stack = new ArrayDeque<>();
    private Network network;

    public Route(Network network, Deque<Position> from){
        this.network = network;
        Iterator<Position> iterator = from.iterator();
        while(iterator.hasNext()){
            stack.addLast(network.getCachedClone(iterator.next()));
        }
    }

    public Route(Network network){
        this.network = network;
    }

    public Deque<Position> fromPosition(Position position){
        if(stack.contains(position)){
            //System.out.println("Creating Path from Route starting from " + position);
            Iterator<Position> iterator = stack.iterator();
            boolean foundPos = false;
            Deque<Position> path = new ArrayDeque<>();
            while(iterator.hasNext()){
                Position p = iterator.next();
                if(!foundPos && p.equals(position)) {
                    foundPos = true;
                } else if(foundPos) {
                    if(!path.contains(p)) {
                        path.addLast(network.getCachedClone(p));
                    }
                    //System.out.println("Put " + p + " into path");
                }
            }
            return path.isEmpty() ? null : path;
        }
        return null;
    }

    public int sizeFrom(Position position){
        if(stack.contains(position)){
            int i = 0;
            Iterator<Position> iterator = stack.iterator();
            boolean foundPos = false;
            while(iterator.hasNext()){
                Position p = iterator.next();
                if(!foundPos && p.equals(position)) {
                    foundPos = true;
                } else if(foundPos) {
                    i++;
                }
            }
            return i;
        }
        return -1;
    }

    public void add(Position p){
        stack.addFirst(network.getCachedClone(p));
    }

    public int size(){
        return stack.size();
    }
}
