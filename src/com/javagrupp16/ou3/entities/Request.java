package com.javagrupp16.ou3.entities;

import com.javagrupp16.ou3.Network;
import com.javagrupp16.ou3.Position;

/**
 * Created by Marcus on 2016-05-17.
 */
public class Request extends Moveable {

    public Request(Network network, Position position){
        super(network, position);
    }

    @Override
    public void move() {

    }
}
