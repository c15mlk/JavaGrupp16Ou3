package com.javagrupp16.ou3;

import org.junit.Test;

/**
 * Created by Marcus on 2016-05-22.
 */
public class PositionTest {

	@Test
	public void equalsTest() {
		Position pos = new Position(0, 1);
		assert (pos.equals(new Position(0, 1)));
		assert (!pos.equals(new Position(0, 2)));
	}
}
