import javax.swing.*;
import java.awt.*;

public class Stats extends JPanel {
    private int healthyCount;
    private int infectedCount;
    private int immuneCount;
    private int deadCount;

    public Stats(Board board) {
        this.healthyCount = board.getHealthyCount();
        this.infectedCount = board.getInfectedCount();
        this.immuneCount = board.getImmuneCount();
        this.deadCount = board.getDeadCount();
        this.setPreferredSize(new Dimension(350, 150));
       // this.setBackground(Color.lightGray);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setFont(new Font("Arial", Font.PLAIN, 20));
        g.drawString("Statystyki symulacji:", 50, 50);
        g.drawString("Rundy: " + Simulation.totalRounds,50,90);
        g.drawString("Zdrowi: " + healthyCount, 50, 130);
        g.drawString("Zainfekowani: " + infectedCount, 50, 170);
        g.drawString("Odporni: " + immuneCount, 50, 210);
        g.drawString("Martwi: " + deadCount, 50, 250);
    }
}
