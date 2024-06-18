import java.util.List;
import java.util.Random;

import static java.lang.Math.abs;

/**
 * The type Vaccine.
 */
public class Vaccine  extends Object{
    /**
     * The Effectiveness.
     */
    double effectiveness;
    /**
     * The Random.
     */
    Random random = new Random();

    /**
     * Instantiates a new Vaccine.
     *
     * @param x             the x
     * @param y             the y
     * @param effectiveness the effectiveness
     */
    Vaccine(int x, int y, double effectiveness){
        this.x = x;
        this.y = y;
        this.effectiveness = effectiveness;
    }

    /**
     * Vaccine.
     *
     * @param vaccine the vaccine
     * @param healthy the healthy
     * @param humans  the humans
     * @param objects the objects
     */
    protected void vaccine(Vaccine vaccine ,Healthy healthy, List<Human> humans,List<Object> objects) {
        if (GUI.roundCount >= 1 ){
        if (effectiveness > random.nextDouble() && this.x == healthy.x && this.y == healthy.y && !healthy.immune) {
            humans.remove(healthy);
            humans.add(new Healthy(healthy.x, healthy.y, (int) (abs((random.nextGaussian()))*300), 100, false, 0, true, 30, false));
            objects.remove(vaccine);
        }
    }
    }




}
