import javax.swing.*;
import java.awt.*;

public class Stats extends JPanel {
    private final int healthyCount;
    private final int infectedCount;
    private final int immuneCount;
    private final int deadCount;

    public Stats(Board board) {
        this.healthyCount = board.getHealthyCount();
        this.infectedCount = board.getInfectedCount();
        this.immuneCount = board.getImmuneCount();
        this.deadCount = Infected.getDeathCount() + Human.getOldAgeCount();
        this.setPreferredSize(new Dimension(350, 150));
    }

    public String getData(){
        return "Zdrowi: " + healthyCount + "\nZainfekowani: " + infectedCount + "\nOdporni: " + immuneCount + "\nMartwi: " + deadCount;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setFont(new Font("Calibri", Font.PLAIN, 20));
        g.drawString("Statystyki symulacji:", 50, 50);
        g.drawString("Rundy: " + GUI.totalRounds,50,90);
        g.drawString("Zdrowi: " + healthyCount, 50, 130);
        g.drawString("Zainfekowani: " + infectedCount, 50, 170);
        g.drawString("Odporni: " + immuneCount, 50, 210);
        g.drawString("Martwi: " +deadCount , 50, 250);
    }
}
