package com.javagrupp16.ou3;


import java.io.PrintStream;

/**
 * Created by Grupp-16 on 2016-05-17.
 */
public class NetworkMain {

	private static int eventCounter = 0;

	public static final int AGENT_MAX_STEPS = 50;
	public static final int REQUEST_MAX_STEPS = 45;
	public static final int NETWORK_WIDTH = 1;
	public static final int NETWORK_HEIGHT = 50;
	public static final int NETWORK_AGENT_PROB = 50;
	public static final double NETWORK_EVENT_PROB = 0.01;

	public static void main(String[] args) {

		final Network network = new Network(NETWORK_HEIGHT, NETWORK_WIDTH,
				NETWORK_AGENT_PROB, NETWORK_EVENT_PROB);

		System.setOut(new PrintStream(System.out) {
			@Override
			public void println(String string) {
				if (string.contains("Request")) {
					eventCounter++;
				}
				super.println(string);
			}
		});


		long time = System.currentTimeMillis();
		for (int i = 0; i < 10000; i++) {
			network.timeTick();
		}
		long endTime = System.currentTimeMillis() - time;

		System.out.println("Ended in " + endTime / 1000 + " seconds and with " + network.getEventIDList().size() +
				" events and " + eventCounter + " requests succeded with their job");
	}

}
