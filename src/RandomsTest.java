import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Grupp-16 on 2016-05-22.
 */
public class RandomsTest {

	@Test
	public void chanceOfTest() {
		int counter = 0;
		for (int i = 0; i < 100; i++) {
			if (Randoms.chanceOf(100)) {
				counter++;
			}
		}
		assertEquals(counter, 100);
		counter = 0;

		for (int i = 0; i < 100; i++) {
			if (Randoms.chanceOf(0)) {
				counter++;
			}
		}
		assertEquals(counter, 0);
		System.out.println("Counter " + counter);
	}

}
