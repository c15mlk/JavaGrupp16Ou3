package com.javagrupp16.ou3.entities;

import com.javagrupp16.ou3.Event;
import com.javagrupp16.ou3.Network;
import com.javagrupp16.ou3.Position;


import java.util.*;

/**
 * Created by Marcus on 2016-05-17.
 **/
public class Node extends Entity {

    private List<Node> neighbours= new ArrayList<Node>();
    protected Map<UUID,Event> eventsMap = new HashMap<UUID,Event>();
    protected Map<UUID,Position> routingMap = new HashMap<UUID,Position>();
    private List<Moveable> moveableList = new ArrayList<Moveable>();
    private Deque<Runnable> runnableQue = new ArrayDeque<Runnable>();

    public Node(Network network, Position position){
        super(network, position);
    }

    public void detectEvent(UUID uuid){
        Event event = new Event(uuid,getPosition(),network.getTime());
        eventsMap.put(uuid,event);
        if(network.chanceOf(network.getAgentProb())) {
            Agent agent = new Agent(network, getPosition(),Network.AGENTMAXSTEPS);
            moveableList.add(agent);
        }
    }

    public void requestEvent(UUID uuid){
        Request request = new Request(network,getPosition(),uuid,this,Network.REQUESTMAXSTEPS);
        runnableQue.add(new Runnable(){
            @Override
            public void run(){
                moveableList.add(request);
            }
        });
    }

    public void timeTick(){
        for(Moveable moveable : moveableList) {
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

    public void receiveEvent(Request request){
        runnableQue.add(new Runnable(){
            @Override
            public void run(){
                System.out.println(request.getInfo().toString());
            }
        });
        request.setComplete(true);
    }

    public List<Node> getNeighbours(){
        return neighbours;
    }

    public void addNeighbourAt(int xAdd, int yAdd){
        Position p = new Position(getPosition().getX() + xAdd, getPosition().getY() + yAdd);
        if(network.hasNode(p)){
            neighbours.add(network.getNode(p));
        }
    }
}
