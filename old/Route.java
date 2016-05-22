package com.javagrupp16.ou3;

import java.util.*;

/**
 * Created by Marcus on 2016-05-19.
 */
public class Route {
    private List<Position> positions = new ArrayList<Position>();
    private Deque<Position> stack = new ArrayDeque<>();
    private Network network;

    public Route(Network network, Deque<Position> from){
        this.network = network;
        Iterator<Position> iterator = from.iterator();
        while(iterator.hasNext()){
            stack.addLast(iterator.next());
        }
    }

    public Route(Network network){
        this.network = network;
    }

    public Deque<Position> fromPosition(Position position){
        if(stack.contains(position)){
            Iterator<Position> iterator = stack.iterator();
            boolean foundPos = false;
            Deque<Position> path = new ArrayDeque<>();
            while(iterator.hasNext()){
                Position p = iterator.next();
                if(!foundPos && p.equals(position)) {
                    foundPos = true;
                } else if(foundPos) {
                    if(!path.contains(p)) {
                        path.addLast(p);
                    }
                }
            }
            return path;
        }
        Iterator<Position> iterator = stack.iterator();
        Deque<Position> path = new ArrayDeque<>();
        while(iterator.hasNext()){
           path.addLast(iterator.next());
        }
        return path;
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
        stack.addFirst(p);
    }

    public int size(){
        return stack.size();
    }
}
