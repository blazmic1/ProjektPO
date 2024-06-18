import java.util.List;
import java.util.Random;

/**
 * The type Hospital.
 */
public class Hospital extends Object {
    /**
     * The Durability.
     */
    int durability;

    /**
     * Instantiates a new Hospital.
     *
     * @param x          the x
     * @param y          the y
     * @param durability the durability
     */
    public Hospital(int x,int y,int durability){
        this.x = x;
        this.y = y;
        this.durability = durability;
    }

    /**
     * Heal.
     *
     * @param hospital the hospital
     * @param infected the infected
     * @param humans   the humans
     * @param objects  the objects
     */
//Recovering in hospital
    protected void heal(Hospital hospital,Infected infected, List<Human> humans, List<Object> objects) {
//        if ((infected.x == hospital.x  && infected.y == hospital.y )) {
        if ((infected.x == hospital.x + 1 && infected.y == hospital.y + 1) || (infected.x == hospital.x + 1 && infected.y == hospital.y) || (infected.x == hospital.x + 1 && infected.y == hospital.y - 1) || (infected.x == hospital.x && infected.y == hospital.y + 1) || (infected.x == hospital.x && infected.y == hospital.y - 1) || (infected.x == hospital.x - 1 && infected.y == hospital.y) || (infected.x == hospital.x - 1 && infected.y == hospital.y + 1) || (infected.x == hospital.x - 1 && infected.y == hospital.y - 1)) {

            Random random = new Random();
            if (hospital.durability > 0) {
                humans.remove(infected);
                humans.add(new Healthy(infected.x, infected.y, random.nextInt(100), 100, false, 0, true, 14, true));
                hospital.durability--;
            } else {
                objects.remove(hospital);
            }

        }
    }
}
