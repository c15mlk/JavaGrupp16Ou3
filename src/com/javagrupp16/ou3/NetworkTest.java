package com.javagrupp16.ou3;

import java.util.Random;

/**
 * Created by Marcus on 2016-05-17.
 */
public class NetworkTest {

    public static void main(String[] args){
        Network network = new Network(1,2,0,100);

        for(int i = 0; i < 440; i++){
            network.timeTick();
        }

    }

}
