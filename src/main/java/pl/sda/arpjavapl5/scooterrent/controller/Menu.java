package pl.sda.arpjavapl5.scooterrent.controller;

import lombok.Data;

import java.util.List;

@Data
public class Menu {
    private final List<MenuItem> menuItems;

    public void print(){
        int number = 1;
        for (MenuItem item: menuItems){
            System.out.println(number +". "+ item.getLabel());
            number++;
        }
    }

    public void run(int option){
        if (option >= 1 && option <= menuItems.size()){
            menuItems.get(option - 1).getAction().run();
        }
    }
}
