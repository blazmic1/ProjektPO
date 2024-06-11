import java.util.List;

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
        if (this.immune)
            this.infectionChance = 0;
        else
            this.infectionChance = infectionChance;  }

    void loosingImmunity(Healthy healthy, List<Human> humans){
        if (this.immune){
            if (healthy.remainingImmunity == 0){
                humans.remove(healthy);
                humans.add(new Healthy(this.x, this.y, this.age, this.lifespan, false, 0.2, false, 0, true));

            }
        healthy.remainingImmunity--;
        }
    }


}
