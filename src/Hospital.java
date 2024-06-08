import java.util.List;
import java.util.Random;

public class Hospital extends Object {
    int durability;

    public Hospital(int x,int y,int durability){
        this.x = x;
        this.y = y;
        this.durability = durability;
    }

    //Leczenie poprzez szpital
    public void heal(Hospital hospital,Infected infected, List<Human> humans, List<Object> objects) {
        if ((infected.x == hospital.x  && infected.y == hospital.y )) {
            Random random = new Random();
            if (hospital.durability >= 0) {
                humans.remove(infected);
                humans.add(new Healthy(infected.x, infected.y, random.nextInt(100), 100, false, 0.1, true, 14, true));
                hospital.durability--;
            } else {
                objects.remove(hospital);
            }

        }
    }
}
