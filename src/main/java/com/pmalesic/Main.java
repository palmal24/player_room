package com.pmalesic;

import com.pmalesic.controller.PlayerController;

public class Main {
    public static void main(String[] args) {
        if (args.length != 2){
            System.out.println("Invalid number of arguments sent");
            System.exit(0);
        }
        // Init player controller with args
        PlayerController pController = new PlayerController(args[0], Integer.parseInt(args[1]));
    }
}