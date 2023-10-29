package com.pmalesic.service;

import java.io.DataOutputStream;
import java.net.Socket;
import java.net.ServerSocket;
import java.io.IOException;
import java.io.DataInputStream;
import java.io.BufferedInputStream;

public class InitiatorService {
    // Create instance of initiator player that will output it\'s PID
    // and send "firstMessage" to the newly connected beta player
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;

    public InitiatorService(int port)
    {
        try
        {
            ServerSocket server = new ServerSocket(port);
            socket = server.accept();
            out = new DataOutputStream(socket.getOutputStream());
            in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        }
        catch(IOException | RuntimeException ex)
        {
            System.out.printf("Error establishing connection service: %s", ex);
        }
    }


    public void startCommunication(){
        try {
            long pid = ProcessHandle.current().pid();
            System.out.println("Alpha player established connection with PID: "+ pid);
            String betaPlayerPid = in.readUTF();
            System.out.println("Beta player accepted request with PID: " + betaPlayerPid);

            String firstMessage = "Hello beta player";
            out.writeUTF(firstMessage);
            String line = "";

            // Keep communication alive until beta players send termination message "End"
            while (!line.startsWith("End"))
            {
                try {
                    line = in.readUTF();
                    System.out.println(line);
                    // Sleep thread just to not have instant output
                    try{Thread.sleep(500);} catch (InterruptedException e){
                        System.out.println("Thread exception");
                    }
                }
                catch(IOException ex)
                {
                    System.out.printf("Error while sending message to beta player %s: ", ex);
                }
            }
            // Try to gracefully close connection
            in.close();
            socket.close();
        }
        catch(IOException | RuntimeException ex)
        {
            System.out.printf("Exception during alpha player communication service: %s", ex);
        }
    }
}