import java.util.List;
import java.util.Random;

/**
 * The type Human.
 */
public class Human extends Object {
    /**
     * The Lifespan.
     */
    int lifespan;
    /**
     * The Age.
     */
    int age;
    /**
     * The Infection.
     */
    boolean infection;
    /**
     * The Immune.
     */
    boolean immune;
    /**
     * The Count.
     */
    static int count = 0;
    /**
     * The Random.
     */
    Random random = new Random();

    /**
     * Move human.
     *
     * @param human the human
     */
    public void moveHuman(Human human) {
        //random agent moves
        Board board = new Board();
        int dx = random.nextInt(3) - 1;
        int dy = random.nextInt(3) - 1;

        //new coordinates
        int newX = human.x + dx;
        int newY = human.y + dy;


        //staying at map
        if (newX >= 0 && newX < board.cols) {
            human.x = newX;
        }
        if (newY >= 0 && newY < board.rows) {
            human.y = newY;
        }
    }

    /**
     * Get older.
     *
     * @param human the human
     */
    public void getOlder(Human human){
        age++;
    }

    /**
     * Die old age.
     *
     * @param humans the humans
     */
    public void dieOldAge(List<Human> humans){
        if (age > lifespan){
            humans.remove(this);
            count++;
        }
    }

    /**
     * Gets old age count.
     *
     * @return the old age count
     */
    protected static int getOldAgeCount() {
        return count;
    }
}

