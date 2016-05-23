package com.javagrupp16.ou3;


/**
 * Created by Marcus on 2016-05-17.
 */
public class NetworkMain {

    public static void main(String[] args){
        final Network network = new Network(50,50,50,0.01);


        long time = System.currentTimeMillis();
        for(int i = 0; i < 10000; i++){
            network.timeTick();
        }
        long endTime = System.currentTimeMillis() - time;

        System.out.println("Ended in " + endTime/1000 + " seconds and with " + network.getEventIDList().size() + " events");
    }

}
