import java.util.List;
import java.util.Random;

import static java.lang.Math.abs;

public class Vaccine  extends Object{
    double effectiveness;
    Random random = new Random();
     Vaccine(int x, int y, double effectiveness){
        this.x = x;
        this.y = y;
        this.effectiveness = effectiveness;
    }

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
