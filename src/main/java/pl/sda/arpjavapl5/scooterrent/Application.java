package pl.sda.arpjavapl5.scooterrent;

import pl.sda.arpjavapl5.scooterrent.controller.Menu;
import pl.sda.arpjavapl5.scooterrent.controller.MenuController;
import pl.sda.arpjavapl5.scooterrent.controller.MenuItem;
import pl.sda.arpjavapl5.scooterrent.dao.CrudDao;
import pl.sda.arpjavapl5.scooterrent.entity.Rent;
import pl.sda.arpjavapl5.scooterrent.entity.Scooter;
import pl.sda.arpjavapl5.scooterrent.entity.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Application {
    static Scanner scanner = new Scanner(System.in);
    static CrudDao<Scooter, Long> scooters = new CrudDao<>("scooter-rent", Scooter.class);
    static CrudDao<User, Long> users = new CrudDao<>("scooter-rent", User.class);
    
    static CrudDao<Rent, Long> rents = new CrudDao<>("scooter-rent", Rent.class);
    static MenuController controller;

    public static void main(String[] args) {
        Menu menu = new Menu(
                List.of(
                        MenuItem.builder()
                                .label("Dodaj skuter")
                                .action(Application::addScooter)
                                .build(),
                        MenuItem.builder()
                                .label("Usuń skuter")
                                .action(Application::deleteScooter)
                                .build(),
                        MenuItem.builder()
                                .label("Lista skuterów")
                                .action(Application::scootersList)
                                .build(),
                        MenuItem.builder()
                                .action(Application::addUser)
                                .label("Dodaj użytkownika").build(),
                        MenuItem.builder()
                                .action(Application::rentScooter)
                                .label("Wynajmij").build(),
                        MenuItem.builder()
                                .label("Koniec")
                                .action(Application::exit)
                                .build()
                )
        );
        controller = new MenuController(scanner, menu);
        controller.processLoop();
    }

    private static void rentScooter(){
        System.out.println("Podaj id skutera:");
        long idScooter = scanner.nextLong();
        System.out.println("Podaj id użytkownika:");
        long idUser = scanner.nextLong();
        //początek transakcji
        scooters.transaction(em -> {
            final Optional<Scooter> scooterOp = Optional.ofNullable(em.find(Scooter.class, idScooter));
            final Optional<User> userOp = Optional.ofNullable(em.find(User.class, idUser));
            if (!scooterOp.isPresent()) {
                System.out.println("Brak takiej hulajnogi, wynajęcie niemożliwe!");
                return;
            }
            if (!userOp.isPresent()) {
                System.out.println("Brak takiego użytkownika, wynajęcie niemożliwe!");
                return;
            }
            final Scooter scooter = scooterOp.get();
            final User user = userOp.get();
            if (!scooter.isAvailable()) {
                System.out.println("Hulajnoga jest aktualnie wynajęta przez innego użytkownika, wynajęcie nie możliwe");
                return;
            }
            scooter.setAvailable(false);
            final Rent rent = Rent.builder()
                    .user(user)
                    .scooter(scooter)
                    .pricePerMinute(new BigDecimal(2))
                    .startOn(LocalDateTime.now())
                    .build();
            em.persist(rent);
            //koniec transakcji
        });
    }
    
    private static void rentsList(){
        final Optional<List<Rent>> list = rents
                .resultTransaction(em -> em.createQuery("from Rent ", Rent.class).getResultList());
        if (list.isPresent()){
            list.get().forEach(System.out::println);
        }
    }
    
    private static void exit() {
        System.exit(0);
    }

    private static void addUser() {
        System.out.println("Podaj email:");
        String email = scanner.nextLine();
        System.out.println("Podaj hasło:");
        final String password = scanner.nextLine();
        final User user = User
                .builder()
                .email(email)
                .password(password)
                .build();
        users.save(user);
    }

    private static void scootersList() {
        final Optional<List<Scooter>> allScooters = scooters.resultTransaction(em -> {
            final List<Scooter> list = em.createQuery("from Scooter", Scooter.class).getResultList();
            return list;
        });
        if (allScooters.isPresent()) {
            allScooters.get().forEach(System.out::println);
        }
    }

    private static void deleteScooter() {
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
    }

    private static void addScooter() {
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
    }
}
