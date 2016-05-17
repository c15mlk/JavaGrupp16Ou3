package com.javagrupp16.ou3;

import com.javagrupp16.ou3.entities.Node;

import java.util.*;

/**
 * Created by Marcus on 2016-05-17.
 **/
public class Network {

    private Map<Position, Node> nodes = new HashMap<>();
    private List<UUID> eventIDList = new ArrayList<>();
    private int height, width, agentProb, eventProb, numberOfTicks, counter;
    public static final int AGENTMAXSTEPS = 50;
    public static final int REQUESTMAXSTEPS = 45;

    private final Random random = new Random();

    public Network(int height, int width, int agentProb, int eventProb){
        this.height = height;
        this.width = width;
        this.agentProb = agentProb;
        this.eventProb = eventProb;
        for(int x = 0 ; x < width ; x++){
            for(int y = 0 ; y < height ; y++){
                Position p = new Position(x*10,y*10);
                Node node = new Node(this, p);
                nodes.put(p,node);
            }
        }
    }

    public boolean hasNode(Position position){
        return nodes.containsKey(position);
    }

    public Node getNode(Position position){
        if(nodes.containsKey(position))
            return nodes.get(position);
        return null;
    }

    public int getAgentProb(){
        return agentProb;
    }

    public void timeTick(){
        for(Node n : nodes.values()) {
            if (chanceOf(eventProb)) {
                UUID uuid = UUID.randomUUID();
                eventIDList.add(uuid);
                n.detectEvent(uuid);
            }
            n.timeTick();
        }

        if(counter >= 400){
            for(int i = 0 ; i < 4 ; i++){
                int randomInt = random.nextInt(nodes.size());
                Node randomNode = randomItem(new ArrayList<Node>(nodes.values()));
                randomNode.requestEvent(randomItem(eventIDList));
            }
            counter = 0;
        }
        counter++;
        numberOfTicks++;
    }

    public int getTime(){
        return numberOfTicks;
    }

    public boolean chanceOf(int procent){
        int rng = random.nextInt(100);
        return procent > rng;
    }

    private <T> T randomItem(List<T> list){
        int randomInt = random.nextInt(list.size());
        return list.get(randomInt);
    }
}
