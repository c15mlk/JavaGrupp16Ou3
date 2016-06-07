package com.javagrupp16.ou3.entities;

import com.javagrupp16.ou3.Network;
import com.javagrupp16.ou3.Position;

import org.junit.Before;
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
    private Node node;
    private Request request;

    @Before
    public void setUp() throws Exception {
        network = new Network(50, 50, 0, 100);

        for(int i = 0 ; i < 10 ; i++){
            network.timeTick();
        }

        Field f = Network.class.getDeclaredField("nodes");
        f.setAccessible(true);
        Map<Position, Node> map = (Map<Position, Node>) f.get(network);
        node = map.values().iterator().next();
        f.setAccessible(false);

        node.requestEvent(UUID.randomUUID(), network.getTime());
        for(int i = 0 ; i < 1 ; i++){
            network.timeTick();
        }
        f = Node.class.getDeclaredField("moveableMap");
        f.setAccessible(true);
        Map<Moveable, Moveable> map2 = (Map<Moveable, Moveable>) f.get(node);
        for(Moveable m : map2.values()){
            if(m instanceof Request) {
                request = (Request) m;
            }
        }
        f.setAccessible(false);
    }

    @Test
    public void checkSoRequestMovesWhenItShould() {
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
}
