import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Infected extends Human {
    int infectionLength;
    boolean canTransmit;
    double mortality;
    int count = 0;
    Random random = new Random();
    public Infected(int x, int y, int age,double mortality, int lifespan, boolean infection, int infectionLength,boolean canTransmit) {
        this.x = x;
        this.y = y;
        this.age = age;
        this.mortality = mortality;
        this.lifespan = lifespan; // number of rounds
        this.infection = infection;
        this.infectionLength = infectionLength;
        this.canTransmit = canTransmit;
    }

    //infekowanie zdrowych
    void infect(Infected infected, Healthy healthy, List<Human> humans) {
        if ((infected.x == healthy.x + 1 && infected.y == healthy.y + 1) || (infected.x == healthy.x + 1 && infected.y == healthy.y) || (infected.x == healthy.x + 1 && infected.y == healthy.y - 1) || (infected.x == healthy.x && infected.y == healthy.y + 1) || (infected.x == healthy.x && infected.y == healthy.y - 1) || (infected.x == healthy.x - 1 && infected.y == healthy.y) || (infected.x == healthy.x - 1 && infected.y == healthy.y + 1) || (infected.x == healthy.x - 1 && infected.y == healthy.y - 1)) {
            if (!healthy.immune && healthy.infectionChance > random.nextDouble() && infected.canTransmit) {
                humans.remove(healthy);
                if (random.nextDouble() > 0.5) {
                    humans.add(new Infected(healthy.x, healthy.y, random.nextInt(100), 0.2, 100, true, 14, true));
                    }
                    else{
                    humans.add(new Infected(healthy.x, healthy.y, random.nextInt(100), 0.2, 100, true, 14, false));
                }
                }
        }
    }
   protected void recover(List<Human> humans) {
        if (this.infectionLength == 0) {
            humans.remove(this);
            humans.add(new Healthy(this.x, this.y, random.nextInt(100), 100, false, 0, true, 14, false));
        }
        else {
            infectionLength--;
        }
    }

    void die(List<Human> humans){
        if (mortality > random.nextDouble() || lifespan > age){
            humans.remove(this);
            count++;
        }
    }

}
