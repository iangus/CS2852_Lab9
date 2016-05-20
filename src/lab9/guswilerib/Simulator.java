/*
 * CS2852 - 041
 * Spring 2016
 * Lab 9
 * Name: Ian Guswiler
 * Created: 5/10/2016
 */

package lab9.guswilerib;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.io.File;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

/**
 * Gui implementation for the domain name system program.
 */
public class Simulator extends JFrame {
    private DNS system = new DNS("dnsentries.txt");
    private JButton startButton = new JButton("Start");
    private JButton stopButton = new JButton("Stop");
    private JButton updateButton = new JButton("Update");
    private JButton addButton = new JButton("Add");
    private JButton deleteButton = new JButton("Delete");
    private JButton undoButton = new JButton("Undo");
    private JButton redoButton = new JButton("Redo");
    private JButton exitButton = new JButton("Exit");
    private JTextField nameField = new JTextField(30);
    private JTextField addressField = new JTextField(20);
    private Queue<String> undoQueue = new LinkedList<>();
    private Stack<String> redoStack = new Stack<>();

    /**
     * Constructs a new program window.
     */
    public Simulator(){
        initializeButtons();
        disableComponents();
        JLabel nameLabel = new JLabel("Domain Name: ");
        JLabel addressLabel = new JLabel("IP Address: ");
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

    private void initializeButtons(){
        startButton.addActionListener(e -> {
            if(!system.start()){
                JOptionPane.showMessageDialog(null,"The system failed to start.", "Start Error", JOptionPane.ERROR_MESSAGE);
            }else {
                enableComponents();
                startButton.setEnabled(false);
            }
        });

        exitButton.addActionListener(e -> {
            this.dispose();
        });

        stopButton.addActionListener(e -> {
            system.stop();
            nameField.setText("");
            addressField.setText("");
            disableComponents();
            startButton.setEnabled(true);
        });

        updateButton.addActionListener(e -> {
            try(Scanner fileScan = new Scanner(new File("updates.txt"))){
                while(fileScan.hasNextLine()){
                    system.update(fileScan.nextLine());
                }
            } catch (IOException e1){
                JOptionPane.showMessageDialog(null,"IOException: " + e1.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
            }catch (InputMismatchException e2){
                System.err.println(e2.getMessage());
            } catch (IllegalArgumentException e3){
                System.err.println(e3.getMessage());
            }
        });

        addButton.addActionListener(e -> {
            try{
                String action = "ADD " + addressField.getText() + " " + nameField.getText();
                system.update(action);
                undoQueue.offer(action);
                if(!undoButton.isEnabled()){
                    undoButton.setEnabled(true);
                }
            } catch (IllegalArgumentException e1){
                JOptionPane.showMessageDialog(null,e1.getMessage(),"Illegal Argument",JOptionPane.ERROR_MESSAGE);
            }
        });

        deleteButton.addActionListener(e -> {
            try{
                String action = "DEL " + addressField.getText() + " " + nameField.getText();
                system.update(action);
                undoQueue.offer(action);
                if(!undoButton.isEnabled()){
                    undoButton.setEnabled(true);
                }
            } catch (IllegalArgumentException e1){
                JOptionPane.showMessageDialog(null,e1.getMessage(),"Illegal Argument",JOptionPane.ERROR_MESSAGE);
            } catch (InputMismatchException e2){
                JOptionPane.showMessageDialog(null,e2.getMessage(),"Input Mismatch",JOptionPane.ERROR_MESSAGE);
            }
        });

        undoButton.addActionListener(e -> {
            try {
                String action = undoQueue.poll();
                Scanner commandScan = new Scanner(action);
                switch (commandScan.next()){
                    case "ADD":
                        system.update("DEL " + commandScan.next() + " " + commandScan.next());
                        break;
                    case "DEL":
                        system.update("DEL " + commandScan.next() + " " + commandScan.next());
                        break;
                }
                redoStack.push(action);
                if(!redoButton.isEnabled()){
                    redoButton.setEnabled(true);
                }
                if(undoQueue.isEmpty()){
                    undoButton.setEnabled(false);
                }
            } catch (IllegalArgumentException e1){
                JOptionPane.showMessageDialog(null,e1.getMessage(),"Illegal Argument",JOptionPane.ERROR_MESSAGE);
            } catch (InputMismatchException e2){
                JOptionPane.showMessageDialog(null,e2.getMessage(),"Input Mismatch",JOptionPane.ERROR_MESSAGE);
            }
        });

        redoButton.addActionListener(e -> {
            try {
                String action = redoStack.pop();
                system.update(action);
                undoQueue.offer(action);
                if(!undoButton.isEnabled()){
                    undoButton.setEnabled(true);
                }
                if(redoStack.isEmpty()){
                    redoButton.setEnabled(false);
                }
            } catch (IllegalArgumentException e1){
                JOptionPane.showMessageDialog(null,e1.getMessage(),"Illegal Argument",JOptionPane.ERROR_MESSAGE);
            } catch (InputMismatchException e2){
                JOptionPane.showMessageDialog(null,e2.getMessage(),"Input Mismatch",JOptionPane.ERROR_MESSAGE);
            }
        });

        nameField.addActionListener(e -> {
            try{
                DomainName name = new DomainName(nameField.getText());
                IPAddress address = system.lookup(name);
                if(address == null){
                    addressField.setText("NOT FOUND");
                }else {
                    addressField.setText(address.toString());
                }
            } catch (IllegalArgumentException e1){
                JOptionPane.showMessageDialog(null,e1.getMessage(),"Illegal Argument",JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private void enableComponents(){
        stopButton.setEnabled(true);
        updateButton.setEnabled(true);
        addButton.setEnabled(true);
        deleteButton.setEnabled(true);
        nameField.setEnabled(true);
        addressField.setEnabled(true);
    }

    private void disableComponents(){
        stopButton.setEnabled(false);
        updateButton.setEnabled(false);
        addButton.setEnabled(false);
        deleteButton.setEnabled(false);
        undoButton.setEnabled(false);
        redoButton.setEnabled(false);
        nameField.setEnabled(false);
        addressField.setEnabled(false);
    }

    /**
     * Makes a new simulator object and sets its visiblity
     * @param args Ignored
     */
    public static void main(String[] args) {
        Simulator simulator = new Simulator();
        simulator.setVisible(true);
    }
}