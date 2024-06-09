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
        //this.setSize(new Dimension(cols * tilesize, rows * tilesize));
        this.setBounds(0, 5,cols * tilesize,rows * tilesize);
        humans = new ArrayList<>();
        objects = new ArrayList<>();
    }

    protected void populateBoard(int healthyPeople, int infectedTransmitingPeople, int infectedNotTransmitingPeople, int immune, int vaccines, int hospitals) {

        //Adding healthy agents
        for (int i = 0; i < healthyPeople; i++) {
            int x = random.nextInt(cols);
            int y = random.nextInt(rows);
            humans.add(new Healthy(x, y, random.nextInt(100), 100, false, 0.1, false, 0, true));
        }

        //Adding infected agents that can transmit disease
        for (int i = 0; i < infectedTransmitingPeople; i++) {
            int x = random.nextInt(cols);
            int y = random.nextInt(rows);
            humans.add(new Infected(x, y, random.nextInt(100),0.2, 100, true, 14, true));
        }

        //Adding infected agents that cannot transmit disease
        for (int i = 0; i < infectedNotTransmitingPeople; i++) {
            int x = random.nextInt(cols);
            int y = random.nextInt(rows);
            humans.add(new Infected(x, y, random.nextInt(100),0.2, 100, true, 14, false));
        }

        //Adding immune agents
        for (int i = 0; i < immune; i++) {
            int x = random.nextInt(cols);
            int y = random.nextInt(rows);
            humans.add(new Healthy(x, y, random.nextInt(100), 100, false, 0, true, 14, false));
        }

        //Adding vaccines
        for (int i = 0; i < vaccines; i++) {
            int x = random.nextInt(cols);
            int y = random.nextInt(rows);
            objects.add(new Vaccine(x, y, 0.3));
        }

        //Adding hospitals
        for (int i = 0; i < hospitals; i++) {
            int x = random.nextInt(cols);
            int y = random.nextInt(rows);
            objects.add(new Hospital(x, y, random.nextInt(4)));
        }
    }

    protected int getHealthyCount() {
        int count = 0;
        for (Human human : humans) {
            if (human instanceof Healthy && human.immune == false) {
                count++;
            }
        }
        return count;
    }

    protected int getInfectedCount() {
        int count = 0;
        for (Human human : humans) {
            if (human instanceof Infected) {
                count++;
            }
        }
        return count;
    }

    protected int getImmuneCount() {
        int count = 0;
        for (Human human : humans) {
            if (human instanceof Healthy && human.immune) {
                count++;
            }
        }
        return count;
    }

//    protected int getDeadCount() {
//        {
//
//        }
//    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Drawing a board
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                g2d.setColor((c + r) % 2 == 0 ? new Color(34, 34, 34) : new Color(42, 45, 47));
                g2d.fillRect(c * tilesize, r * tilesize, tilesize, tilesize);
            }
        }

        // Drawing agents
        Image healthyImage = new ImageIcon("healthy_new.png").getImage();
        Image infectedImage = new ImageIcon("infected_new.png").getImage();
        Image immuneImage = new ImageIcon("immune_new.png").getImage();
        Image notTransmiting = new ImageIcon("notTransmitting_new.png").getImage();
        Image Hospital = new ImageIcon("hospital_new.png").getImage();
        Image Vaccine = new ImageIcon("vaccine_new.png").getImage();

        for (Human human : humans) {
            if (human instanceof Healthy healthy) {

                if (healthy.immune) {
                    g2d.drawImage(immuneImage,human.x * tilesize, human.y * tilesize, tilesize, tilesize,null);
                } else {
                    g2d.drawImage(healthyImage,human.x * tilesize, human.y * tilesize, tilesize, tilesize,null);
                }

            } else if (human instanceof Infected infected) {
                if (infected.canTransmit) {
                    g2d.drawImage(infectedImage,human.x * tilesize, human.y * tilesize, tilesize, tilesize,null);
                }
                else{
                    g2d.drawImage(notTransmiting,human.x * tilesize, human.y * tilesize, tilesize, tilesize,null);
                }
            }
        }

        // Drawing objects
        for (Object object : objects) {
            //vaccines
            if (object instanceof Vaccine) {
                g2d.setColor(Color.ORANGE);
                g2d.drawImage(Vaccine,object.x * tilesize, object.y * tilesize, tilesize, tilesize,null);

            } else if (object instanceof Hospital) {
                //hospital
                g2d.setColor(Color.BLUE);
                g2d.drawImage(Hospital,object.x * tilesize, object.y * tilesize, tilesize, tilesize,null);
            }
        }
    }

    public void updateSimulation() {
        List<Human> newHumans = new ArrayList<>(humans);
        List<Object> newObjects = new ArrayList<>(objects);

        // movement
        for (Human human : humans) {
            human.moveHuman(human);
        }

        // infecting healthy agents
        for (Human human : humans) {
            if (human instanceof Infected) {
                if (GUI.roundCount > 1) {
                    for (Human human1 : humans) {
                        if (human1 instanceof Healthy) {
                            ((Infected) human).infect((Infected) human, (Healthy) human1, newHumans);
                        }
                    }
                }
            }
        }
        // recovering agents
        for (Human human : humans) {
            if (GUI.roundCount > 1) {
                if (human instanceof Infected) {
                    ((Infected) human).recover(newHumans);
                }
            }
        }
        // Recovering in hospital
        for (Object object : objects) {
            if (object instanceof Hospital) {
                if (GUI.roundCount > 1){
                for (Human human : humans) {
                    if (human instanceof Infected) {
                        ((Hospital) object).heal((Hospital) object, (Infected) human, newHumans, newObjects);
                    }
                }
            }
            }
        }

        //Vaccining healthy
        for (Object object : objects) {
            if (object instanceof Vaccine) {
                    for (Human human : humans) {
                        if (human instanceof Healthy) {
                            ((Vaccine) object).vaccine((Vaccine) object, (Healthy) human, newHumans, newObjects);
                        }
                    }
                }
        }

            //loosing immunity
            for (Human human : humans) {
                if (GUI.roundCount > 1) {
                    if (human instanceof Healthy) {
                        ((Healthy) human).loosingImmunity( (Healthy) human,newHumans);
                    }
                }
            }



        humans = newHumans;
        objects = newObjects;

        repaint();
    }

}