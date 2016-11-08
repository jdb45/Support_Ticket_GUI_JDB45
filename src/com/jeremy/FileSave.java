package com.jeremy;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;

public class FileSave extends TicketGUI{

    //file save method
    public static void fileSave(LinkedList<Ticket> ticketToSave) {

        //try catch to check of the file exists.
        try {
            FileWriter writerOpenTickets = new FileWriter("open_tickets.txt");
            for(Ticket ticketPrint : ticketLine){
                writerOpenTickets.write(String.valueOf(ticketPrint + "\n"));
            }
            writerOpenTickets.close();
        } catch (IOException e) {
            System.out.println("File was not saved");
        }
    }
    //method for saving a resolved file
    public static void fileSaveResolve(LinkedList<ResolvedTicket> resolvedTickets){

        try {
            Date date = new Date();
            FileWriter writerResolved = new FileWriter("Resolved_tickets_as_of_" + date + ".txt");
            for(Ticket ticketPrint : resolvedTickets) {
                writerResolved.write(String.valueOf(ticketPrint + "\n"));
            }
            writerResolved.close();
        } catch (IOException e) {
            System.out.println("File was not saved");
        }
    }
}
