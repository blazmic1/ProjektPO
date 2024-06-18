import java.util.List;
import java.util.Random;

/**
 * The type Infected.
 */
public class Infected extends Human {
    /**
     * The Infection length.
     */
    int infectionLength;
    /**
     * The Can transmit.
     */
    boolean canTransmit;
    /**
     * The Mortality.
     */
    double mortality;
    /**
     * The Count.
     */
    static int count = 0;
    /**
     * The Random.
     */
    Random random = new Random();

    /**
     * Instantiates a new Infected.
     *
     * @param x               the x
     * @param y               the y
     * @param age             the age
     * @param mortality       the mortality
     * @param lifespan        the lifespan
     * @param infection       the infection
     * @param infectionLength the infection length
     * @param canTransmit     the can transmit
     */
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

    /**
     * Infect.
     *
     * @param infected the infected
     * @param healthy  the healthy
     * @param humans   the humans
     */
//spreading disease
    protected void infect(Infected infected, Healthy healthy, List<Human> humans) {
        if ((infected.x == healthy.x + 1 && infected.y == healthy.y + 1) || (infected.x == healthy.x + 1 && infected.y == healthy.y) || (infected.x == healthy.x + 1 && infected.y == healthy.y - 1) || (infected.x == healthy.x && infected.y == healthy.y + 1) || (infected.x == healthy.x && infected.y == healthy.y - 1) || (infected.x == healthy.x - 1 && infected.y == healthy.y) || (infected.x == healthy.x - 1 && infected.y == healthy.y + 1) || (infected.x == healthy.x - 1 && infected.y == healthy.y - 1)) {
            if (!healthy.immune && healthy.infectionChance > random.nextDouble() && infected.canTransmit) {
                humans.remove(healthy);
                if (random.nextDouble() > 0.3) {
                    humans.add(new Infected(healthy.x, healthy.y, healthy.age, 0.01, healthy.lifespan, true, 14, true));
                }
                else{
                    humans.add(new Infected(healthy.x, healthy.y, healthy.age, 0.01, healthy.lifespan, true, 14, false));
                }
                }
        }
    }

    /**
     * Recover.
     *
     * @param humans   the humans
     * @param infected the infected
     */
//recovering
    protected void recover(List<Human> humans,Infected infected) {
        if (infected.infectionLength <= 0 &&  random.nextDouble() > 0.5) {
            humans.remove(infected);
            humans.add(new Healthy(infected.x, infected.y, infected.age, infected.lifespan, false, 0, true, 14, false));
        }
        else {
            this.infectionLength--;
        }
    }

    /**
     * Die.
     *
     * @param humans the humans
     */
    protected void die(List<Human> humans){
        if (mortality > random.nextDouble()){
            humans.remove(this);
            count++;
        }
    }

    /**
     * Gets death count.
     *
     * @return the death count
     */
    protected static int getDeathCount() {
        return count;
    }
}
