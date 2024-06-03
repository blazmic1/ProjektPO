import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Simulation {
    static int roundCount = 0;
    static int totalRounds;
    static Timer timer;

    public static void main(String[] args) {

        String font = "Calibri";

        JFrame frame = new JFrame("Symulacja epidemii");
        frame.setLayout(null);
        frame.setSize(1000, 650);
        frame.getContentPane().setBackground(Color.DARK_GRAY);
        frame.setLocationRelativeTo(null);
        ImageIcon imageIcon = new ImageIcon("virus_icon.png");
        frame.setIconImage(imageIcon.getImage());

        Board board = new Board();
        frame.add(board);

        JLabel label1 = new JLabel("Podaj ilość rund");
        label1.setBounds(615, -58, 300, 200);
        label1.setFont(new Font(font, Font.PLAIN, 20));
        label1.setForeground(Color.white);
        frame.add(label1);

        JTextField rounds = new JTextField();
        rounds.setBounds(875, 33, 100, 20);
        frame.add(rounds);

        JLabel label2 = new JLabel("Podaj ilość zdrowych");
        label2.setBounds(615, -10, 300, 200);
        label2.setFont(new Font(font, Font.PLAIN, 20));
        label2.setForeground(Color.white);
        frame.add(label2);

        JTextField numberofHealthy = new JTextField();
        numberofHealthy.setBounds(875, 80, 100, 20);
        frame.add(numberofHealthy);

        JLabel label3 = new JLabel("Podaj ilość chorych");
        label3.setBounds(615, 38, 300, 200);
        label3.setFont(new Font(font, Font.PLAIN, 20));
        label3.setForeground(Color.white);
        frame.add(label3);

        JTextField numberofInfected = new JTextField();
        numberofInfected.setBounds(875, 127, 100, 20);
        frame.add(numberofInfected);

        JLabel label4 = new JLabel("Podaj ilość odpornych");
        label4.setBounds(615, 86, 300, 200);
        label4.setFont(new Font(font, Font.PLAIN, 20));
        label4.setForeground(Color.white);
        frame.add(label4);

        JTextField numberofImmune = new JTextField();
        numberofImmune.setBounds(875, 176, 100, 20);
        frame.add(numberofImmune);

        JLabel label5 = new JLabel("Podaj ilość nie przenoszacych");
        label5.setBounds(615, 134, 300, 200);
        label5.setFont(new Font(font, Font.PLAIN, 20));
        label5.setForeground(Color.white);
        frame.add(label5);

        JTextField numberofnotTransmiting = new JTextField();
        numberofnotTransmiting.setBounds(875, 225, 100, 20);
        frame.add(numberofnotTransmiting);

        JLabel label6 = new JLabel("Podaj ilość szpitali");
        label6.setBounds(615, 182, 300, 200);
        label6.setFont(new Font(font, Font.PLAIN, 20));
        label6.setForeground(Color.white);
        frame.add(label6);

        JTextField numberofHospitals = new JTextField();
        numberofHospitals.setBounds(875, 274, 100, 20);
        frame.add(numberofHospitals);

        JLabel label7 = new JLabel("Podaj ilość szczepionek");
        label7.setBounds(615, 230, 300, 200);
        label7.setFont(new Font(font, Font.PLAIN, 20));
        label7.setForeground(Color.white);
        frame.add(label7);

        JTextField numberofVaccines = new JTextField();
        numberofVaccines.setBounds(875, 323, 100, 20);
        frame.add(numberofVaccines);

        JLabel label8 = new JLabel("Prędkość symulacji");
        label8.setBounds(712, 280, 400, 200);
        label8.setFont(new Font(font, Font.PLAIN, 20));
        label8.setForeground(Color.white);
        frame.add(label8);

        JSlider slider = new JSlider(500, 3500);
        slider.setBounds(610, 405, 350, 40);
        slider.setValue(2000);

        slider.setPaintTicks(true);
        slider.setMinorTickSpacing(250);

        slider.setPaintTrack(true);
        slider.setMajorTickSpacing(500);

        slider.setPaintLabels(true);
        slider.setFont(new Font(font,Font.PLAIN,10));
        slider.setBackground(Color.DARK_GRAY);
        slider.setForeground(Color.white);
        frame.add(slider);

        JButton button = new JButton();
        button.setBounds(615, 480, 350, 120);
        button.setText("Rozpocznij symulacje");
        button.setFont(new Font(font, Font.PLAIN, 20));
        button.setBackground(Color.decode("#1fd655"));
        button.setFocusable(false);
        frame.add(button);

        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                totalRounds = Integer.parseInt(rounds.getText());
                int x = Integer.parseInt(numberofHealthy.getText());
                int y = Integer.parseInt(numberofInfected.getText());
                int z = Integer.parseInt(numberofnotTransmiting.getText());
                int a = Integer.parseInt(numberofImmune.getText());
                int b = Integer.parseInt(numberofVaccines.getText());
                int c = Integer.parseInt(numberofHospitals.getText());
                board.populateBoard(x, y, z, a, b, c);
                button.setEnabled(false);

                int delay = slider.getValue();
                timer = new Timer(delay, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (roundCount < totalRounds) {
                            board.updateSimulation();
                            roundCount++;
                        } else {
                            ((Timer) e.getSource()).stop();

                            showStatsWindow(board);

//                            JFrame frame1 =  new JFrame("Podsumowanie");
//                            frame1.setSize(new Dimension(500,500));
//                            frame1.setLocationRelativeTo(null);
//                            frame1.setVisible(true);
//                            frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//
//                            JLabel label = new JLabel("Symulacja zakonczona po");
//                            label.setSize(new Dimension(100,200));
//                            label.setVerticalAlignment();
//                            frame1.add(label);

                            //System.out.println("Symulacja zakończona po " + totalRounds + " rundach");
                        }
                    }
                });

                roundCount = 0;
                timer.start();
            }
        });

        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (timer != null) {
                    timer.setDelay(slider.getValue());

                }
            }
        });
    } private static void showStatsWindow(Board board) {
        JFrame frame1 = new JFrame("Podsumowanie");
        frame1.setSize(new Dimension(500, 300));
        frame1.setLocationRelativeTo(null);

        Stats stats = new Stats(board);
        frame1.add(stats);

        frame1.setVisible(true);
    }
}

