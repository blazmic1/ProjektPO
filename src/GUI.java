import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class GUI {
    static int roundCount = 0;
    static int totalRounds;
    static Timer timer;
    String font = "Calibri";

    public GUI() {
        JFrame frame = new JFrame("Symulacja epidemii");
        frame.setLayout(null);
        frame.setSize(1100, 650);
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
        rounds.setBounds(975, 33, 100, 20);
        frame.add(rounds);

        JLabel label2 = new JLabel("Podaj ilość zdrowych");
        label2.setBounds(615, -10, 250, 200);
        label2.setFont(new Font(font, Font.PLAIN, 20));
        label2.setForeground(Color.white);
        frame.add(label2);

        JTextField numberofHealthy = new JTextField();
        numberofHealthy.setBounds(975, 80, 100, 20);
        frame.add(numberofHealthy);

        JLabel label3 = new JLabel("Podaj ilość chorych");
        label3.setBounds(615, 38, 300, 200);
        label3.setFont(new Font(font, Font.PLAIN, 20));
        label3.setForeground(Color.white);
        frame.add(label3);

        JTextField numberofInfected = new JTextField();
        numberofInfected.setBounds(975, 127, 100, 20);
        frame.add(numberofInfected);

        JLabel label4 = new JLabel("Podaj ilość odpornych");
        label4.setBounds(615, 86, 300, 200);
        label4.setFont(new Font(font, Font.PLAIN, 20));
        label4.setForeground(Color.white);
        frame.add(label4);

        JTextField numberofImmune = new JTextField();
        numberofImmune.setBounds(975, 176, 100, 20);
        frame.add(numberofImmune);

        JLabel label5 = new JLabel("Podaj ilość chorych nie przenoszących");
        label5.setBounds(615, 134, 350, 200);
        label5.setFont(new Font(font, Font.PLAIN, 20));
        label5.setForeground(Color.white);
        frame.add(label5);

        JTextField numberofnotTransmiting = new JTextField();
        numberofnotTransmiting.setBounds(975, 225, 100, 20);
        frame.add(numberofnotTransmiting);

        JLabel label6 = new JLabel("Podaj ilość szpitali");
        label6.setBounds(615, 182, 300, 200);
        label6.setFont(new Font(font, Font.PLAIN, 20));
        label6.setForeground(Color.white);
        frame.add(label6);

        JTextField numberofHospitals = new JTextField();
        numberofHospitals.setBounds(975, 274, 100, 20);
        frame.add(numberofHospitals);

        JLabel label7 = new JLabel("Podaj ilość szczepionek");
        label7.setBounds(615, 230, 300, 200);
        label7.setFont(new Font(font, Font.PLAIN, 20));
        label7.setForeground(Color.white);
        frame.add(label7);

        JTextField numberofVaccines = new JTextField();
        numberofVaccines.setBounds(975, 323, 100, 20);
        frame.add(numberofVaccines);

        JLabel label8 = new JLabel("Prędkość symulacji");
        label8.setBounds(770, 280, 400, 200);
        label8.setFont(new Font(font, Font.PLAIN, 20));
        label8.setForeground(Color.white);
        frame.add(label8);

        JSlider slider = new JSlider(500, 3500);
        slider.setBounds(610, 405, 470, 40);
        slider.setValue(2000);

        slider.setPaintTicks(true);
        slider.setMinorTickSpacing(250);

        slider.setPaintTrack(true);
        slider.setMajorTickSpacing(500);

        slider.setPaintLabels(true);
        slider.setFont(new Font(font, Font.PLAIN, 10));
        slider.setBackground(Color.DARK_GRAY);
        slider.setForeground(Color.white);
        frame.add(slider);

        JButton button = new JButton();
        button.setBounds(615, 480, 460, 120);
        button.setText("Rozpocznij symulacje");
        button.setFont(new Font(font, Font.PLAIN, 20));
        button.setBackground(Color.decode("#1fd655"));
        button.setFocusable(false);
        frame.add(button);


        //legenda
        Image healthyImage = new ImageIcon("healthy_new.png").getImage();
        Image infectedImage = new ImageIcon("infected_new.png").getImage();
        Image immuneImage = new ImageIcon("immune_new.png").getImage();
        Image notTransmiting = new ImageIcon("notTransmitting_new.png").getImage();
        Image Hospital = new ImageIcon("hospital_new.png").getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        Image vaccine = new ImageIcon("vaccine_new.png").getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);


        //healthy
        JLabel label10 = new JLabel();
        label10.setBounds(810, 73, 30, 30);
        label10.setIcon(new ImageIcon(healthyImage));
        frame.add(label10);

        //infected
        JLabel label12 = new JLabel();
        label12.setBounds(810, 115, 30, 30);
        label12.setIcon(new ImageIcon(infectedImage));
        frame.add(label12);

        //immune
        JLabel label14 = new JLabel();
        label14.setBounds(810, 165, 30, 30);
        label14.setIcon(new ImageIcon(immuneImage));
        frame.add(label14);

        //infected not transmiting
        JLabel label16 = new JLabel();
        label16.setBounds(930, 215, 35, 30);
        label16.setIcon(new ImageIcon(notTransmiting));
        frame.add(label16);

        //hospital
        JLabel label18 = new JLabel();
        label18.setBounds(810, 265, 30, 30);
        label18.setIcon(new ImageIcon(Hospital));
        frame.add(label18);

        //vaccine
        JLabel label20 = new JLabel();
        label20.setBounds(820, 275, 100, 100);
        label20.setIcon(new ImageIcon(vaccine));
        frame.add(label20);


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
    }

private static void showStatsWindow(Board board) {
    JFrame frame1 = new JFrame("Podsumowanie");
    frame1.setSize(new Dimension(500, 300));
    frame1.setLocationRelativeTo(null);

    Stats stats = new Stats(board);
    frame1.add(stats);

    frame1.setVisible(true);
}
}
