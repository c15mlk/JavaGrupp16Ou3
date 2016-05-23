package com.javagrupp16.ou3.entities;

import com.javagrupp16.ou3.Network;
import com.javagrupp16.ou3.Position;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Marcus on 2016-05-22.
 */
public class NodeTest {
	private Network network;
	private Node node;

	@Before
	public void setUp() throws Exception {
		network = new Network(2, 2, 100, 100);
	}

	@Test
	public void nodeTest() {
		assert (network.hasNode(new Position(0, 0)));
		assert (network.getNode(new Position(0, 0)) != null);
		assert (network.hasNode(new Position(0, 10)));
		assert (network.getNode(new Position(0, 0)) != null);
	}
}
