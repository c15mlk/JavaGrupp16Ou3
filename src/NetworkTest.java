import org.junit.Before;
import org.junit.Test;

/**
 * Created by Grupp-16 on 2016-05-22.
 */
public class NetworkTest {
	private Network network;

	@Before
	public void setUp() throws Exception {
		network = new Network(2, 2, 100, 100);
	}

	@Test
	public void nodeTest() {
		assert (network.hasNode(new Position(0, 0)));
		assert (network.hasNode(new Position(0, 10)));
	}

	@Test
	public void checkSoEventsHappen() {
		int time = network.getTime();
		network.timeTick();
		assert (network.getEventIDList().size() != 0);
		assert (time < network.getTime());
	}


}
