import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Infected extends Human {
    int infectionLength;
    boolean canTransmit;
    public Infected(int x, int y, int age, int lifespan, boolean infection, int infectionLength,boolean canTransmit) {
        this.x = x;
        this.y = y;
        this.age = age;
        this.lifespan = lifespan;
        this.infection = infection;
        this.infectionLength = infectionLength;
        this.canTransmit = canTransmit;
    }

    //infekowanie zdrowych
     void infect(Infected infected, Healthy healthy, List<Human> humans) {
        Random random = new Random();
        if ((infected.x == healthy.x + 1 && infected.y == healthy.y + 1) || (infected.x == healthy.x + 1 && infected.y == healthy.y) || (infected.x == healthy.x + 1 && infected.y == healthy.y - 1 ) || (infected.x == healthy.x  && infected.y == healthy.y + 1) || (infected.x == healthy.x  && infected.y == healthy.y -1) || (infected.x == healthy.x - 1 && infected.y == healthy.y  )|| (infected.x == healthy.x - 1 && infected.y == healthy.y + 1 )|| (infected.x == healthy.x - 1 && infected.y == healthy.y - 1  )) {
               if (healthy.immune == false && healthy.infectionChance > random.nextDouble() && infected.canTransmit == true) {
                humans.remove(healthy);
                humans.add(new Infected(healthy.x, healthy.y, random.nextInt(100), 100, true, 14, true));
            }
        }

    }

}


