package com.javagrupp16.ou3;

import java.util.ArrayDeque;

/**
 * TODO Gör denna till riktiga Route klassen då den lär vara effektivare i minne.
 * Created by Marcus on 2016-05-22.
 */
public class RouteV2 {
    private final ArrayDeque<BiValue<Direction, Position>> stack;

    public RouteV2(ArrayDeque<BiValue<Direction, Position>> from){
        this.stack = from.clone();
    }

    public RouteV2(){
        this.stack = new ArrayDeque<>();
    }

    public ArrayDeque<BiValue<Direction, Position>> fromPosition(Position position) {
        if (stack.contains(position)) {
            ArrayDeque<BiValue<Direction, Position>> path = stack.clone();
            for (int i = 0; i < path.size(); i++) {
                if (path.pop().getValue().equals(position)) {
                    break;
                }
            }
            return path;
        }
        return stack.clone();
    }

    public RouteV2 clone(){
        return new RouteV2(stack);
    }

    public void add(Direction direction, Position position){
        stack.addFirst(new BiValue<>(direction, position));
    }

    public int size(){
        return stack.size();
    }

}
