package com.jeremy;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.Scanner;

public class TicketGUI extends JFrame{
    private JPanel rootPanel;
    private JButton addTicketButton;
    private JList<Ticket> currentTicketList;
    private JTextField enterProblem;
    private JTextField reportIssue;
    private JComboBox priorityCmbo;
    private JButton deleteTicketButton;
    private JButton quitButton;

    //creating a lot of lists to store everything
    protected static LinkedList<Ticket> ticketLine = new LinkedList<Ticket>();

    protected static ResolvedTicket resolvedTickets;

    public static LinkedList<ResolvedTicket> savedResolved = new LinkedList<ResolvedTicket>();

    public static DefaultListModel<Ticket> ticketsModel = new DefaultListModel<>();
    public static LinkedList<Ticket> ticketQue = new LinkedList<Ticket>();

    public static Ticket t;


    Date date = new Date();

    public void setCurrentTicketList(JList<Ticket> currentTicketList) {
        this.currentTicketList = currentTicketList;
    }

    public TicketGUI() {
        //setting the headline name
        super("Ticket Support GUI");
        //setting the content
        setContentPane(rootPanel);
        //setting an exit on close to make sure it closes the GUI
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //setting the size
        setPreferredSize(new Dimension(800, 500));
        pack();
        //calling the methods
        addTicket();
        setVisible(true);
        addPriority();
        resolvedTickets();
        FileOpen.fileOpen();
        //for each loop to add all of the Open Tickets from the file
        for(Ticket tick : ticketQue){
            t = tick;
            ticketsModel.addElement(t);
            ticketLine.add(tick);
            currentTicketList.setModel(ticketsModel);
        }
        
        quit();

    }

    //add ticket method
    private void addTicket() {
        addTicketButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //geting the input from the user, and the combo box
                String problem = enterProblem.getText();
                String reporter = reportIssue.getText();
                String priority = priorityCmbo.getSelectedItem().toString();
                //parsing what priority the used chose
                int inputPriority = Integer.parseInt(priority);
                //saving a ticket based off what the user entered
                Ticket userTicket = new Ticket(problem, inputPriority, reporter, new Date());
                //adding the ticket to the model
                ticketsModel.addElement(userTicket);
                //adding the ticket to the linkedList
                ticketLine.add(userTicket);
                //setting the model for current tickets
                currentTicketList.setModel(ticketsModel);
                //calling teh method to clear the input boxes
                clearBoxes();
            }

        });

    }
    //a method to handel resolved tickets
    public void resolvedTickets() {
        deleteTicketButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userEnter = null;
                Ticket ticketToResolve = null;
                //a for loop to make sure there is a ticket selected to resolve
                for (int i = 0; i < ticketsModel.size(); i++) {
                    ticketToResolve = currentTicketList.getSelectedValue();
                    if (ticketToResolve == null) {
                        JOptionPane.showMessageDialog(TicketGUI.this, "None selected");
                        return;
                    }
                    //getting the user input for the solution
                    userEnter = JOptionPane.showInputDialog("Enter solution");

                    //calling a JoptionPane to make sure the user wants to delete the ticket
                    if (JOptionPane.OK_OPTION == JOptionPane.showConfirmDialog(TicketGUI.this, "Do you want to delete ticket?", "Delete Ticket", JOptionPane.OK_CANCEL_OPTION)) {

                        //removing a ticket from the model
                        ticketsModel.removeElement(ticketToResolve);
                        //adding the ticket to the resolved ticket
                        resolvedTickets = new ResolvedTicket(ticketToResolve, date, userEnter);
                        savedResolved.add(resolvedTickets);
                        //removing the resolved ticket
                        ticketLine.remove(ticketToResolve);

                    }
                }
                //clearing the selected ticket
                currentTicketList.clearSelection();

            }
        });

    }
    //quit method
    public void quit(){
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //calling a JoptionPane to make sure the user wants to quit the program
                if (JOptionPane.OK_OPTION == JOptionPane.showConfirmDialog(TicketGUI.this, "Are you sure you want to exit?", "Exit", JOptionPane.OK_CANCEL_OPTION)) {
                    //saves the files once the user exits the program
                    FileSave.fileSave(ticketLine);
                    FileSave.fileSaveResolve(savedResolved);
                    System.exit(0);
                }
            }
        });
    }
    //method to add numbers to the priority combo box
    private void addPriority() {
        for (int i = 1; i <= 5; i++) {
            priorityCmbo.addItem(i);

        }
    }
    //clear the input boxes
    public void clearBoxes() {
        enterProblem.setText("");

        reportIssue.setText("");
    }

}


