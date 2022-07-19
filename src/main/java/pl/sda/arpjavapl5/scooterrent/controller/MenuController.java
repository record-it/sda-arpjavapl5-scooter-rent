package pl.sda.arpjavapl5.scooterrent.controller;

import java.util.Scanner;

public class MenuController {
    private final Scanner scanner;
    private final Menu menu;

    public MenuController(Scanner scanner, Menu menu) {
        this.scanner = scanner;
        this.menu = menu;
    }

    public void processLoop(){
        while(true){
            menu.print();
            if (scanner.hasNextInt()){
                int option = scanner.nextInt();
                scanner.nextLine();
                menu.run(option);
            }
        }
    }
}
