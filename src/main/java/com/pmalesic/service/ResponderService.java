package com.pmalesic.service;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.io.DataOutputStream;


public class ResponderService {
    // Create instance of responder player that will output PID to initiator player
    // and reply with back with 10 messages that were sent, along it's with incremented counter
    private Socket socket;
    private DataOutputStream out;
    private DataInputStream in;

    public ResponderService(String address, int port)
    {
        try {
            socket = new Socket(address, port);
            out = new DataOutputStream(socket.getOutputStream());
            in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        }
        catch (IOException ex) {
            System.out.printf("Error establishing connection service: %s", ex);
        }
    }

    public void startCommunication(){
        try{
            String pid = String.valueOf(ProcessHandle.current().pid());
            out.writeUTF(pid);
            String firstMessage = in.readUTF();
            int counter = 0;
            // Send reply messages until counter reaches 10
            while (true) {
                try
                {
                    if(counter <= 10){
                        out.writeUTF(firstMessage + " " + counter++);
                    } else {
                        out.writeUTF("Ending connection...");
                        break;
                    }
                }
                catch(IOException ex)
                {
                    System.out.printf("Error while sending message to alpha player %s: ", ex);
                }
            }
            try {
                // Try to gracefully close connection
                out.close();
                socket.close();
            }
            catch (IOException ex) {
                System.out.printf("Error with beta player: %s", ex);
            }
        }
        catch (IOException ex){
            System.out.printf("Exception during beta player communication service: %s", ex);
        }
    }
}