package com.javagrupp16.ou3;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URISyntaxException;

/**
 * Created by Marcus on 2016-05-17.
 */
public class NetworkTest {

    public static void main(String[] args){
        final Network network = new Network(50,50,50,0.01);

        try {
            File requestLog = new File(Network.class.getResource("RequestLog.txt").toURI());
            File agentLog = new File(Network.class.getResource("AgentLog.txt").toURI());
            assert(requestLog.exists());
            final FileWriter requestWriter = new FileWriter(requestLog);
            final FileWriter agentWriter = new FileWriter(agentLog);
            PrintStream stream = new PrintStream(System.out){
                @Override
                public void println(String string){
                    if(string.contains("Request")){
                        try {
                            requestWriter.write(network.getTime() + ": " + string);
                            requestWriter.write(System.lineSeparator());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }else if(string.contains("Agent")){
                        try {
                            agentWriter.write(network.getTime() + ": " + string);
                            agentWriter.write(System.lineSeparator());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }else if(string.contains("Ended")){
                        try {
                            agentWriter.close();
                            requestWriter.close();
                        }catch(IOException e){
                            e.printStackTrace();
                        }
                        super.println(string);
                    }else{
                        super.println(string);
                    }
                    return;
                }
            };
            System.setOut(stream);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        long time = System.currentTimeMillis();
        int counter = 0;
        for(int i = 0; i < 10000; i++){
            network.timeTick();
            counter++;
            if(counter == 50){
                System.out.println("Time: " + (i + 1));
                counter = 0;
            }

        }
        long endTime = System.currentTimeMillis() - time;

        System.out.println("Ended in " + endTime/1000 + " seconds and with " + network.getEventIDList().size() + " events");
    }

}
