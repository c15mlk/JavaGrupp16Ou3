package com.javagrupp16.ou3;

import java.io.*;
import java.net.URISyntaxException;

/**
 * Created by Marcus on 2016-05-17.
 */
public class NetworkTest {

    public static void main(String[] args){
        final Network network = new Network(5,5,1,1);

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




        for(int i = 0; i < 300; i++){
            network.timeTick();
        }

        System.out.println("Ended");
    }

}
