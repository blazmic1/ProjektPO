import java.util.List;

/**
 * The type Healthy.
 */
public class Healthy extends Human {

    /**
     * The Remaining immunity.
     */
    int remainingImmunity;
    /**
     * The Infection chance.
     */
    double infectionChance;
    /**
     * The Can be vaccinated.
     */
    boolean canBeVaccinated;

    /**
     * Instantiates a new Healthy.
     *
     * @param x                 the x
     * @param y                 the y
     * @param age               the age
     * @param lifespan          the lifespan
     * @param infection         the infection
     * @param infectionChance   the infection chance
     * @param immune            the immune
     * @param remainingImmunity the remaining immunity
     * @param canBeVaccinated   the can be vaccinated
     */
    public Healthy(int x, int y, int age, int lifespan, boolean infection, double infectionChance, boolean immune, int remainingImmunity, boolean canBeVaccinated) {
        this.x = x;
        this.y = y;
        this.age = age;
        this.lifespan = lifespan;
        this.infection = infection;
        this.immune = immune;
        this.remainingImmunity = remainingImmunity;
        this.canBeVaccinated = canBeVaccinated;
        if (this.immune)
            this.infectionChance = 0;
        else
            this.infectionChance = infectionChance;  }

    /**
     * Loosing immunity.
     *
     * @param healthy the healthy
     * @param humans  the humans
     */
    protected void loosingImmunity(Healthy healthy, List<Human> humans){
        if (this.immune){
            if (healthy.remainingImmunity == 0){
                humans.remove(healthy);
                humans.add(new Healthy(this.x, this.y, this.age, this.lifespan, false, 0.2, false, 0, true));

            }
        healthy.remainingImmunity--;
        }
    }


}
