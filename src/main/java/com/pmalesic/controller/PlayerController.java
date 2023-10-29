package com.pmalesic.controller;

import com.pmalesic.service.InitiatorService;
import com.pmalesic.service.ResponderService;

public class PlayerController {
    String instanceType;
    int port;

    public PlayerController(String instanceType, int port) {
        // Determine type of player and initiate communication
        this.instanceType = instanceType;
        this.port = port;
        if (this.instanceType.equals("initiator")) {
            InitiatorService inPlayer = new InitiatorService(port);
            inPlayer.startCommunication();
        } else if (this.instanceType.equals("responder")) {
            ResponderService resPlayer = new ResponderService("localhost", port);
            resPlayer.startCommunication();
        } else {
            System.out.println("Invalid instance type");
        }
    }
}
