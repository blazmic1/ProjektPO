import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Board extends JPanel {
    int tilesize = 30;
    int cols = 20;
    int rows = 20;
    List<Human> humans;
    List<Object> objects;
    Random random = new Random();

    public Board() {
        this.setPreferredSize(new Dimension(cols * tilesize, rows * tilesize));
        this.setBackground(Color.gray);
        humans = new ArrayList<>();
        objects = new ArrayList<>();
        populateBoard();
    }

    private void populateBoard() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Podaj ilosc osob zdrowych:");
        int healthyPeople = scanner.nextInt();

        for (int i = 0; i < healthyPeople; i++) {
            int x = random.nextInt(cols);
            int y = random.nextInt(rows);
            humans.add(new Healthy(x, y, random.nextInt(100), 100, false, 0.1, false, 0, true));
        }

        System.out.println("Podaj ilosc osob chorych przenoszacych chorobe:");
        int infectedTransmitingPeople = scanner.nextInt();

        for (int i = 0; i < infectedTransmitingPeople; i++) {
            int x = random.nextInt(cols);
            int y = random.nextInt(rows);
            humans.add(new Infected(x, y, random.nextInt(100), 100, true, 14, true));
        }

        System.out.println("Podaj ilosc osob chorych nieprzenoszacych chorobe:");
        int infectedNotTransmitingPeople = scanner.nextInt();

        for (int i = 0; i < infectedNotTransmitingPeople; i++) {
            int x = random.nextInt(cols);
            int y = random.nextInt(rows);
            humans.add(new Infected(x, y, random.nextInt(100), 100, true, 14, false));
        }

        System.out.println("Podaj ilosc osob odpornych:");
        int immune = scanner.nextInt();

        for (int i = 0; i < immune; i++) {
            int x = random.nextInt(cols);
            int y = random.nextInt(rows);
            humans.add(new Healthy(x, y, random.nextInt(100), 100, false, 0, true, 50, false));
        }

        System.out.println("Podaj ilosc szczepionek:");
        int vaccines = scanner.nextInt();
        for (int i = 0; i < vaccines; i++) {
            int x = random.nextInt(cols);
            int y = random.nextInt(rows);
            objects.add(new Vaccine(x, y, random.nextDouble()));
        }

        System.out.println("Podaj ilosc Szpitali:");
        int hospitals = scanner.nextInt();
        for (int i = 0; i < hospitals; i++) {
            int x = random.nextInt(cols);
            int y = random.nextInt(rows);
            objects.add(new Hospital(x, y, random.nextInt(10)));
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Rysowanie szachownicy
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                g2d.setColor((c + r) % 2 == 0 ? Color.white : Color.lightGray);
                g2d.fillRect(c * tilesize, r * tilesize, tilesize, tilesize);
            }
        }

        // Rysowanie agentów
        for (Human human : humans) {
            if (human instanceof Healthy) {
                Healthy healthy = (Healthy) human;

                if (healthy.immune) {
                    g2d.setColor(Color.CYAN); //odporny
                } else {
                    g2d.setColor(Color.green); // zdrowy
                }

            } else if (human instanceof Infected) {
                Infected infected = (Infected) human;
                if (infected.canTransmit)
                    g2d.setColor(Color.red); // chory przenoszacy
                else
                    g2d.setColor((Color.black)); //chory nieprzenoszacy
            }
            g2d.fillOval(human.x * tilesize, human.y * tilesize, tilesize, tilesize);
        }

        // Rysowanie obiektow
        for (Object object : objects) {
            if (object instanceof Vaccine) {
                g2d.setColor(Color.ORANGE);
            } else if (object instanceof Hospital) {
                g2d.setColor(Color.BLUE);
            }
            g2d.fillRect(object.x * tilesize, object.y * tilesize, tilesize, tilesize);
        }
    }

    public void updateSimulation() {

        List<Human> humansCopy = new ArrayList<>(humans);
        // Poruszanie sie
        for (Human human : humans) {
            moveHuman(human);

        }



        repaint();
    }

    private void moveHuman(Human human) {
        //losowe ruchy agentów
        int dx = random.nextInt(3) - 1;
        int dy = random.nextInt(3) - 1;

        //nowe wspolrzedne
        int newX = human.x + dx;
        int newY = human.y + dy;

        //nie wychodzenie poza mape
        if (newX >= 0 && newX < cols) {
            human.x = newX;
        }
        if (newY >= 0 && newY < rows) {
            human.y = newY;
        }
    }

    // Leczenie poprzez szpital
    public void heal(Hospital hospital, Infected infected, List<Human> humans, List<Object> objects) {
        if (infected.x == hospital.x && infected.y == hospital.y) {
            Random random = new Random();
            if (hospital.durability > 0) {
                humans.remove(infected);
                humans.add(new Healthy(infected.x, infected.y, random.nextInt(100), 100, false, 0.1, true, 14, true));
                hospital.durability--;
            } else {
                objects.remove(hospital);
            }
        }
    }


    //szczepeinie zdrowych
    private void vaccine(Vaccine vaccine, Healthy healthy, List<Human> humans, List<Object> objects) {
        if (healthy.x == vaccine.x && healthy.y == vaccine.y && healthy.canBeVaccinated) {
            if (vaccine.effectiveness > random.nextDouble()) {
                humans.remove(healthy);
                humans.add(new Healthy(healthy.x, healthy.y, random.nextInt(100), 100, false, 0, true, 50, false));
                objects.remove(vaccine);
            }
        }
    }

    //zdrowienie chorych po pewnym czasie
    private void recover(Infected infected, Healthy healthy , List<Human> humans){
        if (infected.infectionLength < 0) {
            humans.remove(infected);
            humans.add(new Healthy(healthy.x, healthy.y, random.nextInt(100), 100, false, 0, true, 50, false));
        }
    }
}



