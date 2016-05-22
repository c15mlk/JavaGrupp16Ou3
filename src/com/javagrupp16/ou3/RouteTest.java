package com.javagrupp16.ou3;

import org.junit.Before;
import org.junit.Test;

import java.util.Deque;

/**
 * Created by Marcus on 2016-05-22.
 */
public class RouteTest {

    private Route route;

    @Before
    public void setUp() throws Exception {
        route = new Route();
        route.add(new Position(0,1));
        route.add(new Position(0,2));
        route.add(new Position(1,2));
        route.add(new Position(2,2));
        route.add(new Position(2,3));
    }

    @Test
    public void testFromPositionMethod() {
        Deque<Position> deque = route.fromPosition(new Position(1,2));
        assert(deque.pop().equals(new Position(0,2)));
        assert(deque.pop().equals(new Position(0,1)));
        assert(deque.isEmpty());
    }

}
