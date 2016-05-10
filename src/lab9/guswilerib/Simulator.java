package lab9.guswilerib;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

/**
 * CS2852 - 041
 * Spring 2016
 * Lab
 * Name: Ian Guswiler
 * Created: 5/10/2016
 */
public class Simulator extends JFrame {

    public Simulator(){
        JButton startButton = new JButton("Start");
        JButton stopButton = new JButton("Stop");
        JButton updateButton = new JButton("Update");
        JButton addButton = new JButton("Add");
        JButton deleteButton = new JButton("Delete");
        JButton undoButton = new JButton("Undo");
        JButton redoButton = new JButton("Redo");
        JButton exitButton = new JButton("Exit");
        JLabel nameLabel = new JLabel("Domain Name: ");
        JLabel addressLabel = new JLabel("IP Address: ");
        JTextField nameField = new JTextField(30);
        JTextField addressField = new JTextField(20);
        JPanel row1 = new JPanel(new FlowLayout());
        JPanel row2 = new JPanel(new FlowLayout());
        JPanel row3 = new JPanel(new FlowLayout());
        JPanel row4 = new JPanel(new FlowLayout());

        setTitle("Domain Name System");
        setSize(450, 200);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new GridLayout(4,1));

        row1.add(startButton);
        row1.add(stopButton);
        row1.add(updateButton);

        row2.add(nameLabel);
        row2.add(nameField);

        row3.add(addressLabel);
        row3.add(addressField);

        row4.add(addButton);
        row4.add(deleteButton);
        row4.add(undoButton);
        row4.add(redoButton);
        row4.add(exitButton);

        add(row1);
        add(row2);
        add(row3);
        add(row4);
    }

    public static void main(String[] args) {
        Simulator simulator = new Simulator();
        simulator.setVisible(true);
    }
}
