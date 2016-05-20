package com.javagrupp16.ou3.entities;

import com.javagrupp16.ou3.*;


import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Marcus on 2016-05-17.
 **/
public class Node extends Entity {

    private List<BiValue<Direction,Position>> neighbours= new ArrayList<>();
    protected Map<UUID,Event> eventsMap = new HashMap<UUID,Event>();
    protected Map<UUID,Deque<Position>> routingMap = new HashMap<>();
    private List<Moveable> moveableList = new CopyOnWriteArrayList<>(); //Concurrency problems need CopyOnWriteArrayList
    private Deque<Runnable> runnableQue = new ArrayDeque<Runnable>();


    public Node(Network network, Position position){
        super(network, position);
    }

    public void detectEvent(UUID uuid){
        //System.out.println("Event happened at " + getPosition());
        Event event = new Event(uuid,getPosition(),network.getTime());
        eventsMap.put(uuid,event);
        if(Randoms.chanceOf(network.getAgentProb())) {
            Agent agent = new Agent(network,Network.AGENT_MAXSTEPS,this, uuid);
            moveableList.add(agent);
        }
    }

    public boolean requestEvent(UUID uuid){
        if(eventsMap.containsKey(uuid)){
            return false;
        }
        final Request request = new Request(network,getPosition(),uuid,this,Network.REQUEST_MAXSTEPS);
        runnableQue.add(new Runnable(){
            @Override
            public void run(){
                moveableList.add(request);
            }
        });
        return true;
    }

    public void timeTick(){
        for (Moveable moveable : moveableList) {
            if (moveable.isComplete()) {
                moveableList.remove(moveable);
            } else {
                moveable.move();
            }
        }
        if (!runnableQue.isEmpty()){
            runnableQue.pop().run();
        }
    }

    public void receiveEvent(final Request request){
        runnableQue.add(new Runnable(){
            @Override
            public void run(){
                System.out.println(request.getInfo().toString());
            }
        });
        request.setComplete(true);
    }

    public List<BiValue<Direction,Position>> getNeighbours(){
        return neighbours;
    }

    public void addNeighbourAt(Direction direction){
        Position p = direction.toPosition(getPosition(), 10, 10);
        if(network.hasNode(p)){
            neighbours.add(new BiValue<>(direction, p));
        }
    }
}
