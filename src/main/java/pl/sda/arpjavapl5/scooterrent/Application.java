package pl.sda.arpjavapl5.scooterrent;

import pl.sda.arpjavapl5.scooterrent.controller.Menu;
import pl.sda.arpjavapl5.scooterrent.controller.MenuController;
import pl.sda.arpjavapl5.scooterrent.controller.MenuItem;
import pl.sda.arpjavapl5.scooterrent.dao.CrudDao;
import pl.sda.arpjavapl5.scooterrent.entity.Scooter;
import pl.sda.arpjavapl5.scooterrent.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Application {
    static Scanner scanner = new Scanner(System.in);
    static CrudDao<Scooter, Long> scooters = new CrudDao<>("scooter-rent", Scooter.class);
    static CrudDao<User, Long> users = new CrudDao<>("scooter-rent", User.class);
    static MenuController controller;

    public static void main(String[] args) {
        Menu menu = new Menu(
                List.of(
                        MenuItem.builder()
                                .label("Dodaj skuter")
                                .action(() -> {
                                    System.out.println("Podaj identyfikator:");
                                    String identyfikator = scanner.nextLine();
                                    System.out.println("Podaj model");
                                    final String model = scanner.nextLine();
                                    final Scooter scooter = Scooter
                                            .builder()
                                            .model(model)
                                            .identifier(identyfikator)
                                            .build();
                                    scooters.save(scooter);
                                })
                                .build(),
                        MenuItem.builder()
                                .label("Usuń skuter")
                                .action(() ->{
                                    //uzupełnij lambe o
                                    //1. pytanie o id usuwanego skutera
                                    //2. Wczytanie id typu long
                                    //3. Usunięcie z bazy skutera o podanym id
                                    System.out.println("podaj ID skutera do usuniecia:");
                                    long id = scanner.nextLong();
                                    Optional<Scooter> scooter = scooters.findById(id);
                                    if (scooter.isPresent()){
                                        scooters.delete(scooter.get().getId());
                                    }
                                })
                                .build(),
                        MenuItem.builder()
                                .label("Lista skuterów")
                                .action(() -> {
                                    final Optional<List<Scooter>> allScooters = scooters.resultTransaction(em -> {
                                        final List<Scooter> list = em.createQuery("from Scooter", Scooter.class).getResultList();
                                        return list;
                                    });
                                    if (allScooters.isPresent()) {
                                        allScooters.get().forEach(System.out::println);
                                    }
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
        controller = new MenuController(scanner, menu);
        controller.processLoop();
    }
}
