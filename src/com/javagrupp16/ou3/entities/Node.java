package com.javagrupp16.ou3.entities;

import com.javagrupp16.ou3.Event;
import com.javagrupp16.ou3.Network;
import com.javagrupp16.ou3.Position;
import javafx.geometry.Pos;

import java.util.*;

/**
 * Created by Marcus on 2016-05-17.
 **/
public class Node extends Entity {

    private Position position;
    private List<Node> neighbours= new ArrayList<Node>();
    private Map<UUID,Event> eventsMap = new HashMap<UUID,Event>();
    private Map<UUID,Position> routingMap = new HashMap<UUID,Position>();
    private List<Moveable> moveableList = new ArrayList<Moveable>();
    private Queue<Runnable> runnableQue = new ArrayDeque<Runnable>();

    public Node(Network network, Position position){

        super(network, position);
    }

    public void detectEvent(UUID uuid){

        Event event = new Event(uuid,getPosition(),network.getTime());
        eventsMap.put(uuid,event);
    }

    public void requestEvent(){

    }

}
