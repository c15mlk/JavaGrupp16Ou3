package com.javagrupp16.ou3;

import java.util.ArrayDeque;
import java.util.Iterator;

/**
 * Created by Marcus on 2016-05-19.
 */
public class Route {
    private final ArrayDeque<Position> stack;

    private static Route TEST_ROUTE = null;

    public Route(ArrayDeque<Position> from){
        this.stack = from.clone();
    }

    public Route(){
        if(TEST_ROUTE == null){
            TEST_ROUTE = this;
        }
        this.stack = new ArrayDeque<>();
    }


    public ArrayDeque<Position> fromPosition(Position position) {

        if(stack.contains(position)) {
            ArrayDeque<Position> path = stack.clone();
            for (int i = 0; i < path.size(); i++) {
                Position p = path.pop();
                if (p.equals(position)) {
                    break;
                }
            }
            if(stack != null && !stack.isEmpty()){
                int xDiff = Math.abs(stack.peek().getX() - position.getX());
                int yDiff = Math.abs(stack.peek().getY() - position.getY());
                if(xDiff > 10 || yDiff > 10) {

                    System.out.println("Current: " + position);
                    for(int i = 0 ; i < stack.size() ; i++){
                        System.out.println(stack.pop());
                    }

                    throw new IllegalArgumentException("xDiff " + xDiff + " yDiff " + yDiff);
                }
            }
            return path;
        }
        if(stack != null && !stack.isEmpty()){
            int xDiff = Math.abs(stack.peek().getX() - position.getX());
            int yDiff = Math.abs(stack.peek().getY() - position.getY());
            if(xDiff > 10 || yDiff > 10) {

                System.out.println("Current: " + position);
                for(int i = 0 ; i < stack.size() ; i++){
                    System.out.println(stack.pop());
                }

                throw new IllegalArgumentException("xDiff " + xDiff + " yDiff " + yDiff);
            }
        }
        return stack.isEmpty() ? null : stack.clone();
    }

    public Route clone(){
        return new Route(stack);
    }

    public void add(Position p){
        stack.addFirst(p);
        if(this == TEST_ROUTE){
            System.out.println("Added pos " + p);
            int xDiff = Math.abs(stack.peek().getX() - p.getX());
            int yDiff = Math.abs(stack.peek().getY() - p.getY());
            if(xDiff > 10 || yDiff > 10)
                throw new IllegalArgumentException("xDiff " + xDiff + " yDiff " + yDiff);
        }
    }

    public ArrayDeque<Position> getStack(){
        return stack;
    }

    public int size(){
        return stack.size();
    }
}
