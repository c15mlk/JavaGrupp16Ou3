import java.util.List;
import java.util.Random;

/**
 * A utility class for Random methods.
 * Created by Grupp 16 on 2016-05-18.
 */
public class Randoms {

	private static final Random random = new Random();

	/**
	 * Method that checks the chance of something happening.
	 * @param procent the procent chance of something happening, where 100
	 *                is 100% and 1 is 1%
	 * @return true if it passes otherwise false
     */
	public static boolean chanceOf(double procent) {
		double rng = Math.random();
		return rng < (procent / 100);
	}

	/**
	 * Method that takes a random object from a list and returns it.
	 * @param list the list that an object is to be taken from
	 * @param <T> the type of object in the list.
     * @return a random object.
     */
	public static <T> T randomItem(List<T> list) {
		int randomInt = random.nextInt(list.size());
		return list.get(randomInt);
	}

}
