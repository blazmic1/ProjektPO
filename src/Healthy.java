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
        if (this.immune == true)
            this.infectionChance = 0;
        else
            this.infectionChance = infectionChance;  }

    void loosingImmunity(Healthy healthy, List<Human> humans){
        if (this.immune == true){
            if (healthy.remainingImmunity <= 0){
                humans.add(new Healthy(this.x, this.y, random.nextInt(100), 100, false, 0.1, false, 0, true));
                humans.remove(healthy);
            }
        healthy.remainingImmunity--;
        }
    }


}
