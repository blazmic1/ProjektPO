public class Healthy extends Human {

    int remainingImmunity;
    double infectionChance;
    boolean canBeVaccinated;

    public Healthy(int x, int y, int age, int lifespan, boolean infection, double infectionChance, boolean immune, int remainingImmunity, boolean canBeVaccinated) {
        this.x = x;
        this.y = y;
        this.age = age;
        this.lifespan = lifespan;
        this.infection = infection;
        this.immune = immune;
        this.remainingImmunity = remainingImmunity;
        this.canBeVaccinated = canBeVaccinated;
        if (immune == true)
            this.infectionChance = 0;
        else
            this.infectionChance = infectionChance;  }


}
