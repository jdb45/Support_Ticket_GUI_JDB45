package com.jeremy;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class FileOpen extends FileSave{

    public static void fileOpen() {
        Scanner scan = new Scanner(System.in);
        //creating two variables
        Scanner wordFile;
        String ticketWord;

        //this code is terrible. I used what I had from that last ticket program. I ran out of time. It works, but I could defiantly make it better.
        //try block to check if the file exists or not
        try {
            wordFile = new Scanner(new FileReader("open_tickets.txt"));
            wordFile.useDelimiter(" ");
            //creating variables to store the data from the open tickets files
            ticketWord = wordFile.next();
            int ticketID = 0;
            ArrayList<String> description = new ArrayList<>();
            int priority = 0;
            ArrayList<String> reporter = new ArrayList<>();
            String descString = "";
            String repoterString = "";
            ArrayList<String> dateFormat = new ArrayList<>();
            String time = "";
            //setting a while loop to check if the file has data
            while (wordFile.hasNext()) {
                //getting the ID from the file
                if (ticketWord.contains("ID=")) {
                    ticketWord = wordFile.next();
                    ticketID = Integer.parseInt(ticketWord);
                    ticketWord = wordFile.next();
                    continue;
                    //getting the description of the problem, having it check if the word equals Issued, and then ending on Priority to get
                    //the whole description, and adding it to an arraylist
                } else if (ticketWord.contains("Issued:")) {
                    ticketWord = wordFile.next();
                    while (!ticketWord.equals("Priority:")) {
                        for (int i = 0; i < 1; i++) {
                            description.add(ticketWord);
                            ticketWord = wordFile.next();
                        }
                    }
                    continue;

                } //checking when the word contains Priority, and parsing it to an int
                else if (ticketWord.contains("Priority:")) {
                    ticketWord = wordFile.next();
                    priority = Integer.parseInt(ticketWord);
                    ticketWord = wordFile.next();
                    continue;

                } //this will skip past the word Reported
                else if (ticketWord.contains("Reported")) {
                    ticketWord = wordFile.next();
                    continue;

                } //getting the info for who reported the ticket, and ending on reported to get the whole user who reported it
                else if (ticketWord.contains("by:")) {
                    ticketWord = wordFile.next();
                    while (!ticketWord.equals("Reported")) {
                        for (int i = 0; i < 1; i++) {
                            reporter.add(ticketWord);
                            ticketWord = wordFile.next();
                        }
                    }
                    continue;

                } //skipping past the word on
                else if (ticketWord.contains("on:")) {
                    ticketWord = wordFile.next();
                    continue;

                } /*else to add the date format to an arraylist, and end the reading of each line by ending it with a "."
                and each for loop to convert each arrylist to a string, and split each word*/ else {
                    dateFormat.add(ticketWord);
                    ticketWord = wordFile.next();
                    if (ticketWord.contains(".")) {
                        for (String s : description) {
                            descString += s + " ";
                        }
                        for (String s : reporter) {
                            repoterString += s + " ";
                        }
                        dateFormat.add("2016");
                        for (String s : dateFormat) {
                            time += s + " ";
                        }
                        //converting the date to the correct format
                        DateFormat df = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
                        Date date = df.parse(time);
                    /*adding each ticket to the ticket method, and adding it to the Ticket priority method
                    also resetting each variable to 0 or empty string for the next ticket*/
                        t = new Ticket(descString, priority, repoterString, date);
                        description = new ArrayList<>();
                        reporter = new ArrayList<>();
                        repoterString = "";
                        descString = "";
                        dateFormat = new ArrayList<>();
                        time = "";

                        ticketQue.add(t);

                    }
                }

            }
            //catch blocks to catch any errors
        } catch (FileNotFoundException e) {
            System.err.println(e);
            System.out.println();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        catch (NoSuchElementException no){
            System.out.println(no);
        }
    }
}
