package pl.sda.arpjavapl5.scooterrent;

import pl.sda.arpjavapl5.scooterrent.controller.Menu;
import pl.sda.arpjavapl5.scooterrent.controller.MenuController;
import pl.sda.arpjavapl5.scooterrent.controller.MenuItem;
import pl.sda.arpjavapl5.scooterrent.dao.CrudDao;
import pl.sda.arpjavapl5.scooterrent.entity.Scooter;
import pl.sda.arpjavapl5.scooterrent.exception.RollbackException;

import java.util.List;
import java.util.Optional;

public class Application {
    static CrudDao<Scooter, Long> scooters = new CrudDao<>("scooter-rent", Scooter.class);
    static MenuController controller;
    public static void main(String[] args) {
        Menu menu = new Menu(
                List.of(
                        MenuItem.builder()
                                .label("Dodaj skuter")
                                .action(() -> {

                                })
                                .build(),
                        MenuItem.builder()
                                .action(() -> {

                                })
                                .label("Dodaj użytkownika").build(),
                        MenuItem.builder()
                                .action(() -> {

                                })
                                .label("Wynajmij").build(),
                        MenuItem.builder()
                                .label("Koniec")
                                .action(() -> {
                                    System.exit(0);
                                })
                                .build()
                )
        );
        controller = new MenuController(menu);
        controller.processLoop();
    }
}
