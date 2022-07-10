package pl.sda.arpjavapl5.scooterrent.controller;

import lombok.Builder;
import lombok.Data;

//1. Dodaj skuter
//2. Dodaj użytkownika
//3. Wynajmij skuter
@Data
@Builder
public class MenuItem {
    private String label;
    private Runnable action;
}
