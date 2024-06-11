import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import static java.lang.Math.abs;

public class Board extends JPanel {
    int tilesize = 30;
    int cols = 20;
    int rows = 20;
    List<Human> humans;
    List<Object> objects;
    Random random = new Random();

    public Board() {
        this.setBounds(0, 5,cols * tilesize,rows * tilesize);
        humans = new ArrayList<>();
        objects = new ArrayList<>();
    }

    protected void populateBoard(int healthyPeople, int infectedTransmitingPeople, int infectedNotTransmitingPeople, int immune, int vaccines, int hospitals) {

        //Adding healthy agents
        for (int i = 0; i < healthyPeople; i++) {
            int x = random.nextInt(cols);
            int y = random.nextInt(rows);
            humans.add(new Healthy(x, y, (int) (abs((random.nextGaussian()))*300), 750, false, 0.2, false, 0, true));
        }

        //Adding infected agents that can transmit disease
        for (int i = 0; i < infectedTransmitingPeople; i++) {
            int x = random.nextInt(cols);
            int y = random.nextInt(rows);
            humans.add(new Infected(x, y, (int) (abs((random.nextGaussian()))*300),0.01, 750, true, 14, true));
        }

        //Adding infected agents that cannot transmit disease
        for (int i = 0; i < infectedNotTransmitingPeople; i++) {
            int x = random.nextInt(cols);
            int y = random.nextInt(rows);
            humans.add(new Infected(x, y, (int) (abs((random.nextGaussian()))*300),0.01, 750, true, 14, false));
        }

        //Adding immune agents
        for (int i = 0; i < immune; i++) {
            int x = random.nextInt(cols);
            int y = random.nextInt(rows);
            humans.add(new Healthy(x, y, (int) (abs((random.nextGaussian()))*300), 750, false, 0, true, 14, false));
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
            objects.add(new Hospital(x, y, random.nextInt(3)));
        }
    }

    protected int getHealthyCount() {
        int count = 0;
        for (Human human : humans) {
            if (human instanceof Healthy && !human.immune) {
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

        // getting older
        for (Human human : humans) {
            if (GUI.roundCount > 1 && human instanceof Infected infected){
                infected.getOlder(human);
            }
            if (GUI.roundCount > 1 && human instanceof Healthy healthy){
                healthy.getOlder(human);
            }
        }

        // infecting healthy agents
        for (Human human : humans) {
            if (human instanceof Infected infected && GUI.roundCount > 1) {
                    for (Human human1 : humans) {
                        if (human1 instanceof Healthy healthy) {
                            infected.infect(infected, healthy,newHumans);
                        }
                    }
                }
        }
        // recovering agents
        for (Human human : humans) {
            if (GUI.roundCount > 1 && human instanceof Infected infected) {
                    infected.recover(newHumans,infected);
                }
        }

        // Recovering in hospital
        for (Object object : objects) {
            if (object instanceof Hospital hospital && GUI.roundCount > 1) {
                for (Human human : humans) {
                    if (human instanceof Infected infected) {
                        hospital.heal(hospital, infected, newHumans, newObjects);
                    }
                }
            }
        }

        // dying infected
        for (Human human : humans) {
            if (GUI.roundCount > 1 && human instanceof Infected infected) {
                infected.die(newHumans);
            }
        }

        // dying of old age
        for (Human human : humans) {
            if (GUI.roundCount > 1 && human instanceof Infected infected){
                infected.dieOldAge(newHumans);
            }
            if (GUI.roundCount > 1 && human instanceof Healthy healthy){
                healthy.dieOldAge(newHumans);
            }
        }

        //Vaccinating healthy
        for (Object object : objects) {
            if (object instanceof Vaccine vaccine1) {
                    for (Human human : humans) {
                        if (human instanceof Healthy healthy) {
                            vaccine1.vaccine(vaccine1, healthy, newHumans, newObjects);

                        }
                    }
                }
        }

            //loosing immunity
            for (Human human : humans) {
                if (GUI.roundCount > 1 && human instanceof Healthy healthy) {
                        healthy.loosingImmunity(healthy,newHumans);
                }
            }
        humans = newHumans;
        objects = newObjects;
        repaint();
    }

}