import java.util.List;
import java.util.Random;

public class Vaccine  extends Object{
    double effectiveness;
    Random random = new Random();;
    public Vaccine(int x,int y, double effectiveness){
        this.x = x;
        this.y = y;
        this.effectiveness = effectiveness;
    }

    void vaccine(Vaccine vaccine ,Healthy healthy, List<Human> humans,List<Object> objects) {
        if (GUI.roundCount >= 1 ){
        if (effectiveness > random.nextDouble() && this.x == healthy.x && this.y == healthy.y && healthy.immune == false) {
            humans.remove(healthy);
            humans.add(new Healthy(healthy.x, healthy.y, random.nextInt(100), 100, false, 0, true, 50, false));
            objects.remove(vaccine);
        }
    }
    }




}
