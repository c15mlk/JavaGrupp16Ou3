package com.javagrupp16.ou3.entities;

import com.javagrupp16.ou3.Direction;
import com.javagrupp16.ou3.Network;
import com.javagrupp16.ou3.Position;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

/**
 * Created by Marcus on 2016-06-02.
 */
public class RequestTest {
    private Network network;
    private Node node;
    private Request request;

    @Before
    public void setUp() throws Exception {
        network = new Network(50, 50, 50, 0.1);
        node = new Node(new Position(0,0));
        request = new Request(UUID.randomUUID(), node, 45);
    }

    @Test
    public void checkSoRequestMoves() {
        for(int i = 0 ; i < 45 ; i++) {
            request.move(network);
        }

    }

    @Test
    public void checkSoEventsHappen() {

    }
}
