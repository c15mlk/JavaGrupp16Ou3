package com.javagrupp16.ou3.entities;

import com.javagrupp16.ou3.Network;
import com.javagrupp16.ou3.Position;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by Marcus on 2016-06-02.
 */
public class RequestTest {
    private Network network;
    private UUID eventUUID;

    @Test
    public void checkSoRequestMovesWhenItShould() throws Exception {
        Node n = initNetwork(50,50,0);
        Request request = findRequest(n);

        for(int i = 0 ; i < 45 ; i++) {
            Position oldPos = request.getPosition();
            int steps = request.getSteps();
            network.timeTick();
            assertNotEquals(oldPos, request.getPosition());
            assertEquals(steps+1, request.getSteps());
        }
        assert(request.isComplete());
        for(int i = 0 ; i < 45 ; i++){
            Position oldPos = request.getPosition();
            int steps = request.getSteps();
            network.timeTick();
            assertEquals(oldPos, request.getPosition());
            assertEquals(steps, request.getSteps());
        }
    }

    @Test
    public void checkSoRequestFollowsPath() throws Exception {
        Node n = initNetwork(1,50,0);
        Request request = findRequest(n);

        Position pos = new Position(490,0);

        assert(network.hasNode(pos));
        Node n2 = getNode(pos);
        n2.detectEvent(eventUUID, network.getTime(), 100);

        for(int i = 0 ; i < 45 ; i++) {
            Position oldPos = request.getPosition();
            int steps = request.getSteps();
            network.timeTick();
            assertNotEquals(oldPos, request.getPosition());
            assertEquals(steps+1, request.getSteps());
        }
        assert(!request.isComplete()); //Request has found path
        while(!request.isComplete()){
            Position oldPos = request.getPosition();
            int steps = request.getSteps();
            network.timeTick();
            assertNotEquals(oldPos, request.getPosition());
            assertEquals(steps+1, request.getSteps());
        }


    }

    private Node initNetwork(int height, int width, double agentProb) throws Exception {
        network = new Network(height, width, agentProb, 100);

        for(int i = 0 ; i < 10 ; i++){
            network.timeTick();
        }

        Field f = Network.class.getDeclaredField("nodes");
        f.setAccessible(true);
        Map<Position, Node> map = (Map<Position, Node>) f.get(network);
        Node node = map.values().iterator().next();
        f.setAccessible(false);


        eventUUID = UUID.randomUUID();
        node.requestEvent(eventUUID, network.getTime());

        for(int i = 0 ; i < 1 ; i++){
            network.timeTick();
        }

        return node;
    }

    private Node getNode(Position pos) throws Exception {
        Field f = Network.class.getDeclaredField("nodes");
        f.setAccessible(true);
        Map<Position, Node> map = (Map<Position, Node>) f.get(network);
        return map.get(pos);
    }

    private Request findRequest(Node node) throws Exception {
        Request request = null;

        Field f = Node.class.getDeclaredField("moveableMap");
        f.setAccessible(true);
        Map<Moveable, Moveable> map2 = (Map<Moveable, Moveable>) f.get(node);
        for(Moveable m : map2.values()){
            if(m instanceof Request) {
                request = (Request) m;
                break;
            }
        }
        f.setAccessible(false);
        return request;
    }
}
