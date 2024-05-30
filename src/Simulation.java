import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

public class Simulation {
    static int roundCount = 0;
    static int totalRounds;
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Podaj ilosc rund:");
        totalRounds = scanner.nextInt();

        JFrame jFrame = new JFrame("Pandemic Simulation");
        jFrame.setLayout(new GridBagLayout());
        jFrame.setMinimumSize(new Dimension(1000, 1000));
        jFrame.setLocationRelativeTo(null);



        Board board = new Board();
        jFrame.add(board);
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (roundCount < totalRounds) {
                    board.updateSimulation();
                    roundCount++;
                } else {
                    ((Timer)e.getSource()).stop();
                    System.out.println("Symulacja zakończona po " + totalRounds + " rundach");
                }
            }
        });

        timer.start();
    }
}

