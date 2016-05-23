package com.javagrupp16.ou3;


import java.io.PrintStream;

/**
 * Created by Grupp-16 on 2016-05-17.
 */
public class NetworkMain {

	private static int eventCounter = 0;

	public static void main(String[] args) {
		final Network network = new Network(50, 50, 50, 0.01);

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
