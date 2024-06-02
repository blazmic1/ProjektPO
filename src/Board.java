import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Board extends JPanel {
    int tilesize = 30;
    int cols = 20;
    int rows = 20;
    List<Human> humans;
    List<Object> objects;
    Random random = new Random();

    public Board() {
        this.setSize(new Dimension(cols * tilesize, rows * tilesize));
        humans = new ArrayList<>();
        objects = new ArrayList<>();
    }

    public void populateBoard(int healthyPeople, int infectedTransmitingPeople, int infectedNotTransmitingPeople, int immune, int vaccines, int hospitals) {
        for (int i = 0; i < healthyPeople; i++) {
            int x = random.nextInt(cols);
            int y = random.nextInt(rows);
            humans.add(new Healthy(x, y, random.nextInt(100), 100, false, 0.1, false, 0, true));
        }

        for (int i = 0; i < infectedTransmitingPeople; i++) {
            int x = random.nextInt(cols);
            int y = random.nextInt(rows);
            humans.add(new Infected(x, y, random.nextInt(100), 100, true, 14, true));
        }

        for (int i = 0; i < infectedNotTransmitingPeople; i++) {
            int x = random.nextInt(cols);
            int y = random.nextInt(rows);
            humans.add(new Infected(x, y, random.nextInt(100), 100, true, 14, false));
        }

        for (int i = 0; i < immune; i++) {
            int x = random.nextInt(cols);
            int y = random.nextInt(rows);
            humans.add(new Healthy(x, y, random.nextInt(100), 100, false, 0, true, 50, false));
        }

        for (int i = 0; i < vaccines; i++) {
            int x = random.nextInt(cols);
            int y = random.nextInt(rows);
            objects.add(new Vaccine(x, y, random.nextDouble()));
        }

        for (int i = 0; i < hospitals; i++) {
            int x = random.nextInt(cols);
            int y = random.nextInt(rows);
            objects.add(new Hospital(x, y, random.nextInt(10)));
        }
    }

    public int getHealthyCount() {
        int count = 0;
        for (Human human : humans) {
            if (human instanceof Healthy && !human.immune) {
                count++;
            }
        }
        return count;
    }

    public int getInfectedCount() {
        int count = 0;
        for (Human human : humans) {
            if (human instanceof Infected) {
                count++;
            }
        }
        return count;
    }

    public int getImmuneCount() {
        int count = 0;
        for (Human human : humans) {
            if (human instanceof Healthy && human.immune) {
                count++;
            }
        }
        return count;
    }

//    public int getDeadCount() {
//
//    }

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
        Image healthyImage = new ImageIcon("healthy.png").getImage();
        Image infectedImage = new ImageIcon("ill.png").getImage();
        Image immuneImage = new ImageIcon("immune.jpg").getImage();
        Image notTransmiting = new ImageIcon("notTransmiting1.png").getImage();
        Image Hospital = new ImageIcon("Hospital1.jpg").getImage();
        Image Vaccine = new ImageIcon("Vaccine1.png").getImage();

        for (Human human : humans) {
            if (human instanceof Healthy healthy) {

                if (healthy.immune) {
                 //   g2d.setColor(Color.CYAN); //odporny
                    g2d.drawImage(immuneImage,human.x * tilesize, human.y * tilesize, tilesize, tilesize,this);
                } else {
                   // g2d.setColor(Color.green);// zdrowy
                    g2d.drawImage(healthyImage,human.x * tilesize, human.y * tilesize, tilesize, tilesize,this);
                }

            } else if (human instanceof Infected infected) {
                if (infected.canTransmit) {
                //    g2d.setColor(Color.red); // chory przenoszacy
                    g2d.drawImage(infectedImage,human.x * tilesize, human.y * tilesize, tilesize, tilesize,this);
                }
                else{
                 //   g2d.setColor((Color.black)); //chory nieprzenoszacy
                g2d.drawImage(notTransmiting,human.x * tilesize, human.y * tilesize, tilesize, tilesize,this);
                }
            }
           // g2d.fillOval(human.x * tilesize, human.y * tilesize, tilesize, tilesize);

        }

        // Rysowanie obiektow
        for (Object object : objects) {
            if (object instanceof Vaccine) {
                //szczepionki
                g2d.setColor(Color.ORANGE);
                g2d.drawImage(Vaccine,object.x * tilesize, object.y * tilesize, tilesize, tilesize,this);

            } else if (object instanceof Hospital) {
                //szpital
                g2d.setColor(Color.BLUE);
                g2d.drawImage(Hospital,object.x * tilesize, object.y * tilesize, tilesize, tilesize,this);
            }
          //  g2d.fillRect(object.x * tilesize, object.y * tilesize, tilesize, tilesize);
        }
    }

    public void updateSimulation() {
        List<Human> newHumans = new ArrayList<>(humans);
        List<Object> newObjects = new ArrayList<>(objects);

        // Poruszanie sie
        for (Human human : humans) {
            moveHuman(human);
        }

        // Infekowanie zdrowych
        for (Human human : humans) {
            if (human instanceof Infected) {
                for (Human other : humans) {
                    if (other instanceof Healthy) {
                        ((Infected) human).infect((Infected) human, (Healthy) other, newHumans);
                    }
                }
            }
        }

        // Zdrowienie chorych
        for (Human human : humans) {
            if (human instanceof Infected) {
               ((Infected) human).recover((Infected) human, (Healthy) human, newHumans);
            }
        }

        // Leczenie w szpitalach
        for (Object object : objects) {
            if (object instanceof Hospital) {
                for (Human human : humans) {
                    if (human instanceof Infected) {
                        ((Hospital) object).heal((Hospital) object, (Infected) human, newHumans, newObjects);
                    }
                }
            }
        }

        //Szczepienie zdrowych
        for (Object object : objects) {
            if (object instanceof Vaccine) {
                for (Human human : humans) {
                    if (human instanceof Healthy) {
                       ((Vaccine) object).vaccine((Vaccine) object, (Healthy) human, newHumans, newObjects);
                    }
                }
            }
        }

        humans = newHumans;
        objects = newObjects;

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
}
