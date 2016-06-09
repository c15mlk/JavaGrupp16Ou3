import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by Grupp 16 on 2016-06-07.
 */
public class AgentTest {
    private Network network;
    private Node node;
    private Agent agent;

    @Before
    public void setUp() throws Exception {
        network = new Network(5, 5, 0, 100);

        Field f = Network.class.getDeclaredField("nodes");
        f.setAccessible(true);
        Map<Position, Node> map = (Map<Position, Node>) f.get(network);
        node = map.values().iterator().next();
        f.setAccessible(false);

        for(int i = 0 ; i < 1 ; i++){
            network.timeTick();
        }
        node.detectEvent(UUID.randomUUID(), network.getTime(), 100);
        f = Node.class.getDeclaredField("moveableMap");
        f.setAccessible(true);
        Map<Moveable, Moveable> map2 = (Map<Moveable, Moveable>) f.get(node);
        for(Moveable m : map2.values()){
            if(m instanceof Agent) {
                agent = (Agent) m;
                break;
            }
        }
        f.setAccessible(false);
    }

    @Test
    public void checkSoAgentMovesWhenItShould() {
        for(int i = 0 ; i < 50 ; i++) {
            Position oldPos = agent.getPosition();
            int steps = agent.getSteps();
            network.timeTick();
            assertNotEquals(oldPos, agent.getPosition());
            assertEquals(steps+1, agent.getSteps());
        }
        assert(agent.isComplete());
        for(int i = 0 ; i < 50 ; i++){
            Position oldPos = agent.getPosition();
            int steps = agent.getSteps();
            network.timeTick();
            assertEquals(oldPos, agent.getPosition());
            assertEquals(steps, agent.getSteps());
        }
    }

}
