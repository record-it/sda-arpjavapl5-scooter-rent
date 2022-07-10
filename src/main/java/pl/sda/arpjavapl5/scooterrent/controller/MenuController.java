package pl.sda.arpjavapl5.scooterrent.controller;

import pl.sda.arpjavapl5.scooterrent.entity.Scooter;

import java.util.Scanner;

public class MenuController {
    private static Scanner scanner = new Scanner(System.in);
    private final Menu menu;

    public MenuController(Menu menu) {
        this.menu = menu;
    }

    public void processLoop(){
        while(true){
            menu.print();
            if (scanner.hasNextInt()){
                int option = scanner.nextInt();
                menu.run(option);
                scanner.nextLine();
            }
        }
    }
}
