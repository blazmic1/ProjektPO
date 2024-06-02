import java.util.Random;

public class Human extends Object {
    int lifespan;
    int age;
    boolean infection;
    boolean immune;
    Random random = new Random();

    public void moveHuman(Human human) {
        //losowe ruchy agentów
        Board board = new Board();
        int dx = random.nextInt(3) - 1;
        int dy = random.nextInt(3) - 1;

        //nowe wspolrzedne
        int newX = human.x + dx;
        int newY = human.y + dy;

        //nie wychodzenie poza mape
        if (newX >= 0 && newX < board.cols) {
            human.x = newX;
        }
        if (newY >= 0 && newY < board.rows) {
            human.y = newY;
        }
    }
}

